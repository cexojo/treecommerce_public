package ca.pivotree.treecommerce.service.credential.exception;

import ca.pivotree.treecommerce.model.Site;

/**
 * Created by cexojo on 07/04/2020
 */

public class InvalidCredentialsException extends CredentialsException {

	public InvalidCredentialsException(String username) {
		super("Invalid credentials for username '" + username + "'");
	}

	public InvalidCredentialsException(String username, Site site) {
		super("Invalid credentials for username '" + username + "' and site '" + (site != null ? site.getUid() : null) + "'");
	}
}
