package me.jamc.skeleton.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.jamc.skeleton.oauth.OAuthCommonApi;

/**
 * Created by Jamc on 11/7/16.
 */
@Configuration
public class BeanConfiguration {

    @Bean(initMethod = "afterPropertiesSet")
    @ConfigurationProperties(prefix = "app.auth.github")
    public OAuthCommonApi OAuthApi() {
        return new OAuthCommonApi(OAuthCommonApi.OAUTH_GITHUB);
    }
}
