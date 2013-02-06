package cz.kamoska.partner.support;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.2.13
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public class ArrayStringUtils {

	public static String mkString(List list, final String delimiter, final String surrounder) {

		if (list != null) {
			StringBuilder sb = new StringBuilder(64);

			for (Object item : list) {
				if (surrounder != null) {
					sb.append(surrounder);
				}
				sb.append(item.toString());
				if (surrounder != null) {
					sb.append(surrounder);
				}
				sb.append(delimiter);
			}

			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		} else {
			return "";
		}

	}

}
