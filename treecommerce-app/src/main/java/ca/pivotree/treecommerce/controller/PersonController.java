package ca.pivotree.treecommerce.controller;

import static ca.pivotree.treecommerce.core.controller.AbstractController.API_ROOT;

import ca.pivotree.treecommerce.facade.person.PersonFacade;
import ca.pivotree.treecommerce.facade.person.datafetcher.person.AllPeoplePersonDataFetcher;
import ca.pivotree.treecommerce.facade.person.datafetcher.person.FindByEmailPersonDataFetcher;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.response.ResponseBuilder;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Created by cexojo on 04/04/2020
 */
@Slf4j
@Path(API_ROOT + "/person")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Person", description = "Controller for operation with persons")
public class PersonController extends AbstractSecuredGraphQLController {
	private static final String GRAPHQL_SCHEMA = "person";

	@Inject
	AllPeoplePersonDataFetcher allPeoplePersonDataFetcher;

	@Inject
	FindByEmailPersonDataFetcher findByEmailPersonDataFetcher;

	@Inject
	PersonFacade personFacade;

	@PostConstruct
	public void init() {
		super.init(GRAPHQL_SCHEMA);
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(
			@Parameter(description = "First name", required = true) @FormParam("firstName") String firstName,
			@Parameter(description = "Last name", required = true) @FormParam("lastName") String lastName,
			@Parameter(description = "Email", required = true) @FormParam("email") String email,
			@Parameter(description = "birthDate", required = true) @FormParam("birthDate") LocalDate birthDate,
			@Parameter(description = "Username", required = true) @FormParam("username") String username,
			@Parameter(description = "Password", required = true) @FormParam("password") String password) {
		try {
			Person person = personFacade.create(firstName, lastName, email, birthDate, username, password);
			return ResponseBuilder.ok(person.getPk());
		} catch (Exception e) {
			log.debug("", e);
			return ResponseBuilder.badRequest(e);
		}
	}

	@GET
	public Response get(
			@QueryParam("query") String query,
			@QueryParam("operationName") String operationName,
			@QueryParam("variables") String variables) {
		return executeGraphQLRequest(query, operationName, variables);
	}

	protected RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type(
						TypeRuntimeWiring.newTypeWiring("Query")
								.dataFetcher("allPeople", allPeoplePersonDataFetcher))
				.type(
						TypeRuntimeWiring.newTypeWiring("Query")
								.dataFetcher("findByEmail", findByEmailPersonDataFetcher))
				.type(
						TypeRuntimeWiring.newTypeWiring("Mutation")
								.dataFetcher("createPerson", findByEmailPersonDataFetcher))
				.build();
	}


}
