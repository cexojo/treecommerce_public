package ca.pivotree.treecommerce.controller;

import static ca.pivotree.treecommerce.core.controller.AbstractController.API_ROOT;

import ca.pivotree.treecommerce.core.constants.PatchOperation;
import ca.pivotree.treecommerce.core.controller.RequestController;
import ca.pivotree.treecommerce.facade.credential.CredentialFacade;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.response.ResponseBuilder;
import io.quarkus.security.Authenticated;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Slf4j
@Path(API_ROOT + "/credential")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Tag(name = "Credential", description = "Controller for operation related to credentials")
@RequestScoped
public class CredentialController extends AbstractSecuredController {
	@Inject
	CredentialFacade credentialFacade;

	@Inject
	RequestController requestController;

	@POST
	@Path("/do-login")
	@Operation(summary = "Performs an authentication", description = "Retrieves a valid JWT for the credential that matches with the provided username and password.")
	@APIResponses({
			@APIResponse(responseCode = "200", description = "The provided credentials match", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Person.class))),
			@APIResponse(responseCode = "400", description = "The username and password don't match with a valid credential")})
	public Response doLogin(@Parameter(description = "Username", required = true) @FormParam("username") String username,
			@Parameter(description = "Password", required = true) @FormParam("password") String password) {

		try {
			return ResponseBuilder.ok(credentialFacade.doLogin(username, password, (Site) requestController.getSite()));
		} catch (Exception e) {
			log.debug("", e);
			return ResponseBuilder.badRequest(e);
		}
	}

	@POST
	@Path("/do-impersonate")
	@Operation(summary = "Performs an authentication and impersonates the logged user as the given customer", description = "Retrieves a valid JWT for the credential that matches with the provided username and password.")
	@APIResponses({
			@APIResponse(responseCode = "200", description = "The provided credentials match and the user could be impersonated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Person.class))),
			@APIResponse(responseCode = "400", description = "The username and password don't match with a valid credential, "
					+ "or the user to impersonate couldn't be impersonated")})
	public Response doImpersonate(
			@Parameter(description = "Username", required = true) @FormParam("username") String username,
			@Parameter(description = "Password", required = true) @FormParam("password") String password,
			@Parameter(description = "Username to impersonate", required = true) @FormParam("usernameToImpersonate") String usernameToImpersonate) {

		try {
			return ResponseBuilder.ok(credentialFacade.doImpersonate(username, password, usernameToImpersonate, (Site) requestController.getSite()));
		} catch (Exception e) {
			log.debug("", e);
			return ResponseBuilder.badRequest(e);
		}
	}

	@GET
	@Path("/is-impersonating")
	@Operation(summary = "Is the authenticated user impersonating someone?", description = "Returns the username being impersonated in case the authenticated "
			+ "user was impersonating someone")
	@APIResponses({
			@APIResponse(responseCode = "200", description = "The request could be resolved and a user was impersonated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Person.class))),
			@APIResponse(responseCode = "204", description = "The request could be resolved and no user was impersonated")})
	@Authenticated
	public Response isImpersonating() {
		try {
			return ResponseBuilder.ok(credentialFacade.isImpersonating());
		} catch (Exception e) {
			log.debug("", e);
			return ResponseBuilder.noContent();
		}
	}

	@PATCH
	@Path("/modify-roles")
	@Operation(summary = "Modifies the list of roles for a credential", description = "Adds or removes a list of roles to the credential of the given username")
	@APIResponses({
			@APIResponse(responseCode = "200", description = "The operation could be performed correctly"),
			@APIResponse(responseCode = "400", description = "There was an error performing the operation")})
	@RolesAllowed({ROLES.ADMIN, ROLES.CREDENTIAL_MANAGER})
	public Response addRoles(
			@Parameter(description = "Username", required = true) @FormParam("username") String username,
			@Parameter(description = "Roles as comma separated values", required = true) @FormParam("roles") String roles,
			@Parameter(description = "Operation to perform", required = true) @FormParam("operation") PatchOperation operation) {
		try {
			switch (operation) {
				case ADD:
					credentialFacade.addRoles(username, roles);
					break;
				case REMOVE:
					credentialFacade.removeRoles(username, roles);
					break;
			}

			return ResponseBuilder.ok();
		} catch (Exception e) {
			log.debug("", e);
			return ResponseBuilder.badRequest(e);
		}
	}

	@PATCH
	@Path("/modify-sites")
	@Operation(summary = "Modifies the list of sites for a credential", description = "Adds or removes a list of sites to the credential of the given username")
	@APIResponses({
			@APIResponse(responseCode = "200", description = "The operation could be performed correctly"),
			@APIResponse(responseCode = "400", description = "There was an error performing the operation")})
	@RolesAllowed({ROLES.ADMIN, ROLES.CREDENTIAL_MANAGER})
	public Response addSites(
			@Parameter(description = "Username", required = true) @FormParam("username") String username,
			@Parameter(description = "Site UIDs as comma separated values", required = true) @FormParam("sites") String sites,
			@Parameter(description = "Operation to perform", required = true) @FormParam("operation") PatchOperation operation) {
		try {
			switch (operation) {
				case ADD:
					credentialFacade.addSites(username, sites);
					break;
				case REMOVE:
					throw new UnsupportedOperationException();
			}

			return ResponseBuilder.ok();
		} catch (Exception e) {
			log.debug("", e);
			return ResponseBuilder.badRequest(e);
		}
	}
}