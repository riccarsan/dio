package br.com.riccar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.riccar"})
public class StocksManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksManagerApplication.class, args);
	}

}
