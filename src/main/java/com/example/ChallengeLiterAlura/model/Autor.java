package com.example.ChallengeLiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros =new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutores dados) {
        this.nome = dados.nome();
        this.anoNascimento = dados.anoNascimento();
        this.anoFalecimento = dados.anoFalecimento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(int anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    @Override
    public String toString() {
        return  "Nome = '" + nome + '\'' +
                ", AnoNascimento = '" + anoNascimento + '\'' +
                ", AnoFalecimento = '" + anoFalecimento;

    }
}
