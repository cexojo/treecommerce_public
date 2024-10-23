package ca.pivotree.treecommerce.dao;

import ca.pivotree.treecommerce.core.dao.TreeDao;
import ca.pivotree.treecommerce.model.Credential;
import java.util.Optional;

/**
 * Created by cexojo on 04/04/2020
 */

public interface CredentialDao extends TreeDao<Credential> {
	Optional<Credential> getByUsername(String username);
	Optional<Credential> getByUsernameAndPassword(String username, String password);
	Optional<Credential> getByPrincipalId(String principalId);
	void save(Credential credential);
}
