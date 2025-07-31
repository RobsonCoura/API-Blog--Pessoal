package org.generation.blogPessoal.repository;

import org.generation.blogPessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemaRepository extends JpaRepository<Tema, Long> {

    // Método personalizado do Spring Data JPA que busca todos os temas
    public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);
}
