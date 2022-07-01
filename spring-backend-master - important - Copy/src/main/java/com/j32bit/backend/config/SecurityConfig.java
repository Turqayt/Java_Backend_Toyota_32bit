package com.j32bit.backend.config;

import com.j32bit.backend.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserManagementService userManagementService;
    private final J32bitExceptionHandlerFilter j32bitExceptionHandlerFilter;

    private final JwtSecurityFilter jwtSecurityFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/user/authenticate").permitAll()
                .antMatchers("/api/user/homepage").hasAuthority("ADMIN")
                .antMatchers("/api/user/management/**").hasAnyAuthority("ADMIN","SUPERVISOR")
                .antMatchers("/api/user/form/**").hasAuthority("ADMIN")
                .antMatchers("/api/test").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/actuator/**").permitAll();


        http.cors().and().csrf().disable();

        http.addFilterBefore(j32bitExceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }


    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new J32bitUserDetailsServiceImpl(userManagementService);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        InvisoDaoAuthenticationProvider invisoDaoAuthenticationProvider = new InvisoDaoAuthenticationProvider();
        invisoDaoAuthenticationProvider.setUserDetailsService(userDetailsService());
        invisoDaoAuthenticationProvider.setPasswordEncoder(md5PasswordEncoder());
        invisoDaoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return invisoDaoAuthenticationProvider;
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.addAllowedHeader("Token");
        configuration.addExposedHeader("Token");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public MessageDigestPasswordEncoder md5PasswordEncoder() {
        return new MessageDigestPasswordEncoder("MD5");
    }

}