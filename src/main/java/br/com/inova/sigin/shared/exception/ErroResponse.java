package br.com.inova.sigin.shared.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErroResponse {

    private final LocalDateTime timestamp;
    private final String mensagem;


    public ErroResponse(LocalDateTime timestamp, String mensagem) {
        this.timestamp = timestamp;
        this.mensagem = mensagem;
    }
}