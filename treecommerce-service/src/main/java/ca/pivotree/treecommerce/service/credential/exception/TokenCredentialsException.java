package ca.pivotree.treecommerce.service.credential.exception;

/**
 * Created by cexojo on 07/04/2020
 */

public class TokenCredentialsException extends CredentialsException {

	public TokenCredentialsException(String username, Throwable e) {
		super("Error generating the authentication token for the username " + username, e);
	}
}
