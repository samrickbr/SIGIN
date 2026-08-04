package br.com.inova.sigin.produtovenda.controller;

import br.com.inova.sigin.produtovenda.dto.ProdutoVendaRequest;
import br.com.inova.sigin.produtovenda.dto.ProdutoVendaResponse;
import br.com.inova.sigin.produtovenda.service.ProdutoVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos-vendas")
@RequiredArgsConstructor
public class ProdutoVendaController {

    private final ProdutoVendaService service;


    @PostMapping
    public ProdutoVendaResponse cadastrar(
            @RequestBody ProdutoVendaRequest request
    ) {
        return service.cadastrar(request);
    }


    @GetMapping
    public List<ProdutoVendaResponse> listar() {
        return service.listar();
    }


    @GetMapping("/{id}")
    public ProdutoVendaResponse buscarPorId(
            @PathVariable Long id
    ) {
        return service.buscarPorId(id);
    }


    @PutMapping("/{id}")
    public ProdutoVendaResponse atualizar(
            @PathVariable Long id,
            @RequestBody ProdutoVendaRequest request
    ) {
        return service.atualizar(id, request);
    }


    @DeleteMapping("/{id}")
    public void excluir(
            @PathVariable Long id
    ) {
        service.excluir(id);
    }
}