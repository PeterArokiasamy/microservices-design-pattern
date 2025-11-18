package com.arokia.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET).permitAll()
                /* exchanges.anyExchange().authenticated() means any type of request can be authenticated
                exchanges.anyExchange().permitAll() means for some reason we can permit all request
                But we want some API to be access without security and some with security like only authenticated user
                can access them.
                exchanges.pathMatchers(HttpMethod.GET).permitAll() means GET API will not be enforced with security
                Get API has been given top priority
                */
                        //.pathMatchers("/arokiabank/accounts/**").authenticated()
                        /* enforcing any path with above prefix will be authenticated*/
                        .pathMatchers("/arokiabank/accounts/**").hasRole("ACCOUNTS")
                        /* Allow roles*/
                        //.pathMatchers("/arokiabank/cards/**").authenticated()
                       .pathMatchers("/arokiabank/cards/**").hasRole("CARDS")
                        //.pathMatchers("/arokiabank/loans/**").authenticated())
                       .pathMatchers("/arokiabank/loans/**").hasRole("LOANS"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        /* oAuth2ResourceServerSpec - Convert gateway as OAuth2 resource server*/
                        //.jwt(Customizer.withDefaults()));
                        /* .jwt(Customizer.withDefaults())); means telling spring to use default config w.r.t JWT tokens*/
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
                        /* now we should not use default config as we have created roles
                        * so now we have established link about our keyrole converter inside Spring Security config */
        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());
        /* invoke .csrf disable means no browsers are involved else post method will fail */
        return serverHttpSecurity.build();
        /* build method will return object of SecurityWebFilterChain, which will be converted as a bean with  @bean annotation*/
    }


    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }






}
