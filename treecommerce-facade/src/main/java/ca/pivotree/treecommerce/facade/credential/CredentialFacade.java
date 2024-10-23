package ca.pivotree.treecommerce.facade.credential;

import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import java.util.Optional;

/**
 * Created by cexojo on 07/04/2020
 */

public interface CredentialFacade {
	/**
	 * Performs a login with the given username and password, and if succeeds, then it returns a JWT for authorization purposes.
	 *
	 * @param username
	 * @param password
	 * @param site
	 * @return
	 */
	String doLogin(String username, String password, Site site);

	/**
	 * Performs a login with the given username and password, and if succeeds, then it returns a JWT for authorization purposes containing the person being
	 * impersonated.
	 *
	 * @param username
	 * @param password
	 * @param usernameToImpersonate
	 * @return
	 */
	String doImpersonate(String username, String password, String usernameToImpersonate, Site site);

	/**
	 * Returns the username of the {@link Person} being impersonated.
	 *
	 * @return the username of the {@link Person} being impersonated.
	 */
	Optional<String> isImpersonating();

	/**
	 * Adds a list of roles to the credential of the given username.
	 *
	 * @param username the username
	 * @param roles    the list of roles to add, separated by comma
	 */
	void addRoles(String username, String roles);

	/**
	 * Removes a list of roles from the credential of the given username.
	 *
	 * @param username the username
	 * @param roles    the list of roles to remove, separated by comma
	 */
	void removeRoles(String username, String roles);

	/**
	 * Adds a list of roles to the credential of the given username.
	 *
	 * @param username the username
	 * @param sites    the list of sites to add, separated by comma
	 */
	void addSites(String username, String sites);
}
