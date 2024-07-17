package com.alura.lileitura.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alura.lileitura.modelos.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    boolean existsByNome(String nome);

    Autor findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoMorte >= :ano AND :ano >= a.anoNascimento")
    List<Autor> buscarPorAnoMorte(int ano);

    @Query("SELECT a FROM Autor a WHERE a.nome ILIKE %:autor%")
    List<Autor> encontrarPorNome(String autor);

}
