package ca.pivotree.treecommerce.controller;

import ca.pivotree.treecommerce.core.controller.AbstractController;
import ca.pivotree.treecommerce.service.jwt.JwtService;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbstractSecuredController extends AbstractController {
	@Inject
	JwtService jwtService;

	public Optional<String> userBeingImpersonated() {
		return jwtService.getUserIdBeingImpersonated();
	}

	public String effectiveUser() {
		return jwtService.getEffectiveUserPk();
	}
}