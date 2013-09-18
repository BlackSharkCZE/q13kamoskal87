import org.apache.commons.math.random.RandomGenerator;

/**
 * <p>To change this template use File | Settings | File Templates.</p>
 * Created with IntelliJ IDEA.<br/>
 * User: blackshark<br/>
 * Date: 4.9.13<br/>
 * Time: 20:14<br/>
 * <br/>
 */
public class SortedItem {

	private Advert advert;
	private long life;

	public SortedItem(Advert advert, RandomGenerator r) {
		this.advert = advert;
		this.life = r.nextInt();
	}

	public Advert getAdvert() {
		return advert;
	}

	public void setAdvert(Advert advert) {
		this.advert = advert;
	}

	public long getLife() {
		return life;
	}

	public void setLife(long life) {
		this.life = life;
	}

	@Override
	public String toString() {
		return "SortedItem{" +
			 "advert=" + advert +
			 ", life=" + life +
			 '}';
	}
}
