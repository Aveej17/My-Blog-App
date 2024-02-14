package com.jeeva.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter){
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // All Access

//        http.csrf((csrf)-> {
//            try {
//                csrf.disable().authorizeHttpRequests((authorize)->authorize.anyRequest()
//                        .authenticated())
//                        .httpBasic(Customizer.withDefaults());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });

        // Limited Access for an User (Authorization)
        http.csrf((csrf)-> {
            try {
                csrf.disable().authorizeHttpRequests((authorize)->
//                        authorize.anyRequest().authenticated();
                                authorize.requestMatchers(HttpMethod.GET, "/api**").permitAll().
//                                        requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll().
                                        requestMatchers("/api/auth/**").permitAll().
                                        anyRequest().authenticated()
                        ).exceptionHandling(
                                exception->exception.authenticationEntryPoint(authenticationEntryPoint)
                        ).sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    //In Memory Authentication
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails jeeva = User.builder().
//                username("jeeva").
//                password(passwordEncoder().encode("jeeva")).
//                roles("USER").
//                build();
//
//        UserDetails admin = User.builder().
//                username("admin").
//                password(passwordEncoder().encode("admin")).
//                roles("ADMIN").
//                build();
//
//        return new InMemoryUserDetailsManager(jeeva, admin);
//    }

}
