package br.com.inova.sigin.canalvenda.controller;

import br.com.inova.sigin.canalvenda.dto.CanalVendaRequest;
import br.com.inova.sigin.canalvenda.dto.CanalVendaResponse;
import br.com.inova.sigin.canalvenda.service.CanalVendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canais-venda")
@RequiredArgsConstructor
@Tag(name = "Canal de Venda", description = "Gerenciamento dos canais de venda do SIGIN")
public class CanalVendaController {

    private final CanalVendaService service;

    @Operation(summary = "Cadastrar canal de venda")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Canal cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<CanalVendaResponse> cadastrar(
            @Valid @RequestBody CanalVendaRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.cadastrar(request));
    }

    @Operation(summary = "Listar canais de venda")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<CanalVendaResponse>> listar(
            @RequestParam(required = false) Boolean ativo) {

        return ResponseEntity.ok(service.listar(ativo));
    }

    @Operation(summary = "Buscar canal de venda por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Canal encontrado"),
            @ApiResponse(responseCode = "404", description = "Canal não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CanalVendaResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualizar canal de venda")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Canal atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Canal não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CanalVendaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CanalVendaRequest request) {

        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @Operation(summary = "Excluir canal de venda")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Canal excluído"),
            @ApiResponse(responseCode = "404", description = "Canal não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}