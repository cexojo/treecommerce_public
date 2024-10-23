package ca.pivotree.treecommerce.core.exceptions.model;

import ca.pivotree.treecommerce.core.exceptions.TreeException;

/**
 * Created by cexojo on 21/04/2020
 */

public class TreeUnknownModelFieldException extends TreeException {
	public TreeUnknownModelFieldException(String field, String type) {
		super("The field '%s' is unknown for the type '%s'.", field, type);
	}
}
