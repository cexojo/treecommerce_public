package ca.pivotree.treecommerce.service.site;

import ca.pivotree.treecommerce.model.Site;
import java.util.Optional;

/**
 * Created by cexojo on 18/04/2020
 */

public interface SiteService {
	Optional<Site> getByUid(String uid);
}
