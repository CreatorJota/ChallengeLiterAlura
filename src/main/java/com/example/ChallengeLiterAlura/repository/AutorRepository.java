package com.example.ChallengeLiterAlura.repository;

import com.example.ChallengeLiterAlura.model.Autor;
import com.example.ChallengeLiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeContainingIgnoreCase(String nome);
}
