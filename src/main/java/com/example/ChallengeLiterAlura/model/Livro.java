package com.example.ChallengeLiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Integer numeroDownload;
    private String linguagem;

    @ManyToOne
    private Autor autor;

    public Livro() {
    }

    public Livro(DadosLivros dados, Autor autor) {
        this.titulo = dados.titulo();
        this.numeroDownload = dados.numeroDownload();
        this.linguagem = dados.linguagem().isEmpty() ? null : dados.linguagem().get(0);
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDownload() {
        return numeroDownload;
    }

    public void setNumeroDownload(Integer numeroDownload) {
        this.numeroDownload = numeroDownload;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", numeroDownload=" + numeroDownload + '\'' +
                ", linguagem='" + linguagem + '\'' +
                ", autor=" + autor;
    }
}
