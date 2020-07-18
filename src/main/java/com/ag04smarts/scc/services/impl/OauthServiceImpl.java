package com.ag04smarts.scc.services.impl;

import com.ag04smarts.scc.model.AccessToken;
import com.ag04smarts.scc.model.OauthUser;
import com.ag04smarts.scc.repository.OauthUserRepository;
import com.ag04smarts.scc.security.MyUserDetails;
import com.ag04smarts.scc.security.util.JwtUtil;
import com.ag04smarts.scc.services.OauthService;
import com.ag04smarts.scc.services.SocialProviderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OauthServiceImpl implements OauthService {
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final OauthUserRepository oauthUserRepository;
    private final SocialProviderService socialProviderService;

    public OauthServiceImpl(JwtUtil jwtUtil, RestTemplate restTemplate, OauthUserRepository oauthUserRepository,
                            SocialProviderService socialProviderService) {
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
        this.oauthUserRepository = oauthUserRepository;
        this.socialProviderService = socialProviderService;
    }

    @Override
    public AccessToken getAccessToken(String code, String provider) {
        return restTemplate.postForEntity(
                socialProviderService.getAccessTokenUri(provider) +
                        "&code=" + code, null, AccessToken.class).getBody();
    }

    @Override
    public OauthUser getUser(AccessToken accessToken, String provider) {
        if (accessToken != null) {
            HttpHeaders headers = new HttpHeaders();
            String tokenType = accessToken.getToken_type().substring(0, 1).toUpperCase() +
                    accessToken.getToken_type().substring(1);
            headers.set("Authorization", tokenType + " " + accessToken.getAccess_token());
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            Map userInfo = restTemplate.exchange(socialProviderService.getUserInfoUri(provider),
                    HttpMethod.GET, entity, Map.class).getBody();

            if (userInfo != null) {
                String name = (String) userInfo.get("name");
                String id = userInfo.containsKey("sub") ? userInfo.get("sub").toString() : userInfo.get("id").toString();
                String role = "ROLE_USER";

                return new OauthUser(name, role, id);
            }
        }

        return null;
    }

    @Override
    public OauthUser loadAndVerifyOauthUser(String jwt, AccessToken accessToken, String name, String oauthId,
                                            String provider) throws BadCredentialsException {
        OauthUser user = oauthUserRepository.findByOauthIdAndName(oauthId, name).orElseThrow(()
                -> new BadCredentialsException("User not found"));

        if (jwtUtil.validateToken(jwt, new MyUserDetails(user)) && user.equals(this.getUser(accessToken, provider))) {
            return user;
        }

        return null;
    }
}
