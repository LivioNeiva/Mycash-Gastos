package org.mycash.security;

import javassist.bytecode.ExceptionsAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//proxyTargetClass = true =>
//prePostEnabled = true => habilita o pre e post na autorização dos acessos ao usuario
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)//essa anotation vai possibilitar a configuração atraves de annotations nos metodos q eu quero fazer a segurança
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService; //responsavel por buscar os dados do cliente no banco de dados

    /*
    essa classe é justamente para nosso resource server, nosso servidor de recursos. como ele vai
    buscar ndos dados na nossa api vai melhorar a msg quando é retornada quando houver falha na
    autenticação e quando tentar acessessar um recurso nao autorizado, ou falha no login.
     */
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    //injetado na classe OAutoConfiguration
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        /*
        quando é lançada uma sessão, todo funcionamento da aplicaçao de forma geral é receber requisição,
        processar e gerar uma resposta, nenhuma informação ficará armazenada em memoria
         */
        //vamos definir como o usuario pode ou nao está acesseçando os endPoints da nossa aplicação
        http //como é uma aplication rest
                .sessionManagement()// a forma sera gerenciado a sessão. sessão é comunicação criada entre usuario e aplicação
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //a forma gerencia a sessao do tipo STATELESS, ou seja,STATELESS qualquer informação que enviamos para api,nao ficara armazenado na sessão
        .and() //vamos definir as regras do endPoint que serão acessados
                .authorizeRequests() //atorização para requisição
                //vamos procurar os padrões para cada padrao q for encontrado, será add uma regra. ** siguinifica qualquer coisa que vinher depois de lancamento
                .antMatchers("/mycash/api/lancamento/**") ////para api delancamento, vamos inserir uma regra, o usuario que acessar esse endpoint precisa ter pelo menos algumas dessas autorizaçao ou role
                .hasAnyAuthority("ADMIN", "USER")//autorizando admin e usuario
                //.hasAnyRole("ADMIN", "USER") //para acessar lancamento precisara ter um admin ou um usuario
                //.antMatchers("mycash/api/usuario/**").hasAnyRole("ADMIN") //para acessar usuario eu preciso ser admin
                .anyRequest()// Para qualquer requisição (anyRequest) é preciso estar, autenticado (authenticated).
                .authenticated() //qualquer outra requisição eu coloco authenticated(), caso eu tenha esquecido algum mapeamento acima, eu preciso está autenticado
        .and() //configuração para manipular a exception quando ocorrer qualquer problema no momento da atenticação
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }

}
