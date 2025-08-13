package com.torneo.infra.drive.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private static final Logger log = LoggerFactory.getLogger(JwtReactiveAuthenticationManager.class);
    private final JwtUtil jwtUtil;

    public JwtReactiveAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        return Mono.just(token)
                .map(t -> {
                    boolean valid = jwtUtil.validateToken(t);
                    log.info("Validando token: {}, resultado: {}", t, valid);
                    if (!valid) {
                        log.warn("Token inválido: {}", t);
                        throw new org.springframework.security.authentication.BadCredentialsException("Token inválido");
                    }
                    return t;
                })
                .flatMap(t -> {
                    String username = jwtUtil.getUsernameFromToken(t);
                    List<String> roles = jwtUtil.getRolesFromToken(t);
                    log.info("Username del token: {}", username);
                    log.info("Roles extraidos: {}", roles);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    return Mono.just(auth);
                })
                .onErrorResume(e -> {
                    log.error("Excepción al validar token", e);
                    return Mono.error(new org.springframework.security.authentication.BadCredentialsException("Token inválido", e));
                });
    }

/*
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        boolean valid = jwtUtil.validateToken(token);
        log.info("Validando token: {}, resultado: {}", token, valid);

        if (valid) {
            String username = jwtUtil.getUsernameFromToken(token);
            List<String> roles = jwtUtil.getRolesFromToken(token);
            log.info("Username del token: {}", username);
            log.info("Roles extraidos: {}", roles);

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            return Mono.just(auth);
        }

        log.warn("Token inválido: {}", token);
        return Mono.empty();
    }
*/

}
