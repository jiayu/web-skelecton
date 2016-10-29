package me.jamc.skeleton.oauth;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;


/**
 * Created by Jamc on 10/29/16.
 */
public class OAuthCommonApi extends DefaultApi20 {

    public static final String OAUTH_GITHUB = "github";

    private String clientId;
    private String clientSecret;
    private String accessTokenPoint;
    private String authorizationBaseUrl;
    private String callBackUrl;

    private OAuth20Service service;
    private String platform;

    public OAuthCommonApi(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenPoint(String accessTokenPoint) {
        this.accessTokenPoint = accessTokenPoint;
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
        return Verb.POST;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return accessTokenPoint;
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
            service = new ServiceBuilder()
                    .apiKey(clientId)
                    .apiSecret(clientSecret)
                    .callback(callBackUrl)
                    .build(this);
        }
        return service;
    }
}
