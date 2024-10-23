package ca.pivotree.treecommerce.core.exceptions;

/**
 * Created by cexojo on 18/04/2020
 */

public class TreeException extends RuntimeException {
	public TreeException() {
		super();
	}

	public TreeException(String msg) {
		super(msg);
	}

	public TreeException(String msg, Object... args) {
		super(String.format(msg, args));
	}

	public TreeException(String msg, Throwable e) {
		super(msg, e);
	}
}
