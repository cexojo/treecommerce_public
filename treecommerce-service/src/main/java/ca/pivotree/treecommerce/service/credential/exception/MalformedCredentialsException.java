package ca.pivotree.treecommerce.service.credential.exception;

/**
 * Created by cexojo on 07/04/2020
 */

public class MalformedCredentialsException extends CredentialsException {
	public MalformedCredentialsException(String username) {
		super("The credentials are malformed for the user " + username);
	}
}
