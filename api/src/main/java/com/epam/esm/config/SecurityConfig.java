package com.epam.esm.config;

import com.epam.esm.exception.handler.AccessDeniedHandlerEntryPoint;
import com.epam.esm.exception.handler.AuthenticationHandlerEntryPoint;
import com.epam.esm.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTFilter jwtFilter;
    private final AuthenticationHandlerEntryPoint authenticationHandlerEntryPoint;
    private final AccessDeniedHandlerEntryPoint accessDeniedHandlerEntryPoint;

    private static final String ADMIN = "ADMIN";

    @Autowired
    public SecurityConfig(JWTFilter jwtFilter, AuthenticationHandlerEntryPoint authenticationHandlerEntryPoint,
                             AccessDeniedHandlerEntryPoint accessDeniedHandlerEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.authenticationHandlerEntryPoint = authenticationHandlerEntryPoint;
        this.accessDeniedHandlerEntryPoint = accessDeniedHandlerEntryPoint;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                //All
                .antMatchers(GET, "/api/V1/certificates/**").permitAll()
                .antMatchers(POST, "/api/V1/users/register", "/auth").permitAll()
                //User
                .antMatchers(POST, "/api/V1/orders/**").fullyAuthenticated()
                .antMatchers(GET, "/api/V1/tags/**", "/api/V1/orders/**", "/api/V1/users/**").fullyAuthenticated()
                //Admin
                .anyRequest().hasRole(ADMIN)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerEntryPoint).authenticationEntryPoint(authenticationHandlerEntryPoint)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
