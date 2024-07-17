package com.alura.lileitura.principal;

import java.util.Scanner;

import com.alura.lileitura.modelos.Autor;
import com.alura.lileitura.modelos.ConsumoAPI;
import com.alura.lileitura.modelos.ConverteDados;
import com.alura.lileitura.modelos.Livro;
import com.alura.lileitura.modelos.ResultDto.AutorDto;
import com.alura.lileitura.modelos.ResultDto.LivroDto;
import com.alura.lileitura.repositorios.AutorRepository;
import com.alura.lileitura.repositorios.LivroRepository;

public class Principal {
	
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private LivroRepository repositorioLivro;
    private AutorRepository repositorioAutor;
    private final String ENDERECO = "https://gutendex.com/books?search=";
	
   
    
	public void exibirMenu() {
        var opcao = -1;
        while (opcao != 0) {
        	
        	System.out.println(obterMenu());
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivrosPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 6:
                    buscarAutorPorNome();
                    break;
                case 7:
                    top10LivrosBaixados();
                    break;
                case 0:
                	System.out.println("Obrigado...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }
	
	private void buscarLivrosPorTitulo() {
        System.out.println("Digite o nome do livro: ");
        var nomeTitulo = leitura.nextLine();
        var dados = consumo.obterDados(ENDERECO + nomeTitulo.replace(" ", "%20"));
        salvarNoBanco(dados);
    }
	
	
	
	private void salvarNoBanco(String dados) {
        try {
        	LivroDto livroDto = conversor.obterDados(dados).results().get(0);
        	AutorDto autorDto = livroDto.autores().get(0);
        	
        	Livro livro = new Livro(livroDto);
            Autor autor = new Autor(autorDto);
   

            Autor bancoAutor = null;
            Livro bancoLivro = null;
            if (!repositorioAutor.existsByNome(autor.getNome())) {
                repositorioAutor.save(autor);
                bancoAutor = autor;
            } else {
                bancoAutor = repositorioAutor.findByNome(autor.getNome());
            }
            if (!repositorioLivro.existsByTitulo(livro.getTitulo())) {
                livro.setAutor(bancoAutor);
                repositorioLivro.save(livro);
                bancoLivro = livro;
            } else {
                bancoLivro = repositorioLivro.findByTitulo(livro.getTitulo());
            }
            System.out.println(bancoLivro);
        } catch (NullPointerException e) {
            System.out.println("Livro não encontrado");
        }
    }

	private void listarLivrosRegistrados() {
        var livro = repositorioLivro.findAll();
        if (!livro.isEmpty()) {
            System.out.println("\nLivros cadastrados no banco: ");
            livro.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum livro encontrado");
        }
    }

	private void listarAutoresRegistrados() {
        var buscarNoBanco = repositorioAutor.findAll();
        if (!buscarNoBanco.isEmpty()) {
            System.out.println("\nAutores cadastrados no banco: ");
            buscarNoBanco.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum autor encontrado");
        }
    }

    private void listarAutoresVivos() {
        System.out.println("\nDigite o ano para pesquisa: \n");
        var ano = leitura.nextInt();
        leitura.nextLine();
        var buscarAutoresNoBanco = repositorioAutor.buscarPorAnoMorte(ano);
        if (!buscarAutoresNoBanco.isEmpty()) {
            System.out.println("\nAutores vivos no ano " + ano + ": ");
            buscarAutoresNoBanco.forEach(System.out::println);
        } else {
            System.out.println("\nNenhum autor encontrado");
        }
    }

    private void listarLivrosPorIdioma() {
        var idiomasNoBanco = repositorioLivro.buscarIdiomas();
        System.out.println("\nIdiomas cadastrados no banco: ");
        idiomasNoBanco.forEach(System.out::println);
        System.out.println("\nDigite um dos idiomas para pesquisa: \n");
        var idiomaPesquisa = leitura.nextLine();
        repositorioLivro.buscarPorIdiomas(idiomaPesquisa).forEach(System.out::println);
    }

    private void buscarAutorPorNome() {
        System.out.println("\nDigite um autor pra busca: \n");
        var autorPesquisa = leitura.nextLine();
        var autor = repositorioAutor.encontrarPorNome(autorPesquisa);
        if (!autor.isEmpty()){
            autor.forEach(System.out::println);
        } else {
            System.out.println("\nAutor não encontrado");
        }
    }

    private void top10LivrosBaixados() {
        var top10 = repositorioLivro.findTop10ByOrderByNumeroDownloadsDesc();
        top10.forEach(System.out::println);
    }


	private String obterMenu() {
		return """
                *****  LiterAlura  *****
                
                Escolha o número da sua opção:
                                    
                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em um determinado ano
                5 - Listar livros em um determinado idioma
                6 - Buscar autor por nome
                7 - Top 10 livros mais baixados
                                    
                0 - Sair
                """;
	}

    

}
