package br.com.inova.sigin.produto.service;

import br.com.inova.sigin.produto.dto.CategoriaRequest;
import br.com.inova.sigin.produto.dto.CategoriaResponse;
import br.com.inova.sigin.produto.entity.Categoria;
import br.com.inova.sigin.produto.mapper.CategoriaMapper;
import br.com.inova.sigin.produto.repository.CategoriaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return repository.findAll()
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = buscarEntidade(id);

        return CategoriaMapper.toResponse(categoria);
    }

    @Transactional
    public CategoriaResponse cadastrar(CategoriaRequest request) {

        validarNomeDuplicado(request.getNome(), null);

        Categoria categoria = CategoriaMapper.toEntity(request);

        if (categoria.getAtivo() == null) {
            categoria.setAtivo(true);
        }

        Categoria salva = repository.save(categoria);

        return CategoriaMapper.toResponse(salva);
    }

    @Transactional
    public CategoriaResponse atualizar(
            Long id,
            CategoriaRequest request
    ) {
        Categoria categoria = buscarEntidade(id);

        validarNomeDuplicado(request.getNome(), id);

        CategoriaMapper.updateEntity(categoria, request);

        Categoria atualizada = repository.save(categoria);

        return CategoriaMapper.toResponse(atualizada);
    }

    private Categoria buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Categoria não encontrada")
                );
    }

    private void validarNomeDuplicado(String nome, Long id) {

        boolean duplicado;

        if (id == null) {
            duplicado = repository.findByNomeIgnoreCase(nome).isPresent();
        } else {
            duplicado = repository.existsByNomeIgnoreCaseAndIdNot(nome, id);
        }

        if (duplicado) {
            throw new RegraNegocioException(
                    "Já existe uma categoria com este nome"
            );
        }
    }
}