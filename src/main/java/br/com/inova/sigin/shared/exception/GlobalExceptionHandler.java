package br.com.inova.sigin.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocio(
            RegraNegocioException ex
    ) {

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );

        HttpStatus status = "Pessoa já possui usuário.".equals(ex.getMessage())
                ? HttpStatus.CONFLICT
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(status)
                .body(erro);
    }

}