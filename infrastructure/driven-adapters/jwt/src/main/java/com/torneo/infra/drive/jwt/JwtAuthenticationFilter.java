package com.torneo.infra.drive.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;

public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(JwtReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.setServerAuthenticationConverter(new JwtServerAuthenticationConverter());
    }
}

// Nuevo: clase que extrae el token del header
class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    private static final Logger log = LoggerFactory.getLogger(JwtServerAuthenticationConverter.class);

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            log.info("**************************************************************************************************");
            log.info("Token extraído del header: {}", token);
            return Mono.just(new UsernamePasswordAuthenticationToken(token, token));
        }
        log.warn("No se encontró token en el header Authorization");
        return Mono.empty();
    }
}
