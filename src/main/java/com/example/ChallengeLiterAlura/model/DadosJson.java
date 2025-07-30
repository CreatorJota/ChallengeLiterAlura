package com.example.ChallengeLiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosJson(
        @JsonAlias("results") List<DadosLivros> resultado
) {
}
