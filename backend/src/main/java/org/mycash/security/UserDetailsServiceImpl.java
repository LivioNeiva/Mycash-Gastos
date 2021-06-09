package org.mycash.security;

import org.mycash.domain.Usuario;
import org.mycash.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepo;

    /*
    no metodo abaixo o fluxo de autenticação ele vai receber usuario e senha e vai tentar fazer a autenticaçao
    do usuario, o metodo a baixo vai fornecer uma maneira de está buscando o usuario no banco de dados
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepo //buscando o usuario pelo email
                        .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Name não Encontrado"));
        /*
        criar uma authority a partir da role do nosso usuario, role se é usuario ou admin
         */
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRole().name());
        return new User(
                    usuario.getEmail(),
                    usuario.getSenha(),
                    Arrays.asList(authority));//retorna em forma de lista
    }
}
//01:11:53