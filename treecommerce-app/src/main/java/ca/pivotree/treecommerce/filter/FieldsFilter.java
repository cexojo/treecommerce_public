package ca.pivotree.treecommerce.filter;

import ca.pivotree.treecommerce.core.utils.TreeUtils;
import javax.annotation.Priority;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.core.interception.jaxrs.PostMatchContainerRequestContext;

/**
 * Created by cexojo on 21/04/2020
 */
@Provider
@Priority(1030)
public class FieldsFilter extends AbstractRequestFilter {
	private static final String FIELDS_PARAM = "fields";

	@Override
	public void filterInternal(PostMatchContainerRequestContext context) {
		TreeUtils.getRequestController().setFields(getQueryParam(FIELDS_PARAM));
	}
}