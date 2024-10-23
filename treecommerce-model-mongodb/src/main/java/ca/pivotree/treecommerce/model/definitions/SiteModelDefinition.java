package ca.pivotree.treecommerce.model.definitions;

import ca.pivotree.treecommerce.core.model.definition.AbstractModelDefinition;
import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Created by cexojo on 18/04/2020
 */
public class SiteModelDefinition extends AbstractModelDefinition {
	public static final String __TYPE = "Site";
	public static final String _UID = "uid";
	public static final String _DESCRIPTION = "description";
	public static final String _CHECKEDOUT = "checkedOut";
	public static final String _TOTAL = "total";

	private static final List<String> ALL_PUBLIC_FIELDS = ImmutableList.of(_UID, _DESCRIPTION);
	private static final List<String> MIN_FIELDS = ImmutableList.of(_UID);
	private static final List<String> DEFAULT_FIELDS = ImmutableList.of(_UID);
	private static final List<String> FULL_FIELDS = ImmutableList.of(_UID, _DESCRIPTION);

	@Override
	public String getType() {
		return __TYPE;
	}

	@Override
	public List<String> getAllPublicFields() {
		return ALL_PUBLIC_FIELDS;
	}

	@Override
	public List<String> getMinFields() {
		return MIN_FIELDS;
	}

	@Override
	public List<String> getDefaultFields() {
		return DEFAULT_FIELDS;
	}

	@Override
	public List<String> getPublicFields() {
		return FULL_FIELDS;
	}
}
