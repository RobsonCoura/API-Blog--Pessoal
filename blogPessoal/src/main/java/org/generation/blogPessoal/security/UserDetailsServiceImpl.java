package org.generation.blogPessoal.security;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Indica que essa classe é um serviço gerenciado pelo Spring
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório para buscar usuários no banco de dados

    // Método que carrega um usuário com base no nome de usuário (username)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca o usuário pelo campo 'usuario' (nome de login)
        Optional<Usuario> user = usuarioRepository.findByUsuario(username);

        // Se o usuário não for encontrado, lança uma exceção com mensagem personalizada
        user.orElseThrow(() -> new UsernameNotFoundException(username + " Not found."));

        // Converte o objeto Usuario em um objeto UserDetails usando a classe UserDetailsImpl
        return user.map(UserDetailsImpl::new).get();
    }
}
