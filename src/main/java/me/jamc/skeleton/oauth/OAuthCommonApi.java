package me.jamc.skeleton.oauth;

import com.google.common.base.Preconditions;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.InitializingBean;

import java.util.Random;


/**
 * Created by Jamc on 10/29/16.
 */
public class OAuthCommonApi extends DefaultApi20 {

    public static final String OAUTH_GITHUB = "github";

    private String clientId;
    private String clientSecret;
    private String accessTokenEndpoint;
    private String refreshTokenEndpoint;
    private String authorizationBaseUrl;
    private String callBackUrl;
    private String scope;
    private Boolean state;
    private String verb;

    private OAuth20Service service;
    private String platform;

    public OAuthCommonApi(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setVerb(String verb) {
        this.verb = verb.toUpperCase();
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setRefreshTokenEndpoint(String refreshTokenEndpoint) {
        this.refreshTokenEndpoint = refreshTokenEndpoint;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenEndpoint(String accessTokenEndpoint) {
        this.accessTokenEndpoint = accessTokenEndpoint;
    }

    public void setAuthorizationBaseUrl(String authorizationBaseUrl) {
        this.authorizationBaseUrl = authorizationBaseUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public void setService(OAuth20Service service) {
        this.service = service;
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.valueOf(verb);
    }

    @Override
    public String getRefreshTokenEndpoint() {
        return refreshTokenEndpoint;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return accessTokenEndpoint;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return authorizationBaseUrl;
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return OAuth2AccessTokenExtractor.instance();
    }

    public OAuth20Service service() {
        if (service == null) {
            ServiceBuilder sb = new ServiceBuilder()
                    .apiKey(clientId)
                    .apiSecret(clientSecret)
                    .callback(callBackUrl);

            if (state != null && state) {
                sb.state("secret" + new Random().nextInt(999_999));
            }

            if (!Strings.isNullOrEmpty(scope)) {
                sb.scope(scope);
            }

            service = sb.build(this);
        }
        return service;
    }

    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(clientId, errorMsg("clientId"));
        Preconditions.checkNotNull(clientSecret, errorMsg("clientSecret"));
        Preconditions.checkNotNull(accessTokenEndpoint, errorMsg("accessTokenEndpoint"));
        Preconditions.checkNotNull(authorizationBaseUrl, errorMsg("authorizationBaseUrl"));
        Preconditions.checkNotNull(callBackUrl, errorMsg("callBackUrl"));
        Preconditions.checkNotNull(verb, errorMsg("verb"));
        Preconditions.checkState(Verb.valueOf(verb) instanceof Verb);

        if (Strings.isNullOrEmpty(refreshTokenEndpoint)) {
            refreshTokenEndpoint = accessTokenEndpoint;
        }
    }

    private String errorMsg(String field) {
        return String.format("property %s for %s is required for setting up OAuth profile",
                field, platform);
    }
}
