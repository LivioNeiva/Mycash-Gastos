package org.mycash.repository;

import org.mycash.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /*
    faz pesquisa de um usuario por email, o retorno vai ser do tipo optional, o findBy é um
    metodo que segue um padrão de nomenclatura que ja faz Spring entender o que vc quer fazer,
    qual pesquisa vc quer realizar, e ele mesmo ja fornece a implementação para vc, sem o dev ter
    que escrever nada.
    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
     */
    Optional<Usuario> findByEmail(String userName);
}
