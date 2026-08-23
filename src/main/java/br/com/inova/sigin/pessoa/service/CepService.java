package br.com.inova.sigin.pessoa.service;

import br.com.inova.sigin.pessoa.dto.CepResponse;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CepService {

    private static final String BASE_URL = "https://viacep.com.br/ws";

    private final RestClient.Builder restClientBuilder;

    public CepResponse buscar(String cep) {

        String cepNormalizado = normalizarCep(cep);

        if (cepNormalizado.length() != 8) {
            throw new RegraNegocioException(
                    "CEP inválido"
            );
        }

        CepResponse response;

        try {
            response = restClientBuilder
                    .baseUrl(BASE_URL)
                    .build()
                    .get()
                    .uri("/{cep}/json/", cepNormalizado)
                    .retrieve()
                    .body(CepResponse.class);

        } catch (Exception e) {
            throw new RegraNegocioException(
                    "Não foi possível consultar o CEP"
            );
        }

        if (response == null) {
            throw new RegraNegocioException(
                    "Não foi possível consultar o CEP"
            );
        }

        if (Boolean.TRUE.equals(response.getErro())) {
            throw new RegraNegocioException(
                    "CEP não encontrado"
            );
        }

        return response;
    }

    private String normalizarCep(String cep) {

        if (cep == null) {
            return "";
        }

        return cep.replaceAll("\\D", "");
    }
}