package com.example.ChallengeLiterAlura;

import com.example.ChallengeLiterAlura.model.DadosJson;
import com.example.ChallengeLiterAlura.model.DadosLivros;
import com.example.ChallengeLiterAlura.principal.Principal;
import com.example.ChallengeLiterAlura.service.ConsumoApi;
import com.example.ChallengeLiterAlura.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.menu();
	}
}
