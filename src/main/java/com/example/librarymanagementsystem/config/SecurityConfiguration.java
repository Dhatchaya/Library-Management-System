package com.example.librarymanagementsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/edit/**").hasAnyRole(UserRole.ADMIN.name(),UserRole.PUBLISHER.name())//either of these two users can access /edit path
                .antMatchers("/delete/**").hasRole(UserRole.ADMIN.name())//either of these two users can access /edit path
                .antMatchers("/actuator/**").hasRole(UserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }
//this is for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.inMemoryAuthentication()

                 .withUser("user_admin").password("{noop}1234") .roles(UserRole.ADMIN.name())
                 .and().withUser("user_publisher").password("{noop}1234") .roles(UserRole.PUBLISHER.name())
                 .and().withUser("user_read_only").password("{noop}1234") .roles(UserRole.READ_ONLY.name());

    }//noop will give non encoded password
}
