import org.apache.commons.math.random.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>To change this template use File | Settings | File Templates.</p>
 * Created with IntelliJ IDEA.<br/>
 * User: blackshark<br/>
 * Date: 4.9.13<br/>
 * Time: 20:15<br/>
 * <br/>
 */
public class Advert {

	private final String name;
	private List<String> tags;

	public Advert(String name,RandomGenerator r) {
		this.name = name;
		tags = new ArrayList<>(4);
		for (int i = 0; i < 2; i++) {

			switch(r.nextInt(5)) {
				case 1: if (!tags.contains("sex")) tags.add("sex");break;
				case 2: if (!tags.contains("pravo")) tags.add("pravo");break;
				case 3: if (!tags.contains("homepage")) tags.add("homepage");break;
				case 4: if (!tags.contains("holky")) tags.add("holky");break;
				case 5: if (!tags.contains("kluci")) tags.add("kluci");break;
			}

		}

	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Advert{" +
			 "name='" + name + '\'' +
			 ", tags=" + tags +
			 '}';
	}
}
