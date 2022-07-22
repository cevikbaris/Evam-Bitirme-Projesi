package com.evam.app.shared;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {

    // For JSON format errors. Example: Variable integer but when I send string this method catch the error.
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        body.put("path", ((ServletWebRequest)request).getRequest().getRequestURI().toString());
        body.put("message", "Malformed JSON request");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // For @Valid process in the methods. @Valid controls entities and check the annotations like
    // @Size , @NotNull.  If values according to the annotations are not sent,
    // this method convert the errors that will occur in this method to json.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Validation failed for object = "+ex.getObjectName());
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        body.put("path", ((ServletWebRequest)request).getRequest().getRequestURI().toString());


        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
