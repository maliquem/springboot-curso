package br.com.jael.springcurso.springbootcurso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.jael.springcurso.springbootcurso.model.entities.Cliente;
import br.com.jael.springcurso.springbootcurso.model.repository.ClienteRepository;

@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
		return args -> {
			// clienteRepository.salvar(new Cliente("Bruno Nobre"));
			// clienteRepository.salvar(new Cliente("Jefferson Califas"));

			List<Cliente> todosOsClientes = clienteRepository.findAll();
			todosOsClientes.forEach(System.out::println);

			clienteRepository.deleteById(10);
			todosOsClientes.forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
