package org.mycash.security;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
essa classe é justamente para nosso resource server, nosso servidor de recursos. forma vai
buscar os dados na nossa api, melhora a msg de retorno quando houver falha na
autenticação e quando tentar acessessar um recurso nao autorizado, ou falha no login.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*
    quando ocorrer algum erro na autenticação, esse metodo quem vai ser chamado
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        /*
        em algumas situações o uso do codigo httpStatus, ele fica meio confuso entre 401 e 403
        401 quando nao está authenticado, 403 quando nao está autorizado,senao fizermos esse
        confguração         haverá alguns casos que ele vai retornar o codigo 401 quando na
        verdade ele deveria está retornando o 403
        */
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");

        //agora vamos informar como ele faz para buscar um usuario
    }
}
