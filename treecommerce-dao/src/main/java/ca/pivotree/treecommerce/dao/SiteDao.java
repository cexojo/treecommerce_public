package ca.pivotree.treecommerce.dao;

import ca.pivotree.treecommerce.core.dao.TreeDao;
import ca.pivotree.treecommerce.model.Site;
import java.util.Optional;

/**
 * Created by cexojo on 18/04/2020
 */

public interface SiteDao extends TreeDao<Site> {
	Optional<Site> findByUid(String uid);
}
