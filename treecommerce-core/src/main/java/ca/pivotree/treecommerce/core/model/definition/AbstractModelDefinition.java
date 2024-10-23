package ca.pivotree.treecommerce.core.model.definition;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Created by cexojo on 21/04/2020
 */

public abstract class AbstractModelDefinition {
	public static final String _PK = "pk";

	public static final List<String> ALL_PUBLIC_FIELDS = ImmutableList.of();
	public static final List<String> MIN_FIELDS = ImmutableList.of();
	public static final List<String> DEFAULT_FIELDS = ImmutableList.of();
	public static final List<String> FULL_FIELDS = ImmutableList.of();

	public abstract String getType();
	public abstract List<String> getAllPublicFields();
	public abstract List<String> getMinFields();
	public abstract List<String> getDefaultFields();
	public abstract List<String> getPublicFields();

	public List<String> dtoFields(String dtoSet) {
		switch (dtoSet) {
			case DTO_SETS.MIN:
				return getMinFields();
			case DTO_SETS.DEFAULT:
				return getDefaultFields();
			default:
			case DTO_SETS.FULL:
				return getPublicFields();
		}
	}

	public static final class DTO_SETS {
		public static final String MIN = "MIN";
		public static final String DEFAULT = "DEFAULT";
		public static final String FULL = "FULL";
		public static final List<String> DEFAULT_DTO_SETS = ImmutableList.of(MIN, DEFAULT, FULL);
	}
}
