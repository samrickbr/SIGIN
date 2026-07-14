package br.com.inova.sigin.pedido.controller;

import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.pedido.dto.PedidoRequest;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final OrdemProducaoRepository ordemProducaoRepository;
    private final LocalRepository localRepository;


    @PostMapping
    public ResponseEntity<PedidoResponse> criar(
            @Valid @RequestBody PedidoRequest request) {

        return ResponseEntity.ok(
                service.criar(request)
        );
    }


    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.cancelar(id)
        );
    }

    @PatchMapping("/{id}/producao")
    public ResponseEntity<PedidoResponse> enviarParaProducao(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.enviarParaProducao(id)
        );
    }
}