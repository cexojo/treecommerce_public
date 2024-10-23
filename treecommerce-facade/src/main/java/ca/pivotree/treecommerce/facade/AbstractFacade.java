package ca.pivotree.treecommerce.facade;

import ca.pivotree.treecommerce.core.controller.RequestController;
import ca.pivotree.treecommerce.core.utils.TreeUtils;
import ca.pivotree.treecommerce.model.Credential;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.service.jwt.JwtService;
import java.security.PrivateKey;
import java.util.Objects;
import javax.inject.Inject;

/**
 * Created by cexojo on 19/04/2020
 */

public abstract class AbstractFacade {
	@Inject
	JwtService jwtService;

	private RequestController requestController;

	protected Site getSite() {
		return (Site) resolveRequestController().getSite();
	}

	protected Person getPerson() {
		return (Person) resolveRequestController().getPerson();
	}

	protected String getEffectiveUserId() {
		return jwtService.getEffectiveUserPk();
	}

	protected String getToken(Credential credential, Credential impersonatedCredential, PrivateKey privateKey, String privateKeyId) {
		return jwtService.getToken(credential, impersonatedCredential, privateKey, privateKeyId);
	}

	private RequestController resolveRequestController() {
		if (Objects.isNull(requestController)) {
			requestController = TreeUtils.getRequestController();
		}

		return requestController;
	}
}
