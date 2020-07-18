package com.ag04smarts.scc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "OauthUser")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OauthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String oauthId;
    private String name;
    private String role;

    public OauthUser(String name, String role, String oauthId) {
        this.name = name;
        this.role = role;
        this.oauthId = oauthId;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == OauthUser.class) {
            OauthUser user = (OauthUser) obj;
            return this.role.equals(user.getRole()) && this.name.equals(user.getName())
                    && this.oauthId.equals(user.getOauthId());
        }

        return false;
    }
}
