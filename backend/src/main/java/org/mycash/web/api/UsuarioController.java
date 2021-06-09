package org.mycash.web.api;

import org.modelmapper.ModelMapper;
import org.mycash.domain.Usuario;
import org.mycash.service.UsuarioService;
import org.mycash.web.DTO.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.sun.el.stream.Stream;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    /*
    modelmapper -> fz o mapeamento de um objeto para um outro tipo, para
    usa-lo, temos fazer uma dependendia no maven. http://modelmapper.org/
    dependencia: http://modelmapper.org/downloads/
     */
  //esse mapper tem metodo mapper que consegue mapear todos os objs e transformar num dto, temos criar metodo com annotations @Bean
  //metodo foi criado na classe principal MycashApplication.java
    @Autowired
    ModelMapper mapper; //nao aceitou eu fazer a dependencia
    
   @GetMapping
   //na classe ServerSecuryConfig tempo propriedade prePostEnabled = true, ela habilita o pre eo post, ele possibilita podemos colocar o @Oreauthorize para podermos tratar 
   @PreAuthorize("hasRole('ROLE_ADMIN')") //sendo a role_admin, antes de executar ele vai verificar se o usuario q est´tentando acessar esse metodo tem a role_admin, caso contrario, vai dar um erro
    public List<UsuarioDTO> todos() {
	   
	   List<Usuario> usuarios = service.todos();
	   
	   List<UsuarioDTO> usuarioDTO = usuarios.stream() //o mapper vai converter obj todos para o tipo UsuarioDTO
			   						.map((u) -> mapper.map(u, UsuarioDTO.class))
			   						.collect(Collectors.toList());
	   return usuarioDTO;	   
	}

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Usuario criar( //@RequestParam as informaçoes vem pelo parametro url
                          @RequestParam(required = true) String email, //(required = true) o parametro é obrigatorio
                          @RequestParam(required = true) String senha) {
        return service.save(email, senha);
    }
    
    /*
    se o email q está vindo na requisição é igual ao email do usuario que está acessando, ele pode executar, ou se for um
    usuario amin. o "#" acessa a propriedade q está dentro do paramentro. A instrução do codigo fica,se o email for igual ao
    email do usuario que esta susando o metodo ou se o usuario for do tipo admin, ou seja a role tem ser admin
    quando eu tentar recuperar o usuario pelo email ele vai fazer todas as vadilações. OBS - authentication vem do spring boot
    */
    @GetMapping(value = "/{email}")
    @PreAuthorize("#email == authentication.getName() or hasRole('ROLE_ADMIN')")
    public Usuario apenasUm(
    		//posso usar esse obj authentication para filtrar informações que estão sendo retornada rest do token. ex retornar so os lancamento do usuario 
    		Authentication authentication, //estou passando para dentro do metodo o usuario está autenticado pelo token, esta campo userAuthentication/principal
    		@PathVariable("email") String email) {
        return service.findByEmail(email);
    }

    @PutMapping(value = "/{email}")
    public Usuario resetarSenha(
            @PathVariable String email,
            @RequestParam(required = true) String senhaNova) {

        return service.resetarSenha(email, senhaNova);

    }
}
