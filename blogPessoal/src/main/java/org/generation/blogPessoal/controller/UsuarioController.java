package org.generation.blogPessoal.controller;

import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // Define que esta classe é um controller REST
@RequestMapping("/usuarios") // Define o endpoint base como /usuarios
@CrossOrigin(origins = "*", allowedHeaders = "*") // Permite requisições de qualquer origem (CORS liberado)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Injeta o serviço responsável pelas regras de negócio

    // Endpoint para login de usuário
    @PostMapping("/logar")
    public ResponseEntity<UserLogin> autentication(@RequestBody Optional<UserLogin> user) {
        // Chama o método logar do serviço e retorna 200 OK se sucesso ou 401 Unauthorized se falhar
        return usuarioService.logar(user)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // Endpoint para cadastro de novo usuário
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> post(@RequestBody Usuario usuario) {
        // Cadastra o usuário e retorna 201 Created com o usuário criado no corpo da resposta
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.cadastrarUsuario(usuario));
    }
}
