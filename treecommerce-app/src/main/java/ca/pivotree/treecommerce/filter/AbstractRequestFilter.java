package ca.pivotree.treecommerce.filter;

import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import org.jboss.resteasy.core.interception.jaxrs.PostMatchContainerRequestContext;
import org.jboss.resteasy.spi.HttpRequest;

/**
 * Created by cexojo on 18/04/2020
 */
public abstract class AbstractRequestFilter implements ContainerRequestFilter {
	HttpRequest request;

	@Override
	public final void filter(ContainerRequestContext context) {
		if (!(context instanceof PostMatchContainerRequestContext)) {
			return;
		}

		request = ((PostMatchContainerRequestContext) context).getHttpRequest();
		TreeValidationUtils.throwIfNull(request, "Cannot retrieve the request object");

		filterInternal((PostMatchContainerRequestContext) context);
	}

	public abstract void filterInternal(PostMatchContainerRequestContext context);

	protected String getQueryParam(String queryParam) {
		return request.getUri().getQueryParameters().getFirst(queryParam);
	}

	protected String getHeader(String header) {
		return request.getHttpHeaders().getHeaderString(header);
	}
}
