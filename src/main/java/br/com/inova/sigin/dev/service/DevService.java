package br.com.inova.sigin.dev.service;

import br.com.inova.sigin.dev.seed.Seeder;
import br.com.inova.sigin.pedido.service.PedidoItemService;
import br.com.inova.sigin.pedido.service.PedidoService;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevService {

    private final List<Seeder> seeders;
    private final PessoaRepository pessoaRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoService pedidoService;
    private final PedidoItemService pedidoItemService;

    public List<String> popular() {

        List<String> executados = new ArrayList<>();

        for (Seeder seeder : seeders) {
            seeder.executar();
            executados.add(seeder.getNome());
        }
        return executados;
    }
}