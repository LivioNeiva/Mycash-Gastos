package org.mycash.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer //vamos habilitar o AuthorizationServer(autorização de serviço)
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

    //essa variavel sera informada no arquivo de configuração. propeties
    @Value("${mycash.clientId}")
    private String clientId;

    @Value("${mycash.clientSecret}")
    private String clientSecret;//senha do cliente
    /*
    temos que criptografar a senha clientSecret, com a classe do spring security
    terá uma implementation is BCryptPasswordEncoder, q é um algoritmo pra gerar
    um rest de uma string, ele é muito seguro, ele é capaz de gerar um rest
    para cada vez q vc chamar a mesma senha, ele mesmo consegue validar se a senha
    é valida ou não.
    */
    @Autowired
    private PasswordEncoder passWordEncoder; //recebe a senha do cliente

    @Autowired
    UserDetailsService userDetailsService; //responsavel por buscar os dados do cliente no banco de dados

    @Autowired
    private AuthenticationManager authenticationManager; //gerencia a autenticação do login


    //esse metodo está relacionado ao cliente q está utilizando nossa aplicação.como eles vao se autenticar
    @Override
    public void configure(//metodo de configurar cliente
            ClientDetailsServiceConfigurer clients) throws Exception{

        //vamos informar quais sao os clientes estára usando nossa aplicação
        //vamos informar um id e uma chave secreta para cada uma das aplicação
        clients.inMemory() //faremos a configuração em memoria, mais pode ser tb em um banco de dados
                .withClient(clientId) // informamos o id do cliente
                .secret(passWordEncoder.encode(clientSecret)) //vamos informar a senha do cliente
                .accessTokenValiditySeconds(43200) //12h config relacionado ao token, a quant de segundos token vai está valido
                .refreshTokenValiditySeconds(2592000) //30d, a cada 30 dias haverá refresh
                .authorizedGrantTypes("password", "refresh_token") //tipos de grants, quais sao fluxos de authenticações que tem no alf
                .scopes("read", "write") //escopos
                .resourceIds("api"); //onde estaram nossos resouces(restController), na edpoints que começam com api. ex api/lancamento
            //.end() //se tivesemos mais de um cliente, é so repetir os passos acima
            //    .inMemory()
        }
        /*
        agora vamos configurar o enpoint, relacionado a forma q o usuario vai
        pegar o toquen. Vamos config os endpoints que vao está responsaveis
        por tratar o toquen e autenticação do usuario.
        */
        /*
        configurar endPoints. é a forma de vc mostrar para edpoints como ele vai buscar os detahes do usuario
        e qual vai ser o gerenciador de autenticação, a maneira padrão q está sendo autenticada
         */
        // Configuração relacionada aos endpoints de autorização /oauth/token.
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endPoints) throws Exception{

            //vamos fazer a configuração do endpoint, responsavel para fzer a autenticação do usuario
            //serviço que vai fica responsavel em ir buscas as informações do usuario
            endPoints
                    .accessTokenConverter(accessTokenConverter())//converte e enjeta dentro metodo
                    .userDetailsService(userDetailsService) //responsavel em buscas os dados do usuario no banco de dados e retornar aki
                    .authenticationManager(authenticationManager);//gerenciador de autenticação
        }

        /*
        metodo que vai gerar um bin responsavel por trazer uma instancia de um conversor de token generico
        prof fez sem o jwt, fez isso: AccessTokenConverter accessTokenConverter()
        Jwt é token que ele retorna, é token json, responsavel por 
        transportar dados codificado com base 64
        autho -> protocolo que especifica todo fluxo foi desenvolvimento
       */
        @Bean
        public JwtAccessTokenConverter accessTokenConverter(){

            return new JwtAccessTokenConverter();
        }
        /*
        obs. Adependencia do Spring Security que foi colocada no po,.xml 
        ja em com todas as classes utilitarias q precisamos
        
         */
}
