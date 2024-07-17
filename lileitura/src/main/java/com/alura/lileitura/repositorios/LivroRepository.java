package com.alura.lileitura.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alura.lileitura.modelos.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

	boolean existsByTitulo(String titulo);

    Livro findByTitulo(String titulo);

    @Query("SELECT DISTINCT l.idioma FROM Livro l ORDER BY l.idioma")
    List<String> buscarIdiomas();

    @Query("SELECT l FROM Livro l WHERE idioma = :idioma")
    List<Livro> buscarPorIdiomas(String idioma);
    
    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();

	
}
