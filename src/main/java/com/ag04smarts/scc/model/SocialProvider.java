package com.ag04smarts.scc.model;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class SocialProvider {
    @NestedConfigurationProperty
    private String clientId;
    @NestedConfigurationProperty
    private String clientSecret;
    @NestedConfigurationProperty
    private String accessTokenUri;
    @NestedConfigurationProperty
    private String userAuthorizationUri;
    @NestedConfigurationProperty
    private String userInfoUri;
    @NestedConfigurationProperty
    private String redirectUri;
    @NestedConfigurationProperty
    private String scope;

    public SocialProvider(){ }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public void setUserAuthorizationUri(String userAuthorizationUri) {
        this.userAuthorizationUri = userAuthorizationUri;
    }

    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getAccessTokenUri() {
        return accessTokenUri + "?client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri;
    }

    public String getUserAuthorizationUri() {
        String userAuthUri = userAuthorizationUri + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&state=\"123\"";
        if (scope != null) {
            userAuthUri += ("&scope=" + scope);
        }
        return userAuthUri;
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
