package com.ag04smarts.scc.repository;

import com.ag04smarts.scc.model.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthUserRepository extends JpaRepository<OauthUser, Long> {
    Optional<OauthUser> findByOauthIdAndName(String oauthId, String name);
}
