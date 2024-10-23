package ca.pivotree.treecommerce.service.credential.exception;

/**
 * Created by cexojo on 07/04/2020
 */

public class ExpiredPasswordCredentialsException extends CredentialsException {

	public ExpiredPasswordCredentialsException(String username) {
		super("Password has expired for user " + username);
	}
}
