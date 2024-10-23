package ca.pivotree.treecommerce.core.utils;

import ca.pivotree.treecommerce.core.controller.RequestController;
import io.quarkus.arc.Arc;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.inject.Instance;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cexojo on 08/04/2020
 */

public class TreeUtils {
	public static int currentTimeInSecs() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static RequestController getRequestController() {
		Instance<RequestController> instance = Arc.container().beanManager().createInstance().select(RequestController.class);

		if (!instance.isResolvable()) {
			throw new RuntimeException("Cannot resolve request controller");
		}

		return instance.get();
	}

	public static List<String> splitByComma(String s) {
		if (StringUtils.isBlank(s)) {
			return Collections.emptyList();
		}

		return Arrays.stream(s.split(",")).distinct().filter(StringUtils::isNotBlank).collect(Collectors.toList());
	}
}
