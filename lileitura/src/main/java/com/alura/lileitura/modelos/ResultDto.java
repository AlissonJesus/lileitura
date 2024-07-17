package com.alura.lileitura.modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultDto(List<LivroDto> results) {
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public record LivroDto(
			@JsonAlias("title") String titulo, 
			@JsonAlias("authors") List<AutorDto> autores, 
			@JsonAlias("languages") List<String> idioma,
	        @JsonAlias("download_count")  Integer numeroDownloads) {}
	@JsonIgnoreProperties(ignoreUnknown = true)
	
	public record AutorDto(
			@JsonAlias("name") String nome, 
			@JsonAlias("birth_year") Integer anoNascimento, 
			@JsonAlias("death_year") Integer anoMorte) {}
}
