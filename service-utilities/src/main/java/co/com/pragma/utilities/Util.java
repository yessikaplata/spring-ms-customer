package co.com.pragma.utilities;

import java.util.List;

public final class Util {
	

	public static boolean isBlank(String str) {
		if (str == null) {
			return true;
		} else {
			return isEmpty(str.trim());
		}
	}

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static int length(String str) {
		return isEmpty(str) ? 0 : str.length();
	}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

}