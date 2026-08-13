package br.com.inova.sigin.canalvenda.service;

import br.com.inova.sigin.canalvenda.dto.CanalVendaRequest;
import br.com.inova.sigin.canalvenda.dto.CanalVendaResponse;
import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.canalvenda.mapper.CanalVendaMapper;
import br.com.inova.sigin.canalvenda.repository.CanalVendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CanalVendaService {

    private final CanalVendaRepository repository;

    @Transactional
    public CanalVendaResponse cadastrar(CanalVendaRequest request) {

        validarNomeDuplicado(request.getNome());

        CanalVenda canal = CanalVendaMapper.toEntity(request);

        return CanalVendaMapper.toResponse(
                repository.save(canal)
        );
    }

    @Transactional(readOnly = true)
    public List<CanalVendaResponse> listar(Boolean ativo) {

        List<CanalVenda> canais;

        if (ativo == null) {
            canais = repository.findAll();
        } else if (ativo) {
            canais = repository.findByAtivoTrue();
        } else {
            canais = repository.findByAtivoFalse();
        }

        return canais.stream()
                .map(CanalVendaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CanalVendaResponse buscarPorId(Long id) {

        return CanalVendaMapper.toResponse(
                buscarEntidade(id)
        );
    }

    @Transactional
    public CanalVendaResponse atualizar(Long id, CanalVendaRequest request) {

        CanalVenda canal = buscarEntidade(id);

        if (!canal.getNome().equalsIgnoreCase(request.getNome())) {
            validarNomeDuplicado(request.getNome());
        }

        CanalVendaMapper.updateEntity(canal, request);

        return CanalVendaMapper.toResponse(
                repository.save(canal)
        );
    }

    @Transactional
    public void excluir(Long id) {

        CanalVenda canal = buscarEntidade(id);

        canal.setAtivo(false);

        repository.save(canal);
    }

    private CanalVenda buscarEntidade(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Canal de venda não encontrado."));
    }

    private void validarNomeDuplicado(String nome) {

        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new IllegalArgumentException("Já existe um canal de venda com este nome.");
        }
    }

}