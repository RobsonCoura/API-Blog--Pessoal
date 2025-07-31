package org.generation.blogPessoal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Indica que esta classe contém configurações do Spring
@EnableWebSecurity // Habilita a segurança web da aplicação
@EnableMethodSecurity // Permite usar anotações como @PreAuthorize nos métodos
public class BasicSecurityConfig {

    private final UserDetailsService userDetailsService;

    // Injeta a implementação de UserDetailsService usada para autenticar usuários
    public BasicSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Define o algoritmo de criptografia de senhas como BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expõe o AuthenticationManager como bean, necessário para autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Define as regras de segurança HTTP da aplicação
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (adequado para APIs RESTful)
                .csrf(csrf -> csrf.disable())

                // Desabilita o CORS (habilite se necessário para frontend externo)
                .cors(cors -> cors.disable())

                // Define que a sessão será *stateless* (sem armazenar estado no servidor)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define as autorizações por endpoint
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público para login e cadastro
                        .requestMatchers("/usuarios/logar", "/usuarios/cadastrar").permitAll()

                        // Qualquer outra requisição exige autenticação
                        .anyRequest().authenticated()
                )

                // Habilita autenticação do tipo HTTP Basic (usuário e senha via cabeçalho)
                .httpBasic(httpBasic -> {
                }); // Novo padrão sem usar .and()

        // Constrói e retorna a cadeia de filtros de segurança
        return http.build();
    }
}