package ca.pivotree.treecommerce.response;

import java.util.Optional;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;

/**
 * Created by cexojo on 07/04/2020
 */

public class ResponseBuilder {
	private static final int STATUS_OK = 200;
	private static final int STATUS_NO_CONTENT = 204;
	private static final int STATUS_BAD_REQUEST = 400;

	private ResponseBuilder() {

	}

	public static Response ok() {
		return ok(STATUS_OK, null);
	}

	public static Response ok(Object o) {
		return ok(STATUS_OK, o);
	}

	public static Response okOrNoContent(Optional optional) {
		return optional.isPresent() ? Response.ok(optional.get()).build() : Response.noContent().build();
	}

	public static Response noContent() {
		return ok(STATUS_NO_CONTENT, null);
	}

	public static Response badRequest(Exception exception) {
		return ko(STATUS_BAD_REQUEST, exception);
	}

	private static Response ok(int code, Object o) {
		return Response.status(code).entity(o != null ? new Document().append("data", o) : null).build();
	}

	private static Response ko(int code, Exception exception) {
		return Response.status(code)
				.entity(
						new Document()
								.append("error", exception.getClass().getSimpleName())
								.append("message", ObjectUtils.firstNonNull(exception.getMessage(), ""))
								.append("code", code))
				.build();
	}
}