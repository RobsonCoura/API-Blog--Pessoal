package org.generation.blogPessoal.service;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;

@Service // Define que essa classe é um componente de serviço gerenciado pelo Spring
public class UsuarioService {

    @Autowired
    UsuarioRepository repository; // Injeta o repositório para operações no banco de dados

    // Método responsável por cadastrar um novo usuário com senha criptografada
    public Usuario cadastrarUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Criptografador de senha

        String senhaEncoder = encoder.encode(usuario.getSenha()); // Criptografa a senha

        usuario.setSenha(senhaEncoder); // Define a senha criptografada no objeto

        return repository.save(usuario); // Salva o usuário no banco de dados
    }

    // Método responsável por autenticar um usuário e retornar os dados de login (token, nome)
    public Optional<UserLogin> logar(Optional<UserLogin> user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Busca o usuário no banco de dados pelo login
        Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

        // Se o usuário existir e a senha for válida
        if (usuario.isPresent() && encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

            // Cria um token básico (formato: Basic base64(usuario:senha))
            String auth = user.get().getUsuario() + ":" + user.get().getSenha();
            byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodeAuth);

            // Preenche os dados do objeto de login com nome e token
            user.get().setToken(authHeader);
            user.get().setNome(usuario.get().getNome());
            user.get().setSenha(usuario.get().getSenha()); // opcional: retorna senha criptografada

            return user;
        }

        // Retorna null caso a autenticação falhe
        return null;
    }
}
