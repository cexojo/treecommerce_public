package ca.pivotree.treecommerce.service.site.impl;

import ca.pivotree.treecommerce.dao.SiteDao;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.service.site.SiteService;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by cexojo on 07/04/2020
 */

@ApplicationScoped
public class SiteServiceImpl implements SiteService {
	@Inject
	SiteDao siteDao;

	@Override
	public Optional<Site> getByUid(String uid) {
		return siteDao.findByUid(uid);
	}
}
