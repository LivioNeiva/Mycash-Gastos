package org.mycash.service;

import javassist.NotFoundException;
import org.mycash.domain.Usuario;
import org.mycash.domain.UsuarioRole;
import org.mycash.exception.UsuarioException;
import org.mycash.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> todos(){
        return repo.findAll();
    }
    //metodo para registrar usuario como administrador, esse metodo esta sendo testado na
    //classe MycashApplication usuarioAdmin
    public void registraUsuarioAdmin(String email, String senha){

        /*
        vamos criar uma proteção caso ja exista um usuario com mesmo email no dba
        fazer uma consulta no banco de dados para saber se ja existe email.
        e se nao existir sera feito a criaçao do usuario
         */
    	Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setSenha(passwordEncoder.encode(senha));
		usuario.setRole(UsuarioRole.ROLE_ADMIN);
		
		if (repo.findByEmail(usuario.getEmail()).isPresent())
			throw new UsuarioException("Já existe um usuário com este e-mail");
			
		repo.save(usuario);
    	
    	
    	/*
    	if (repo.findByEmail(email).isEmpty()){//isEmpty() checa se está vazio, se estiver vazio, faça
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(passwordEncoder.encode(senha));
            usuario.setRole(UsuarioRole.ROLE_ADMIN);

            repo.save(usuario);
         }
            */
        
    }
    //usuario comum
    public Usuario save(String email, String senha){
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setRole(UsuarioRole.ROLE_USER);
        if (repo.findByEmail(email).isPresent())
            throw new UsuarioException("Já existe usuario com este email");
        return repo.save(usuario);
    }

    public Usuario findByEmail(String email){

        return repo.findByEmail(email) //EntityNotFoundException() é uma exception q usamos
                .orElseThrow(() -> new EntityNotFoundException());//quando nao encontramos um obj ou um registro no banco de dados
    }

    public Usuario resetarSenha(String email, String senhaNova){

        Usuario usuario = findByEmail(email);
        usuario.setSenha(passwordEncoder.encode(senhaNova));

        return repo.save(usuario);

    }
}
