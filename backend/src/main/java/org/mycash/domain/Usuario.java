package org.mycash.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = { //o campo email sera inico na tabela no banco de dados , nao se repete
        @UniqueConstraint(columnNames = "email")
      })
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto, a criação do id sera de acordo com banco de dados será usado
    private Integer id;

    @Column(length = 100)
    private String email;

    //nao podemos expor a senhaa do usuaario, nao retorna no json
    //@JsonIgnore, vamos usar outra maneira proteger a senha, usando dto
    private String senha;

    @Enumerated(EnumType.STRING)//grava no banco de dados em forma de string
    private UsuarioRole role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsuarioRole getRole() {
        return role;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
    }
}
