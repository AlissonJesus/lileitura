package com.alura.lileitura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.lileitura.principal.Principal;

@SpringBootApplication
public class LileituraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LileituraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();
		
	}

}
