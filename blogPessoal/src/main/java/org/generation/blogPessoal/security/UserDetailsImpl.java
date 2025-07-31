package org.generation.blogPessoal.security;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    // Atributos que armazenam os dados do usuário autenticado
    private String userName;
    private String password;

    // Construtor que recebe um objeto Usuario e extrai os dados necessários
    public UserDetailsImpl(Usuario user) {
        this.userName = user.getUsuario(); // Define o nome de usuário a partir do campo "usuario"
        this.password = user.getSenha();   // Define a senha a partir do campo "senha"
    }

    // Construtor vazio (necessário para algumas instâncias do framework)
    public UserDetailsImpl() {
    }

    // Retorna as permissões do usuário (neste caso, nenhuma)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Retorna uma lista vazia (sem roles ou perfis)
    }

    // Retorna a senha do usuário
    @Override
    public String getPassword() {
        return password;
    }

    // Retorna o nome de usuário
    @Override
    public String getUsername() {
        return userName;
    }

    // Indica se a conta está expirada (true = não expirada)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indica se a conta está bloqueada (true = não bloqueada)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Indica se as credenciais estão expiradas (true = válidas)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indica se o usuário está habilitado (true = ativo)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
