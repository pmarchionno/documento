package com.documentos.documentos.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)


/* 
 * La clase ResourCeNotFoundException es una excepción personalizada que se lanza cuando un recurso solicitado
 * no se encuentra, es para cuando no se encuentra la busqueda te muestra un mensaje 
 */
public class ResourceNotFoundException extends RuntimeException{
    
    private static final long serialVersionID = 1L;

    public ResourceNotFoundException (String mensaje){
        super(mensaje);
    }
}
