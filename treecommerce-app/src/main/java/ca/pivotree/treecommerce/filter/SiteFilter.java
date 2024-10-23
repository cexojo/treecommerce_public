package ca.pivotree.treecommerce.filter;

import ca.pivotree.treecommerce.core.exceptions.site.TreeUnsupportedSiteException;
import ca.pivotree.treecommerce.core.utils.TreeUtils;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.response.ResponseBuilder;
import ca.pivotree.treecommerce.service.site.SiteService;
import java.util.Optional;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.core.interception.jaxrs.PostMatchContainerRequestContext;

/**
 * Created by cexojo on 18/04/2020
 */
@Provider
@Priority(1010)
public class SiteFilter extends AbstractRequestFilter {
	private static final String SITE_HEADER = "Tree-Site";

	@Inject
	SiteService siteService;

	@Override
	public void filterInternal(PostMatchContainerRequestContext context) {
		String siteUid = getHeader(SITE_HEADER);

		if (StringUtils.isNotBlank(siteUid)) {
			Optional<Site> site = siteService.getByUid(siteUid);

			if (site.isEmpty()) {
				context.abortWith(ResponseBuilder.badRequest(new TreeUnsupportedSiteException(siteUid)));
				return;
			}

			TreeUtils.getRequestController().setSite(site.get());
		}
	}
}