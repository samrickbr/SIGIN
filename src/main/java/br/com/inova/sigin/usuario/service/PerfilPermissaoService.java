package br.com.inova.sigin.usuario.service;

import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.usuario.dto.PerfilPermissaoRequest;
import br.com.inova.sigin.usuario.dto.PerfilPermissaoResponse;
import br.com.inova.sigin.usuario.entity.Perfil;
import br.com.inova.sigin.usuario.entity.PerfilPermissao;
import br.com.inova.sigin.usuario.entity.Permissao;
import br.com.inova.sigin.usuario.mapper.PerfilPermissaoMapper;
import br.com.inova.sigin.usuario.repository.PerfilPermissaoRepository;
import br.com.inova.sigin.usuario.repository.PerfilRepository;
import br.com.inova.sigin.usuario.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilPermissaoService {

    private final PerfilPermissaoRepository repository;
    private final PerfilRepository perfilRepository;
    private final PermissaoRepository permissaoRepository;
    private final PerfilPermissaoMapper mapper;


    public PerfilPermissaoResponse criar(
            PerfilPermissaoRequest request
    ) {

        repository
                .findByPerfilIdAndPermissaoId(
                        request.perfilId(),
                        request.permissaoId()
                )
                .ifPresent(p -> {
                    throw new RegraNegocioException(
                            "Permissão já vinculada ao perfil."
                    );
                });


        Perfil perfil = perfilRepository.findById(
                request.perfilId()
        ).orElseThrow(() ->
                new RegraNegocioException(
                        "Perfil não encontrado."
                )
        );


        Permissao permissao = permissaoRepository.findById(
                request.permissaoId()
        ).orElseThrow(() ->
                new RegraNegocioException(
                        "Permissão não encontrada."
                )
        );


        PerfilPermissao entity = PerfilPermissao.builder()
                .perfil(perfil)
                .permissao(permissao)
                .build();


        return mapper.toResponse(
                repository.save(entity)
        );
    }


    public List<PerfilPermissaoResponse> listar(){

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    public void excluir(Long id){

        if(!repository.existsById(id)){
            throw new RegraNegocioException(
                    "Vínculo não encontrado."
            );
        }

        repository.deleteById(id);
    }
}