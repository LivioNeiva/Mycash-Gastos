package org.mycash;

import org.modelmapper.ModelMapper;
import org.mycash.service.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MycashApplication {

	public static void main(String[] args) {

		/*
		esse contexto vai ter as informações de todos os beans e de todos os objs q foram
		carregados durante a aplicação
		 */
												//esse metodo retorna um contexto
		ConfigurableApplicationContext context = SpringApplication.run(MycashApplication.class, args);

		//nos vamos pegar todas as instancia da classe usuarioService e add service
		UsuarioService service = context.getBean(UsuarioService.class);

		//todas as vezes q a aplicação ssubir, vamos criar um usuario
		//service.registraUsuarioAdmin("admin@mycash.com", "admin");
		//service.save("user@mycash.com", "user");
		
	}
	
	/*
    modelmapper -> fz o mapeamento de um objeto para um outro tipo, para
    usa-lo, temos fazer uma dependendia no maven. http://modelmapper.org/
    dependencia: http://modelmapper.org/downloads/
    esse mapper tem metodo mapper que consegue mapear todos os objs e transformar num dto, temos criar metodo com annotations @Bean
	*/
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
//01:16:49 erros
//01:17:45 erros
//01:19:18 certo
//01:33:33 usuarioController
//01:55 DTO
//02:08:33 ziroku
//02:11:39 ziroku
