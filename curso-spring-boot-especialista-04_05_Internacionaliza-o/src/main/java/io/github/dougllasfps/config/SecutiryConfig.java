package io.github.dougllasfps.config;


import io.github.dougllasfps.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());

        // super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeRequests()
                    .antMatchers("/api/clientes/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/produtos/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/pedidos/**")
                        .hasAnyRole("USER", "ADMIN")
                .and()
                    .httpBasic();
    }
}
