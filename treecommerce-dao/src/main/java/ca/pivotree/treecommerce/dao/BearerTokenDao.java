package ca.pivotree.treecommerce.dao;

import ca.pivotree.treecommerce.core.dao.TreeDao;
import ca.pivotree.treecommerce.model.BearerToken;
import java.util.List;
import java.util.Optional;

/**
 * Created by cexojo on 18/04/2020
 */

public interface BearerTokenDao extends TreeDao<BearerToken> {
	Optional<BearerToken> findByUid(String uid);
	List<BearerToken> findExpired();
}
