package br.com.inova.sigin.produtocanal.controller;

import br.com.inova.sigin.produtocanal.dto.ProdutoCanalRequest;
import br.com.inova.sigin.produtocanal.dto.ProdutoCanalResponse;
import br.com.inova.sigin.produtocanal.service.ProdutoCanalService;
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
@RequestMapping("/api/produtos-canais")
@RequiredArgsConstructor
@Tag(name = "Produto x Canal", description = "Gerenciamento da disponibilidade de produtos por canal de venda")
public class ProdutoCanalController {

    private final ProdutoCanalService service;

    @Operation(summary = "Cadastrar vínculo Produto x Canal")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vínculo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoCanalResponse> cadastrar(
            @Valid @RequestBody ProdutoCanalRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.cadastrar(request));
    }

    @Operation(summary = "Listar vínculos Produto x Canal")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ProdutoCanalResponse>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar vínculo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vínculo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoCanalResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualizar vínculo Produto x Canal")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vínculo atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoCanalResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoCanalRequest request) {

        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @Operation(summary = "Excluir vínculo Produto x Canal")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vínculo excluído"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }

}