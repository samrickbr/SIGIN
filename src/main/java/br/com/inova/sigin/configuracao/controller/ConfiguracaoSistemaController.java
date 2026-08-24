package br.com.inova.sigin.configuracao.controller;

import br.com.inova.sigin.configuracao.dto.ConfiguracaoSistemaRequest;
import br.com.inova.sigin.configuracao.dto.ConfiguracaoSistemaResponse;
import br.com.inova.sigin.configuracao.service.ConfiguracaoSistemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracoes-sistema")
@RequiredArgsConstructor
public class ConfiguracaoSistemaController {

    private final ConfiguracaoSistemaService service;

    @GetMapping
    public ResponseEntity<ConfiguracaoSistemaResponse> buscar() {

        return ResponseEntity.ok(
                ConfiguracaoSistemaResponse.builder()
                        .taxaEntregaPadrao(
                                service.getTaxaEntregaPadrao()
                        )
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ConfiguracaoSistemaResponse> atualizar(
            @Valid @RequestBody ConfiguracaoSistemaRequest request
    ) {

        return ResponseEntity.ok(
                ConfiguracaoSistemaResponse.builder()
                        .taxaEntregaPadrao(
                                service.atualizarTaxaEntregaPadrao(
                                        request.getTaxaEntregaPadrao()
                                )
                        )
                        .build()
        );
    }
}