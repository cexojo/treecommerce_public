package ca.pivotree.treecommerce.dao.impl;

import ca.pivotree.treecommerce.core.dao.mongo.AbstractMongoDao;
import ca.pivotree.treecommerce.dao.SiteDao;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.model.definitions.SiteModelDefinition;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by cexojo on 18/04/2020
 */

@SuppressWarnings({"unused"})
@ApplicationScoped
public class SiteDaoImpl extends AbstractMongoDao<Site> implements SiteDao {
	public SiteDaoImpl() {
		super(Site::new);
	}

	@Override
	public Optional<Site> findByUid(String uid) {
		return find(SiteModelDefinition._UID, uid).firstResultOptional();
	}
}