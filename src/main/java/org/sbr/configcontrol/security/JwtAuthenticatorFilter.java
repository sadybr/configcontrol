package org.sbr.configcontrol.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticatorFilter extends OncePerRequestFilter {
    @Value("${jwt.secret}")
    private String secret;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .map(this::decodeToken)
                .map(this::extractUserDetails)
                .map(CustomAuthenticatorToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication))
        ;

        filterChain.doFilter(request, response);
    }

    private DecodedJWT decodeToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

    private UserDetails extractUserDetails(DecodedJWT jwt) {
        return User.builder()
                .username(jwt.getSubject())
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .password("none")
                .authorities(
                        jwt.getClaim("roles").asList(String.class).stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                ).build();


    }
}
