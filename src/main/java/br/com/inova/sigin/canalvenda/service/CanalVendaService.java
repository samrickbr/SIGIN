package br.com.inova.sigin.canalvenda.service;

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

    private CanalVenda buscarEntidade(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Canal de venda não encontrado."
                        ));
    }
}