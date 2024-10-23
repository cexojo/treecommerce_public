package ca.pivotree.treecommerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cexojo on 04/04/2020
 */
public abstract class AbstractSecuredGraphQLController extends AbstractSecuredController {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractSecuredGraphQLController.class);
	private GraphQL graphQL;

	public void init(String schema) {
		try (InputStream content = this.getClass().getResourceAsStream(String.format("/graphql/%s.graphqls", schema))) {
			byte[] tmp = new byte[1024000];
			int length = content.read(tmp);

			GraphQLSchema graphQLSchema = buildSchema(new String(tmp, 0, length, StandardCharsets.UTF_8));
			this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
		} catch (Exception e) {
			LOG.error("Couldn't initialize the schema '{}'", schema, e);
		}
	}

	private GraphQLSchema buildSchema(String sdl) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
	}

	protected Response executeGraphQLRequest(String query, String operationName, String variables) {
		if (Objects.isNull(graphQL)) {
			return Response.noContent().build();
		}

		ExecutionInput executionInput = ExecutionInput.newExecutionInput()
				.query(query != null ? query : "")
				.operationName(operationName)
				.variables(jsonToMap(variables))
				.build();
		ExecutionResult executionResult = graphQL.execute(executionInput);

		try {
			String json = new ObjectMapper().writeValueAsString(executionResult.toSpecification());
			return Response.ok(json).build();
		} catch (IOException e) {
			throw new RuntimeException("Could not convert object to JSON: " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> jsonToMap(String jsonMap) {
		if (jsonMap == null) {
			return Collections.emptyMap();
		}

		try {
			return (Map<String, Object>) new ObjectMapper().readValue(jsonMap, Map.class);
		} catch (JsonProcessingException e) {
			LOG.error("Failed to convert the Json object to a map");
			return Collections.emptyMap();
		}
	}

	protected abstract RuntimeWiring buildWiring();

}
