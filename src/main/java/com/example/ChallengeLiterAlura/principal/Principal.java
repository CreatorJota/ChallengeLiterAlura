package com.example.ChallengeLiterAlura.principal;

import ch.qos.logback.core.BasicStatusManager;
import com.example.ChallengeLiterAlura.model.*;
import com.example.ChallengeLiterAlura.repository.AutorRepository;
import com.example.ChallengeLiterAlura.repository.LivroRepository;
import com.example.ChallengeLiterAlura.service.ConsumoApi;
import com.example.ChallengeLiterAlura.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosLivros> livros = new ArrayList<>();
    private LivroRepository repositorioLivro;
    private AutorRepository repositorioAutor;

    public Principal(LivroRepository repositorio, AutorRepository repository){
        this.repositorioLivro = repositorio;
        this.repositorioAutor = repository;
    }

    public void menu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrado();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Diga o titulo do livro: ");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + "?search=" + nomeLivro.toLowerCase()
                .replace(" ", "+"));
        DadosJson dadosJson = conversor.obterDados(json, DadosJson.class);

        Optional<DadosLivros> livroEncontrado = dadosJson.resultado().stream()
                .filter(livro -> livro.titulo().equalsIgnoreCase(nomeLivro))
                .max(Comparator.comparingInt(DadosLivros::numeroDownload));

        livroEncontrado.ifPresent(dadosLivro -> {
            DadosAutores dadosAutor = dadosLivro.autores().get(0);
            Optional<Autor> autorOptional = repositorioAutor.findByNomeContainingIgnoreCase(dadosAutor.nome());
            Autor autor;
            if (autorOptional.isPresent()) {
                autor = autorOptional.get();
            } else {
                autor = new Autor(dadosAutor);
                autor = repositorioAutor.save(autor);
            }
            Optional<Livro> livroExistente = repositorioLivro.findByTituloContainingIgnoreCase(dadosLivro.titulo());

            Livro livroFinal;
            if (livroExistente.isEmpty()) {
                livroFinal = new Livro(dadosLivro, autor);
                livroFinal = repositorioLivro.save(livroFinal);
            } else {
                livroFinal = livroExistente.get();
            }
            System.out.println(
                    "Título: " + livroFinal.getTitulo() +
                            "\nAutor: " + livroFinal.getAutor().getNome() +
                            "\nIdioma: " + livroFinal.getLinguagem() +
                            "\nNúmero de downloads: " + livroFinal.getNumeroDownload()
            );
        });
    }

        private void listarLivrosRegistrado() {

            System.out.println("\n=== Livros encontrados ===");
            int contador = 1;
            for (DadosLivros livro : livros) {
                System.out.println("Livro " + contador++);
                System.out.println("Titulo: " + livro.titulo());
                System.out.println("Autor: " +  livro.autores().get(0).nome());
                System.out.println("Idioma: " + livro.linguagem().get(0));
                System.out.println("Numero de download: " + livro.numeroDownload());
                System.out.println();
            }
        }

    private void listarAutoresRegistrados() {
        System.out.println("\n=== Autores encontrados ===");

        livros.stream()
                .flatMap(livro -> livro.autores().stream())
                .distinct()
                .forEach(autor -> {
                    System.out.println("Autor: " + autor.nome());
                    System.out.println("Ano de nascimento: " + autor.anoNascimento());
                    System.out.println("Ano de falecimento: " + autor.anoFalecimento());
                    System.out.println();
                });
    }

    private void listarAutoresVivosAno() {
        System.out.println("Digite o ano que deseja pesquisar");
        var ano = leitura.nextInt();

        livros.stream()
                .flatMap(livro -> livro.autores().stream())
                .distinct()
                .filter(autor -> {
                    try {
                        int nascimento = Integer.parseInt(autor.anoNascimento());
                        Integer falecimento = autor.anoFalecimento() != null ? Integer.parseInt(autor.anoFalecimento()) : null;

                        return nascimento <= ano && (falecimento == null || falecimento > ano);
                    } catch (NumberFormatException | NullPointerException e) {
                        return false;
                    }
                })
                .forEach(autor -> {
                    System.out.println("Autor: " + autor.nome());
                    System.out.println("Ano de nascimento: " + autor.anoNascimento());
                    System.out.println("Ano de falecimento: " + autor.anoFalecimento());
                    System.out.println();
                });
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                pt - português
                en - ingles
                fr - frances
                es - espanhol
                Digite o idioma que deseja pesquisar: """);
        var idioma = leitura.nextLine().toLowerCase();

        var livrosEncontrados = livros.stream()
                .filter(livro -> livro.linguagem().get(0).toLowerCase().contains(idioma))
                .toList();

        if (livrosEncontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma informado.");
        } else {
            livrosEncontrados.forEach(livro -> {
                System.out.println("Título: " + livro.titulo());
                System.out.println("Idioma: " + livro.linguagem().get(0));
                System.out.println();
            });
        }
    }
}

