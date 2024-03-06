package org.sbr.configcontrol.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class CustomUserDetailService implements UserDetailsService {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final Map<String, UserDetails> users = Map.of(
//            "user", User.builder()
//                    .username("user")
//                    .password(encoder.encode("user"))
//                    .roles("USER")
//                    .accountExpired(false)
//                    .disabled(false)
//                    .accountLocked(false)
//                    .credentialsExpired(false)
//                    .build(),
//            "admin", User.builder()
//                    .username("admin")
//                    .password(encoder.encode("admin"))
//                    .roles("USER", "ADMIN")
//                    .accountExpired(false)
//                    .disabled(false)
//                    .accountLocked(false)
//                    .credentialsExpired(false)
//                    .build()
            "user", CustomUserDetails.builder()
                    .username("user")
                    .password(encoder.encode("user"))
                    .roles("USER")
                    .build(),
            "admin", CustomUserDetails.builder()
                    .username("admin")
                    .password(encoder.encode("admin"))
                    .roles("USER", "ADMIN")
                    .build()
    );
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        log.info("User found: {}", username);

        return user;
    }
}
