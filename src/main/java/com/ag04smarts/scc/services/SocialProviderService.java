package com.ag04smarts.scc.services;

public interface SocialProviderService {
    String getAuthorizationUri(String provider);
    String getAccessTokenUri(String provider);
    String getUserInfoUri(String provider);
}
