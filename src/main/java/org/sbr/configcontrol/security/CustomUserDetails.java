package org.sbr.configcontrol.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static builder builder() {
        return new builder();
    }

    public static class builder {

        String username;
        String password;
        List<String> roles;

        public CustomUserDetails build() {
            return new CustomUserDetails(username, password, this.authorities());
        }

        public builder username(String name) {
            this.username = name;
            return this;
        }

        public builder password(String password) {
            this.password = password;
            return this;
        }

        public builder roles(String... roles) {
            this.roles = List.of(roles);
            return this;
        }
        private List<GrantedAuthority> authorities() {
            List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
            for (String role : roles) {
                Assert.isTrue(!role.startsWith("ROLE_"),
                        () -> role + " cannot start with ROLE_ (it is automatically added)");
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            return authorities;
        }

    }
}
