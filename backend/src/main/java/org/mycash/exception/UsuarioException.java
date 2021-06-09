package org.mycash.exception;

import javassist.SerialVersionUID;

import java.io.Serializable;

/*
gera uma exception da classe usuario que sera tratada pela pela classe
CustomGlobalExceptionHandler
 */
public class UsuarioException extends RuntimeException {

    private static final long SerialVersionUID = 7587198243777580526L;

    public UsuarioException() {
        super();
    }

    public UsuarioException(String message) {
        super(message);
    }

    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioException(Throwable cause) {
        super(cause);
    }

    public UsuarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
