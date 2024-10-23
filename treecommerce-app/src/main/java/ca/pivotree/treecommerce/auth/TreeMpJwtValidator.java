package ca.pivotree.treecommerce.auth;

import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.smallrye.jwt.runtime.auth.MpJwtValidator;
import io.quarkus.smallrye.jwt.runtime.auth.QuarkusJwtCallerPrincipal;
import io.smallrye.jwt.auth.principal.DefaultJWTTokenParser;
import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import io.smallrye.jwt.auth.principal.ParseException;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.jwt.Claims;
import org.jboss.logging.Logger;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.JwtContext;

/**
 * Created by cexojo on 20/04/2020
 */

@SuppressWarnings({"unchecked", "unsafe"})
@ApplicationScoped
public class TreeMpJwtValidator implements IdentityProvider<TreeTokenAuthenticationRequest> {
	private static final Logger log = Logger.getLogger(MpJwtValidator.class);
	final JWTAuthContextInfo authContextInfo;
	private final DefaultJWTTokenParser parser = new DefaultJWTTokenParser();

	public TreeMpJwtValidator() {
		this.authContextInfo = null;
	}

	@Inject
	public TreeMpJwtValidator(JWTAuthContextInfo authContextInfo) {
		this.authContextInfo = authContextInfo;
	}

	@Override
	public Class<TreeTokenAuthenticationRequest> getRequestType() {
		return TreeTokenAuthenticationRequest.class;
	}

	@Override
	public CompletionStage<SecurityIdentity> authenticate(TreeTokenAuthenticationRequest request, AuthenticationRequestContext context) {
		try {
			JwtContext jwtContext = this.parser.parse(request.getToken().getToken(), this.authContextInfo);
			JwtClaims claims = jwtContext.getJwtClaims();
			String name = claims.getClaimValue(Claims.upn.name(), String.class);
			if (name == null) {
				name = claims.getClaimValue(Claims.preferred_username.name(), String.class);
				if (name == null) {
					name = claims.getSubject();
				}
			}

			QuarkusJwtCallerPrincipal principal = new QuarkusJwtCallerPrincipal(name, claims);
			return CompletableFuture
					.completedFuture(QuarkusSecurityIdentity.builder().setPrincipal(principal)
							.addRoles(new HashSet(claims.getStringListClaimValue(Claims.groups.name())))
							.addAttribute("quarkus.user", principal).build());
		} catch (MalformedClaimException | ParseException e) {
			log.debug("Authentication failed", e);
			CompletableFuture<SecurityIdentity> cf = new CompletableFuture();
			cf.completeExceptionally(new AuthenticationFailedException(e));
			return cf;
		}
	}
}
