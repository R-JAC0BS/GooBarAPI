package br.com.goobar_app.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ConfigSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,AuthToken authToken) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "Bar/findBar").permitAll()
                                .requestMatchers(HttpMethod.PUT, "auth/Upload/").permitAll()
                                .requestMatchers("Bar/").authenticated()
                                .requestMatchers(HttpMethod.POST, "Bar/location/").authenticated()
                                .requestMatchers(HttpMethod.POST, "Bar/coments/").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "Bar/BarDelete/").hasAuthority("CNPJ")
                                .requestMatchers(HttpMethod.PUT, "Bar/avaliacao/").authenticated()
                                .requestMatchers(HttpMethod.PUT, "Bar/alter/").hasAuthority("CNPJ").anyRequest().authenticated()

                        )

                .addFilterBefore(authToken, UsernamePasswordAuthenticationFilter.class);



        return http.build();

    }





    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
