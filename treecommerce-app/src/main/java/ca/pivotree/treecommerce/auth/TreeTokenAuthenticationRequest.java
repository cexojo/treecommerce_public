package ca.pivotree.treecommerce.auth;

import io.quarkus.security.credential.TokenCredential;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;

/**
 * Created by cexojo on 20/04/2020
 */

public class TreeTokenAuthenticationRequest extends TokenAuthenticationRequest {
	public TreeTokenAuthenticationRequest(TokenCredential token) {
		super(token);
	}
}
