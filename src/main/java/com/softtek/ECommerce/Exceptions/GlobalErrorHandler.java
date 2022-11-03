
package com.softtek.ECommerce.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
//extends ResponseEntityExceptionHandler -> agregado para poder manejar excepciones de argumenos invalidos
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    /*
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ResourceNotFoundException(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ResourceInvalidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String ResourceInvalidException(ResourceInvalidException ex) {
        return ex.getMessage();
    }

     */


    //agregado del video de la tecnologia avanza

    @ExceptionHandler(ResourceInvalidException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceInvalidException(ResourceInvalidException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles= new ErrorDetalles(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles= new ErrorDetalles(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ErrorException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles= new ErrorDetalles(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles,HttpStatus.BAD_REQUEST);
    }


    //metodo para manejar los argumentos invalidos cuando hace una validacion mediante @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> errores= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String nombreCampo= ((FieldError)error).getField();
            String mensaje=error.getDefaultMessage();

            errores.put(nombreCampo,mensaje);
        });
        ErrorCamposInvalidos listaErrores=new ErrorCamposInvalidos(new Date(),"Campos Incorrectos", errores);
        //return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(listaErrores,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetalles errorDetalles= new ErrorDetalles(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //para error de autenticacion
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ErrorException  error = new ErrorException(ex.getMessage());
        //return ResponseEntity.status(status).headers(headers).body(error);
        return new ResponseEntity<>(error,status);
    }
}
