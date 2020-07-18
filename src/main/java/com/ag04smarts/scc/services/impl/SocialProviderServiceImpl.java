package com.ag04smarts.scc.services.impl;

import com.ag04smarts.scc.model.SocialProvider;
import com.ag04smarts.scc.services.SocialProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class SocialProviderServiceImpl implements SocialProviderService {
    private SocialProvider githubSocialProvider;
    private SocialProvider facebookSocialProvider;
    private SocialProvider googleSocialProvider;

//    @Bean
//    @ConfigurationProperties("facebook")
    private SocialProvider facebookProvider() {
//        return new SocialProvider();
        return facebookSocialProvider;
    };

//    @Bean
//    @ConfigurationProperties("github")
    private SocialProvider githubProvider() {
//        return new SocialProvider();
        return githubSocialProvider;
    };

//    @Bean
//    @ConfigurationProperties("google")
    private SocialProvider googleProvider() {
//        return new SocialProvider();
        return googleSocialProvider;
    };

    public SocialProviderServiceImpl() {
        githubSocialProvider = new SocialProvider();
        githubSocialProvider.setClientId("d8eec1b7da026d8822bf");
        githubSocialProvider.setClientSecret("f7cdcda1a4728f2814769dd8a0d2bee7f6628294");
        githubSocialProvider.setAccessTokenUri("https://github.com/login/oauth/access_token");
        githubSocialProvider.setScope(null);
        githubSocialProvider.setUserAuthorizationUri("https://github.com/login/oauth/authorize");
        githubSocialProvider.setUserInfoUri("https://api.github.com/user");
        githubSocialProvider.setRedirectUri("http://localhost:4200/authenticated/github");

        facebookSocialProvider = new SocialProvider();
        facebookSocialProvider.setClientId("721402388356996");
        facebookSocialProvider.setClientSecret("342366fc51226414bb4b65b688651d6c");
        facebookSocialProvider.setAccessTokenUri("https://graph.facebook.com/oauth/access_token");
        facebookSocialProvider.setScope(null);
        facebookSocialProvider.setUserAuthorizationUri("https://www.facebook.com/dialog/oauth");
        facebookSocialProvider.setUserInfoUri("https://graph.facebook.com/me");
        facebookSocialProvider.setRedirectUri("http://localhost:4200/authenticated/facebook");

        googleSocialProvider = new SocialProvider();
        googleSocialProvider.setClientId("424049827032-vbo2t892unb9gm1q5b7e1l7qcsse7nca.apps.googleusercontent.com");
        googleSocialProvider.setClientSecret("vrLWipd2_2VmmNJ0lrmAhM6Y");
        googleSocialProvider.setAccessTokenUri("https://oauth2.googleapis.com/token");
        googleSocialProvider.setScope("https://www.googleapis.com/auth/userinfo.profile");
        googleSocialProvider.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
        googleSocialProvider.setUserInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
        googleSocialProvider.setRedirectUri("http://localhost:4200/authenticated/google");
    }

    @Override
    public String getAuthorizationUri(String provider) {
        switch (provider) {
            case "facebook":
                return facebookProvider().getUserAuthorizationUri();
            case "github":
                return githubProvider().getUserAuthorizationUri();
            case "google":
                return googleProvider().getUserAuthorizationUri() + "&response_type=code";
            default:
                return null;
        }
    }

    @Override
    public String getAccessTokenUri(String provider) {
        switch (provider) {
            case "facebook":
                return facebookProvider().getAccessTokenUri();
            case "github":
                return githubProvider().getAccessTokenUri();
            case "google":
                return googleProvider().getAccessTokenUri() + "&grant_type=authorization_code";
            default:
                return null;
        }
    }

    @Override
    public String getUserInfoUri(String provider) {
        switch (provider) {
            case "facebook":
                return facebookProvider().getUserInfoUri();
            case "github":
                return githubProvider().getUserInfoUri();
            case "google":
                return googleProvider().getUserInfoUri();
            default:
                return null;
        }
    }
}
