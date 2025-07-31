package org.generation.blogPessoal.controller;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

    // Injeção do repositório responsável pelas operações de acesso a dados da entidade Tema
    @Autowired
    private TemaRepository repository;

    //Método para buscar por todos os temas
    @GetMapping
    public ResponseEntity<List<Tema>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    //Método para buscar por um tema pelo seu ID
    @GetMapping("/{id}")
    public ResponseEntity<Tema> getById(@PathVariable long id){
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    //Método que faz uma busca do tema pelo nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Tema>> getByName(@PathVariable String nome){
        return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(nome));
    }
}
