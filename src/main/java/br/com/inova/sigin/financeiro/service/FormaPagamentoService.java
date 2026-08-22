package br.com.inova.sigin.financeiro.service;

import br.com.inova.sigin.financeiro.dto.FormaPagamentoRequest;
import br.com.inova.sigin.financeiro.dto.FormaPagamentoResponse;
import br.com.inova.sigin.financeiro.entity.FormaPagamento;
import br.com.inova.sigin.financeiro.mapper.FormaPagamentoMapper;
import br.com.inova.sigin.financeiro.repository.FormaPagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository repository;

    @Transactional
    public FormaPagamentoResponse salvar(FormaPagamentoRequest dto) {

        FormaPagamento entity = FormaPagamento.builder()
                .descricao(dto.descricao())
                .ativo(true)
                .baixaAutomatica(
                        dto.baixaAutomatica() != null
                                && dto.baixaAutomatica()
                )
                .build();

        return FormaPagamentoMapper.toDTO(
                repository.save(entity)
        );
    }

    @Transactional(readOnly = true)
    public List<FormaPagamentoResponse> listar() {

        return repository.findAll()
                .stream()
                .map(FormaPagamentoMapper::toDTO)
                .toList();
    }

    @Transactional
    public FormaPagamentoResponse atualizar(
            Long id,
            FormaPagamentoRequest dto
    ) {

        FormaPagamento entity = buscarEntidade(id);

        entity.setDescricao(dto.descricao());
        entity.setBaixaAutomatica(
                dto.baixaAutomatica() != null
                        && dto.baixaAutomatica()
        );

        return FormaPagamentoMapper.toDTO(
                repository.save(entity)
        );
    }

    @Transactional
    public FormaPagamentoResponse alterarAtivo(
            Long id,
            Boolean ativo
    ) {

        FormaPagamento entity = buscarEntidade(id);

        entity.setAtivo(ativo);

        return FormaPagamentoMapper.toDTO(
                repository.save(entity)
        );
    }

    @Transactional
    public void excluir(Long id) {

        FormaPagamento entity = buscarEntidade(id);

        entity.setAtivo(false);

        repository.save(entity);
    }

    private FormaPagamento buscarEntidade(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Forma de pagamento não encontrada."
                        ));
    }
}