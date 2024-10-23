package ca.pivotree.treecommerce.auth;

import ca.pivotree.treecommerce.model.BearerToken;
import ca.pivotree.treecommerce.service.jwt.JwtService;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.quarkus.security.credential.TokenCredential;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.smallrye.jwt.runtime.auth.JWTAuthMechanism;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport.Type;
import io.smallrye.jwt.auth.AbstractBearerTokenExtractor;
import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cexojo on 19/04/2020
 */
@SuppressWarnings({"deprecation", "unchecked", "unsafe"})
@ApplicationScoped
@Alternative
@Priority(1)
public class TreeAuthMechanism extends JWTAuthMechanism {
	protected static final String BEARER_PREFIX = "Tree ";

	@Inject
	JWTAuthContextInfo authContextInfo;

	@Inject
	JwtService jwtService;

	public TreeAuthMechanism() {
	}

	public CompletionStage<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
		String bearerTokenUid = new TreeBearerTokenExtractor(this.authContextInfo, context).getBearerToken();
		if (StringUtils.isBlank(bearerTokenUid)) {
			return (CompletionStage) CompletableFuture.completedFuture(null);
		}

		Optional<BearerToken> bearerToken = jwtService.getBearerTokenByUid(bearerTokenUid);
		if (bearerToken.isEmpty()) {
			return (CompletionStage) CompletableFuture.completedFuture(null);
		}

		String jwtToken = bearerToken.get().getContent();
		return (CompletionStage) (StringUtils.isNotBlank(jwtToken) ?
				identityProviderManager.authenticate(new TreeTokenAuthenticationRequest(new TokenCredential(jwtToken, "tree_bearer")))
				: CompletableFuture.completedFuture(null));
	}

	public CompletionStage<ChallengeData> getChallenge(RoutingContext context) {
		ChallengeData result = new ChallengeData(HttpResponseStatus.UNAUTHORIZED.code(), HttpHeaderNames.WWW_AUTHENTICATE, "Bearer Tree {token}");
		return CompletableFuture.completedFuture(result);
	}

	public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
		return Collections.singleton(TreeTokenAuthenticationRequest.class);
	}

	public HttpCredentialTransport getCredentialTransport() {
		String tokenHeaderName = this.authContextInfo.getTokenHeader();
		if ("Cookie".equals(tokenHeaderName)) {
			String tokenCookieName = this.authContextInfo.getTokenCookie();
			if (tokenCookieName == null) {
				tokenCookieName = "Bearer";
			}

			return new HttpCredentialTransport(Type.COOKIE, tokenCookieName);
		} else {
			return new HttpCredentialTransport(Type.OTHER_HEADER, tokenHeaderName);
		}
	}

	private static class TreeBearerTokenExtractor extends AbstractBearerTokenExtractor {
		private final RoutingContext httpExchange;

		TreeBearerTokenExtractor(JWTAuthContextInfo authContextInfo, RoutingContext exchange) {
			super(authContextInfo);
			this.httpExchange = exchange;
		}

		protected String getHeaderValue(String headerName) {
			return this.httpExchange.request().headers().get(headerName);
		}

		protected String getCookieValue(String cookieName) {
			String cookieHeader = this.httpExchange.request().headers().get(HttpHeaders.COOKIE);
			if (cookieHeader != null && this.httpExchange.cookieCount() == 0) {
				Set<Cookie> nettyCookies = ServerCookieDecoder.STRICT.decode(cookieHeader);

				for (Cookie cookie : nettyCookies) {
					if (cookie.name().equals(cookieName)) {
						return cookie.value();
					}
				}
			}

			io.vertx.ext.web.Cookie cookie = this.httpExchange.getCookie(cookieName);
			return cookie != null ? cookie.getValue() : null;
		}

		@Override
		public String getBearerToken() {
			String headerContent = super.getBearerToken();
			if (StringUtils.isNotBlank(headerContent)) {
				return headerContent.replace(BEARER_PREFIX, "");
			}

			return null;
		}
	}

}
