package com.ag04smarts.scc.services;

import com.ag04smarts.scc.model.AccessToken;
import com.ag04smarts.scc.model.OauthUser;

public interface OauthService {
    AccessToken getAccessToken(String code, String provider);
    OauthUser getUser(AccessToken accessToken, String provider);
    OauthUser loadAndVerifyOauthUser(String jwt, AccessToken accessToken, String name, String oauthId, String provider);
}
