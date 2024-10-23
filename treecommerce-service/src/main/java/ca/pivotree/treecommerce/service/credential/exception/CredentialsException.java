package ca.pivotree.treecommerce.service.credential.exception;

/**
 * Created by cexojo on 07/04/2020
 */

public class CredentialsException extends RuntimeException {

	public CredentialsException(String message) {
		super(message);
	}

	public CredentialsException(String message, Throwable e) {
		super(message, e);
	}
}
