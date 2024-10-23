package ca.pivotree.treecommerce.model.definitions;

import ca.pivotree.treecommerce.core.model.definition.AbstractModelDefinition;
import ca.pivotree.treecommerce.model.Cart;
import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Created by cexojo on 06/04/2020
 */
public class CartModelDefinition extends AbstractModelDefinition {
	public static final String __TYPE = Cart.class.getSimpleName();
	public static final String _CODE = "code";
	public static final String _SITE = "site";
	public static final String _PUBLICCODE = "publicCode";
	public static final String _OWNERID = "ownerId";
	public static final String _STATUS = "status";

	public static final String __STATUS_LIVE = "live";
	public static final String __STATUS_SAVED = "saved";
	public static final List<String> __STATUSES = List.of(__STATUS_LIVE);

	private static final List<String> ALL_PUBLIC_FIELDS = ImmutableList.of(_CODE, _SITE, _PUBLICCODE, _OWNERID, _STATUS);
	private static final List<String> MIN_FIELDS = ImmutableList.of(_CODE, _SITE, _PUBLICCODE, _OWNERID, _STATUS);
	private static final List<String> DEFAULT_FIELDS = ImmutableList.of(_CODE, _SITE, _PUBLICCODE, _OWNERID, _STATUS);
	private static final List<String> FULL_FIELDS = ImmutableList.of(_CODE, _SITE, _PUBLICCODE, _OWNERID, _STATUS);

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
