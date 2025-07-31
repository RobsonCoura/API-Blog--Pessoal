package org.generation.blogPessoal.controller;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagemController {

    // Injeção do repositório responsável pelas operações de acesso a dados da entidade Postagem
    @Autowired
    private PostagemRepository repository;

    //Método de buscar todas as postagens
    @GetMapping
    public ResponseEntity<List<Postagem>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    //Método para buscar uma postagem pelo ID especifico
    @GetMapping("/{id}")
    public ResponseEntity<Postagem> getById(@PathVariable long id) {
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    //Método para buscar por um titulo de uma postagem
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
        return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
    }

}
