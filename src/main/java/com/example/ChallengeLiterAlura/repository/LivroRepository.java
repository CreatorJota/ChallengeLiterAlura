package com.example.ChallengeLiterAlura.repository;

import com.example.ChallengeLiterAlura.model.Autor;
import com.example.ChallengeLiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Livro l WHERE LOWER(l.linguagem) = LOWER(:idioma)")
    List<Livro> buscarPorIdioma(String idioma);
}
