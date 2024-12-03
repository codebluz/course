package com.courses.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource message) {
        this.messageSource = message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<ErrorMessageDTO> dto = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ErrorMessageDTO error = new ErrorMessageDTO(err.getField(), message) ;
            dto.add(error);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        List<ErrorMessageDTO> dto = new ArrayList<>();

        String field = "requestBody";
        String message = "Invalid body request";

        Throwable cause = e.getCause();
        if (cause instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) cause;

            if (!jsonMappingException.getPath().isEmpty()) {
                field = jsonMappingException.getPath().get(0).getFieldName();  // Pega o primeiro campo inválido
                message = "Field [" + field + "] is invalid or contains incompatible data.";
            }
        }

        ErrorMessageDTO error = new ErrorMessageDTO(field, message);
        dto.add(error);

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
