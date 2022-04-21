package com.minshigee.dataserver.security.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@ToString
@Hidden
public class CustomUser implements UserDetails {

    @Getter
    private final Long userCode;
    @Getter
    private final String userEmail;
    @Getter
    private final String token;
    private final ArrayList<? extends GrantedAuthority> authorities;
    @Setter
    private Boolean isAuthenticated = true;
    @Setter
    private Boolean isAccountNonExpired = true;
    @Setter
    private Boolean isAccountNonLocked = true;
    @Setter
    private Boolean isCredentialsNonExpired = true;
    @Setter
    private Boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
