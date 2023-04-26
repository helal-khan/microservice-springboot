package com.microservice.apigateway.filter;

import com.microservice.apigateway.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate template;

    @Autowired
    private JwtProvider jwt;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return ((exchange, chain) -> {

            if (validator.isSecured.test(exchange.getRequest())) {
                HttpHeaders headers = exchange.getRequest().getHeaders();

                if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header.");
                }

                String authHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
//                    String response = template.getForObject("http://auth-service/validate?token=" + authHeader, String.class);
                    jwt.validateToken(authHeader);
                } catch (Exception e) {
                    throw new RuntimeException("Unauthorized...");
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }

}
