package com.example.springsecuritydemo.springsecuritydemo.config;

import com.example.springsecuritydemo.springsecuritydemo.component.CustomLoginHandler;
import com.example.springsecuritydemo.springsecuritydemo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.CachingUserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomLoginHandler customLoginHandler;

    @Autowired
    DataSource dataSource;
    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder)
            throws Exception {
       /* managerBuilder
                .inMemoryAuthentication()
                .withUser("user1").password("pass").roles("USER")
                .and()
                .withUser("user2").password("pass2").roles("USER");*/
       managerBuilder.userDetailsService(userDetailsService);
    }

   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user1").password("{noop}pass").roles("USER")
                .and()
                .withUser("user2").password("{noop}pass2").roles("USER");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/registerUser/**").permitAll()
                .antMatchers("/registrationConfirmation/**").permitAll()
                .antMatchers("/loginUrl").permitAll()
                .antMatchers("/home/**").hasRole("USER")
                .anyRequest()
                .authenticated().and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/loginUrl").and().logout().permitAll();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
