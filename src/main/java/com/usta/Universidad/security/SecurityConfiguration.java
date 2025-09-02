package com.usta.Universidad.security;

import com.usta.Universidad.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build)
            throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Acceso público para todos los roles y anónimos
                        .requestMatchers("/", "/nosotros").permitAll()

                        // Acceso para HUESPED y ADMINISTRADOR
                        .requestMatchers("/emprendedor", "/modificarStartup/" ,"/eliminarStartup/").hasAnyRole( "EMPRENDEDOR")

                        .requestMatchers("/monitor").hasAnyRole( "MONITOR")

                        // Acceso solo para ADMINISTRADOR
                        .requestMatchers("/crearUsuario", "/crearStartup/", "/crearConvocatoria/", "/eliminarUsuario/", "/eliminarStartupAdm/",  "/eliminarConvocatoria/", "/modificarUsu/", "/modificarStartupAdm/", "/modificar/")
                        .hasRole("ADMINISTRADOR")

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .successHandler(loginSuccessHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .exceptionHandling(exeptions -> exeptions
                        .accessDeniedPage("/error404"));
        return http.build();
    }
}