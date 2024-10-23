package ca.pivotree.treecommerce.core.exceptions.model;

import ca.pivotree.treecommerce.core.exceptions.TreeException;

/**
 * Created by cexojo on 14/04/2020
 */

public class TreeSaveModelException extends TreeException {
	public TreeSaveModelException(String message, Throwable e) {
		super(message, e);
	}
}
