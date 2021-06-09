package org.mycash.web;

import org.mycash.exception.UsuarioException;
import org.mycash.web.DTO.error.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

/*
essa classe serve para manipular exceptions, ela vai recebe a exception q ocorrer na
aplicação, e vai manimular essas mensagens de erro
 */

@RestControllerAdvice //todos os erros q acontecer nos @RestController vai cair na nessa  classe de tratamento
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, //quando ocorre um erro de validação fica armazenado nesse metodo ex
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request){

        /*
		vamos criar um objeto do tipo ApiError vai ser retornado no corpo requisição body()
		a classe ApiError foi criata para tratar dos Error das api.
		 */
        //colcoamos o status do error da requisição. error 400 not-found
        ApiError apiError = new ApiError(status.BAD_REQUEST);
        apiError.setMessagem("ERRO DE VALIDAÇÃO");
        apiError.addValidationErrors(ex.getFieldErrors()); //retorna um lista de erros validation

        return ResponseEntity
                .status(apiError.getStatus()) //INFORMA O STATUS DO ERRO, erro 400 erro de requisiçao, ela nao está bem informada
                .body(apiError);
    }

    /*
    vamos c um metodo que vai validar qualquer tipo de exception, inclusive a exception
    que criamos para classe usuario, caso o usuario tenha mesmo email, será criado uma
    constrains no banco de dados, pois criarmos uma regra na classe usuario no qual
    o email é unico.
     */
    //retorna uma resposta de entidade tratada, da class UsuarioException
    @ExceptionHandler(UsuarioException.class) //trata erros dessa classe
    public ResponseEntity<ApiError> handleUsuarioException(UsuarioException ex){
        //colcoamos o status do error da requisição. error 400 not-found
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessagem(ex.getMessage());

        //não haverá a lista de erros pois esse metodo so tem uma unica msg error
        //apiError.addValidationErrors(ex.getFieldErrors());

        return ResponseEntity
                    .status(apiError.getStatus())
                    .body(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex){

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessagem("Recurso não Encontrado");

        return ResponseEntity
                .status(apiError.getStatus())
                .body(apiError);

    }
}
