package org.sbr.configcontrol.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.sbr.configcontrol.model.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private final AuthenticationManager authenticationManager;


    public String authenticate(UserDto user) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.username(), user.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return createJwt((UserDetails) authentication.getPrincipal());
    }

    private String createJwt(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withExpiresAt(Instant.now().plus(5, java.time.temporal.ChronoUnit.MINUTES))
                .sign(Algorithm.HMAC256(secret));

    }
}
