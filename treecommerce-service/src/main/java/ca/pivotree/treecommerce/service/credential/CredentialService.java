package ca.pivotree.treecommerce.service.credential;

import ca.pivotree.treecommerce.model.Credential;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import java.util.List;
import java.util.Optional;

/**
 * Created by cexojo on 07/04/2020
 */

public interface CredentialService {
	Optional<Credential> getByUsername(String username);
	Optional<Credential> getByUsernameAndPassword(String username, String password);
	Optional<Credential> getByPrincipalId(String principalId);
	boolean isExpired(Credential credential);
	boolean isGrantedForSite(Credential credential, Site site);
	void addRoles(Credential credential, List<String> roles);
	void removeRoles(Credential credential, List<String> roles);
	void addSites(Credential credential, List<String> sites);
	void removeSites(Credential credential, List<String> sites);
	void create(Person person, String username, String password);
	void save(Credential credential);
}
