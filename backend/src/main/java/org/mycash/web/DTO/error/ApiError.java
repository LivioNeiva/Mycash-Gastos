package org.mycash.web.DTO.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 DTO -> Obj para transporte de dados:
 */
//classe vai representar um erro API
//essaanotation nao exibi essa msg vazia quando exception é lançada subErros": []
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {

    private HttpStatus status;
    private LocalDateTime dataHora;
    private String messagem;

    /*
    vamos criar uma lista de objetos, que seram subErros e iremos adicionar a essa lista.
    adicionaremos os erros q ocorreram em cada um dos campos
     */
    private List<String> subErros = new ArrayList<>();

    public ApiError(HttpStatus status){
        this.status=status;
        this.dataHora=dataHora.now(); //instancia a data e hora momentanea
    }
    //lista de erros quando ocorre validation vai ser adicionada no List subErros
    public void addValidationErrors(List<FieldError> fieldError) {
        this.subErros = fieldError.stream() //list tipo fieldError vai ser stream
                                .map(fe -> fe.getDefaultMessage()) //vamos coletar todas as msg de erros
                                .collect(Collectors.toList()); //vamos transformar em uma lista

    }

    public List<String> getSubErros() {
        return subErros;
    }

    public void setSubErros(List<String> subErros) {
        this.subErros = subErros;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getMessagem() {
        return messagem;
    }

    public void setMessagem(String messagem) {
        this.messagem = messagem;
    }


}
