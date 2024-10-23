package ca.pivotree.treecommerce.controller;

import static ca.pivotree.treecommerce.core.controller.AbstractController.API_ROOT;

import ca.pivotree.treecommerce.converter.cart.CartConverter;
import ca.pivotree.treecommerce.facade.cart.CartFacade;
import ca.pivotree.treecommerce.response.ResponseBuilder;
import io.quarkus.security.Authenticated;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Created by cexojo on 04/04/2020
 */
@Slf4j
@Path(API_ROOT + "/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cart", description = "Controller for operations with carts")
public class CartController extends AbstractSecuredController {
	@Inject
	CartFacade cartFacade;

	@Inject
	CartConverter cartConverter;

	@POST
	@Path("/create")
	@RolesAllowed({ROLES.CUSTOMER})
	public Response create() {
		return ResponseBuilder.ok(cartFacade.createLiveCart().getPublicCode());
	}

	@GET
	@Path("/{publicCode}")
	@Authenticated
	public Response getCart(@PathParam("publicCode") String publicCode) {
		try {
			return ResponseBuilder.okOrNoContent(cartFacade.getCartByPublicCode(publicCode));
		} catch (Exception e) {
			log.debug("", e);
			return ResponseBuilder.badRequest(e);
		}
	}
}
