package br.com.inova.sigin.canalvenda.controller;

import br.com.inova.sigin.canalvenda.dto.CanalVendaResponse;
import br.com.inova.sigin.canalvenda.service.CanalVendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canais-venda")
@RequiredArgsConstructor
@Tag(
        name = "Canal de Venda",
        description = "Consulta dos canais sistêmicos de venda do SIGIN"
)
public class CanalVendaController {

    private final CanalVendaService service;

    @Operation(summary = "Listar canais de venda sistêmicos")
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso"
    )
    @GetMapping
    public ResponseEntity<List<CanalVendaResponse>> listar(
            @RequestParam(required = false) Boolean ativo) {

        return ResponseEntity.ok(
                service.listar(ativo)
        );
    }

    @Operation(summary = "Buscar canal de venda sistêmico por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Canal encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Canal não encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CanalVendaResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }
}