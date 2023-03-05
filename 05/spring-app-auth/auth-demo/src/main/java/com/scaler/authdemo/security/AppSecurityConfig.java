package com.scaler.authdemo.security;

import com.scaler.authdemo.security.jwt.JwtAuthenticationFilter;
import com.scaler.authdemo.security.jwt.JwtAuthenticationManager;
import com.scaler.authdemo.security.jwt.JwtService;
import com.scaler.authdemo.users.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@EnableWebSecurity
@Configuration
//this class is used to configure the security of the application
//before this class every request is restricted  just after adding spring security dependency
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtAuthenticationFilter jwtAuthenticationFilter;
//    private SSTAuthenticationFilter sstAuthenticationFilter;

    /* ASSIGNMENT 06:

        Implement server-side token authentication as well, and make
        both types of auth available in your server
     */

    public AppSecurityConfig(
            JwtService jwtService,
            UsersService usersService
    ) {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(
                new JwtAuthenticationManager(
                        jwtService, usersService
                )
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: in prod, CORS and CSRF shouldn't be blanket disabled
        //Cross-site request forgery (CSRF) is a type of attack where a malicious website or script attempts to exploit the trust relationship between a user's browser and a legitimate website they are logged into
        //Cross-Origin Resource Sharing (CORS) is a mechanism that allows restricted resources on a web page to be requested from another domain outside the domain from which the first resource was served.

        http.cors().disable().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/about").permitAll()
                .antMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                .antMatchers("/*/**").authenticated()
                .and()
                //adding filter to check every request
                // each filter has converter and authentication manager
                //converter convert request to authentication object
                //authentication manager authenticate the user
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
//                .addFilterBefore(sstAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        super.configure(http);
    }
}
