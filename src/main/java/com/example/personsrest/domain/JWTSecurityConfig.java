package com.example.personsrest.domain;

/*
import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
@Profile("jwt")
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

    //Realm: test
    //Client Id: group-api
    //Username: user
    //Password: djnJnPf7VCQvp3Fc

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        http.authorizeRequests()
                .antMatchers("/api/person").permitAll()
                .anyRequest()
                .hasAnyAuthority("ADMIN")
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(source -> new JwtAuthenticationToken(source, Stream.concat(
                        jwtGrantedAuthoritiesConverter.convert(source).stream(),
                        ((JSONArray)source.getClaimAsMap("realm_access").get("roles")).stream()
                                .map(o -> (String) o)
                                .map(SimpleGrantedAuthority::new)
                ).collect(Collectors.toList())));



    }


}*/
