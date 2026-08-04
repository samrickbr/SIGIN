package br.com.inova.sigin.financeiro.service;

import br.com.inova.sigin.financeiro.dto.ContaReceberResponse;
import br.com.inova.sigin.financeiro.mapper.ContaReceberMapper;
import br.com.inova.sigin.financeiro.repository.ContaReceberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaReceberService {

    private final ContaReceberRepository repository;

    public List<ContaReceberResponse> listar(){
        return repository.findAll()
                .stream()
                .map(ContaReceberMapper::toResponse)
                .toList();
    }

    public ContaReceberResponse buscar(Long id){
        return repository.findById(id)
                .map(ContaReceberMapper::toResponse)
                .orElseThrow();
    }
}