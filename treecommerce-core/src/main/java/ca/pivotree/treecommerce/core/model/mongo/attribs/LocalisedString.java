package ca.pivotree.treecommerce.core.model.mongo.attribs;

import ca.pivotree.treecommerce.core.controller.RequestController;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.enterprise.inject.Instance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Created by cexojo on 14/04/2020
 */
@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalisedString {
	private Map<String, String> localisedStrings = new HashMap<>();

	public LocalisedString(String... args) {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("Odd number of elements");
		}

		for (int i = 0; i < args.length; i += 2) {
			localisedStrings.put(args[i], args[i + 1]);
		}
	}

	public String get() {
		// Recover the copy of the Request controller for this specific request, and from there gather the user language
		Instance<RequestController> instance = Arc.container().beanManager().createInstance().select(RequestController.class);

		if (!instance.isResolvable()) {
			throw new RuntimeException("Cannot resolve request controller");
		}

		return get(instance.get().getLocale());
	}

	public String get(Locale locale) {
		return get(locale.toString());
	}

	public String get(String languageIso) {
		return ObjectUtils.firstNonNull(localisedStrings.get(languageIso), localisedStrings.get(localisedStrings.keySet().iterator().next()), "");
	}

	@Override
	public String toString() {
		return get();
	}
}
