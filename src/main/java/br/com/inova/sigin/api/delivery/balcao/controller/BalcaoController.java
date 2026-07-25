package br.com.inova.sigin.api.delivery.balcao.controller;

import br.com.inova.sigin.api.delivery.balcao.dto.BalcaoPedidoResponse;
import br.com.inova.sigin.api.delivery.balcao.service.BalcaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery/balcao")
@RequiredArgsConstructor
public class BalcaoController {

    private final BalcaoService service;

    @GetMapping("/pedidos")
    public List<BalcaoPedidoResponse> listar() {
        return service.listar();
    }

    @PutMapping("/{id}/aceitar")
    public void aceitar(@PathVariable Long id) {
        service.aceitar(id);
    }

    @PutMapping("/{id}/cancelar")
    public void cancelar(@PathVariable Long id) {
        service.cancelar(id);
    }

    @PutMapping("/{id}/cozinha")
    public void enviarParaCozinha(@PathVariable Long id) {
        service.enviarParaCozinha(id);
    }

}