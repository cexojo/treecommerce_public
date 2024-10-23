package ca.pivotree.treecommerce.filter;

import ca.pivotree.treecommerce.core.utils.TreeUtils;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Priority;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.core.interception.jaxrs.PostMatchContainerRequestContext;

/**
 * Created by cexojo on 18/04/2020
 */
@Provider
@Priority(1020)
@Slf4j
public class LanguageFilter extends AbstractRequestFilter {
	private static final String LANG_PARAM = "lang";
	private static final String LANG_HEADER = "Accept-Language";

	@Override
	public void filterInternal(PostMatchContainerRequestContext context) {
		String lang = Stream.<Supplier<String>>of(
				() -> getQueryParam(LANG_PARAM),
				() -> getHeader(LANG_HEADER))
				.map(Supplier::get)
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);

		if (StringUtils.isNotBlank(lang)) {
			log.debug("Setting request locale to {}.", lang);
			TreeUtils.getRequestController().setLocale(lang);
		}
	}
}