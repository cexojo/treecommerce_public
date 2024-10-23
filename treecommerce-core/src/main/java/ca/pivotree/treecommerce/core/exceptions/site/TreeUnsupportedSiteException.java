package ca.pivotree.treecommerce.core.exceptions.site;

import ca.pivotree.treecommerce.core.exceptions.TreeException;

/**
 * Created by cexojo on 18/04/2020
 */

public class TreeUnsupportedSiteException extends TreeException {
	public TreeUnsupportedSiteException(String siteUid) {
		super("The site '%s' is not configured on the system", siteUid);
	}
}
