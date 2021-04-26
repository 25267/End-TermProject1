package org.example.jwt;

import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtTokenGeneratorFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/**", "/css/*").permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/**", "/addresses/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/notes/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/notes/**", "/addresses/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,  "/users/**", "/addresses/**").hasAuthority( "ADMIN")
                .antMatchers(HttpMethod.PUT,  "/notes/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/users/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/addresses/**", "/notes/**").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/users/register").permitAll()
                .antMatchers("/api/**").permitAll()
                .anyRequest()
                .authenticated();



    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }
}
