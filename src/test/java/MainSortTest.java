import org.apache.commons.math.random.JDKRandomGenerator;
import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * <p>To change this template use File | Settings | File Templates.</p>
 * Created with IntelliJ IDEA.<br/>
 * User: blackshark<br/>
 * Date: 4.9.13<br/>
 * Time: 20:18<br/>
 * <br/>
 */
public class MainSortTest {


	private static final int searchCount = 10000;

	@Test
	public void testCore() throws Exception {

		final int ITEMS_COUNT = 100000;
		TreeSet<SortedItem> cache = new TreeSet<>(new Comparator<SortedItem>() {
			@Override
			public int compare(SortedItem o1, SortedItem o2) {
				return (int)(o1.getLife() - o2.getLife());
			}
		});
		org.apache.commons.math.random.RandomGenerator r = new JDKRandomGenerator();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < ITEMS_COUNT; i++) {
			cache.add(new SortedItem(new Advert("Advert:" + i,r), r));
		}
		long endTIme = System.currentTimeMillis();
		System.out.println("Creating " + ITEMS_COUNT + " SortItems cost " + (endTIme - startTime) + " [ms]");



		startTime = System.currentTimeMillis();
		for (int i = 0; i < searchCount; i++) {
			/*
			if (i % 100 == 0 ) {
				System.out.println("Step: " + i);
			}
			*/
			final Iterator<SortedItem> iterator = cache.iterator();
			SortedItem si = null;
			while (iterator.hasNext()) {
				si = iterator.next();
				if (si.getAdvert().getTags().contains("homepage")) {
					si.setLife(0);
					iterator.remove();
					break;
				}
			}
			if (si != null) {
				cache.add(si);
			}
		}
		endTIme = System.currentTimeMillis();
		System.out.println("Operating " + searchCount + " on cache cost " + (endTIme - startTime) + " [ms]");



	}
}
