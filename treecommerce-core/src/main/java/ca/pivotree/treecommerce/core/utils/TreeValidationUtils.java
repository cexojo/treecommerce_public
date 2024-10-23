package ca.pivotree.treecommerce.core.utils;

import java.util.Objects;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cexojo on 04/04/2020
 */

public class TreeValidationUtils {

	public static void throwIfNull(Object o, RuntimeException e) {
		if (Objects.isNull(o)) {
			throw e;
		}
	}

	public static void throwIfNull(Object o, String message) {
		throwIfNull(o, new IllegalArgumentException(message));
	}

	public static void throwIfFalse(Boolean b, RuntimeException e) {
		if (BooleanUtils.isNotTrue(b)) {
			throw e;
		}
	}

	public static void throwIfTrue(Boolean b, String message) {
		throwIfTrue(b, new IllegalArgumentException(message));
	}

	public static void throwIfTrue(Boolean b, RuntimeException e) {
		if (BooleanUtils.isTrue(b)) {
			throw e;
		}
	}

	public static void throwIfFalse(Boolean b, String message) {
		throwIfFalse(b, new IllegalArgumentException(message));
	}

	public static void throwIfBlank(String s, String message) {
		if (StringUtils.isBlank(s)) {
			throw new IllegalArgumentException(message);
		}
	}
}
