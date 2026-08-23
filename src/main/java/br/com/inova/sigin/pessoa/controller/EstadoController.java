package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.enums.Estado;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    @GetMapping
    public ResponseEntity<List<EstadoResponse>> listar() {

        return ResponseEntity.ok(
                Arrays.stream(Estado.values())
                        .map(EstadoResponse::from)
                        .toList()
        );
    }

    @Getter
    @Builder
    private static class EstadoResponse {

        private String sigla;
        private String nome;

        private static EstadoResponse from(Estado estado) {
            return EstadoResponse.builder()
                    .sigla(estado.getSigla())
                    .nome(estado.getNome())
                    .build();
        }
    }
}