package me.jamc.skeleton.controller;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import me.jamc.skeleton.oauth.OAuthCommonApi;

/**
 * Created by Jamc on 10/30/16.
 */
@RestController
@RequestMapping(value = "/oauth/")
public class OAuthController {
    @Autowired
    private List<OAuthCommonApi> apis;

    @RequestMapping(value = "/{platform}", method = RequestMethod.GET)
    public String authUrl(@PathVariable String platform) {
        Optional<OAuthCommonApi> opt = getApi(platform);
        if (!opt.isPresent()) {
            return "Don't understand platform";
        }

        String authorizationUrl = opt.get().service().getAuthorizationUrl();

        return authorizationUrl;
    }

    @RequestMapping(value = "/{platform}/{code}", method = RequestMethod.GET)
    @ResponseBody
    public OAuth2AccessToken accessToken(@PathVariable String platform, @PathVariable String code) throws IOException {
        Optional<OAuthCommonApi> opt = getApi(platform);
        if (!opt.isPresent()) {
            return null;
        }

        OAuthCommonApi api = opt.get();
        final OAuth2AccessToken accessToken = api.service().getAccessToken(code);

        return accessToken;

    }

    private Optional<OAuthCommonApi> getApi(String platform) {
        return apis.stream().filter( a -> platform.equals(a.getPlatform())).findFirst();
    }
}
