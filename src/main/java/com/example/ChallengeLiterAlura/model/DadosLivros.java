package com.example.ChallengeLiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros (
        Integer id,
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<DadosAutores> autores,
    @JsonAlias("languages") List<String> linguagem,
    @JsonAlias("download_count") Integer numeroDownload
){
}

