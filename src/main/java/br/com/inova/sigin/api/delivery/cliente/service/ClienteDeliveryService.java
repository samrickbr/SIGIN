package br.com.inova.sigin.api.delivery.cliente.service;

import br.com.inova.sigin.api.delivery.cliente.dto.ClienteRequest;
import br.com.inova.sigin.api.delivery.cliente.dto.ClienteResponse;
import br.com.inova.sigin.pessoa.dto.PessoaRequest;
import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.pessoa.service.PessoaService;
import br.com.inova.sigin.pessoa.service.PessoaTipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteDeliveryService {

    private final PessoaRepository pessoaRepository;
    private final PessoaService pessoaService;
    private final PessoaTipoService pessoaTipoService;

    public ClienteResponse buscarPorTelefone(String telefone) {

        Pessoa pessoa = pessoaRepository.findByTelefone(telefone)
                .orElseThrow();

        pessoaTipoService.adicionarTipoCliente(pessoa.getId());

        return converter(pessoa);
    }

    public ClienteResponse buscarPorDocumento(String documento) {

        Pessoa pessoa = pessoaRepository.findByDocumento(documento)
                .orElseThrow();

        pessoaTipoService.adicionarTipoCliente(pessoa.getId());

        return converter(pessoa);
    }

    public ClienteResponse criar(ClienteRequest request) {

        Pessoa pessoaExistente = pessoaRepository
                .findByDocumento(request.getDocumento())
                .orElseGet(() ->
                        pessoaRepository
                                .findByTelefone(request.getTelefone())
                                .orElse(null)
                );

        if (pessoaExistente != null) {

            pessoaTipoService.adicionarTipoCliente(
                    pessoaExistente.getId()
            );

            return converter(pessoaExistente);
        }

        PessoaRequest pessoaRequest = new PessoaRequest();

        pessoaRequest.setNome(request.getNome());
        pessoaRequest.setTipoDocumento("CPF");
        pessoaRequest.setDocumento(request.getDocumento());
        pessoaRequest.setTelefone(request.getTelefone());
        pessoaRequest.setEmail(request.getEmail());

        PessoaResponse pessoa = pessoaService.criar(pessoaRequest);

        pessoaTipoService.adicionarTipoCliente(pessoa.getId());

        return ClienteResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .email(pessoa.getEmail())
                .build();
    }

    private ClienteResponse converter(Pessoa pessoa) {

        return ClienteResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .email(pessoa.getEmail())
                .build();
    }
}