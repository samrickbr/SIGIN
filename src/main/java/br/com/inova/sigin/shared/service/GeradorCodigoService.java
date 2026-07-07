package br.com.inova.sigin.shared.service;

import org.springframework.stereotype.Service;

@Service
public class GeradorCodigoService {

    public String gerarCodigoProduto(Long id) {
        return String.format("PROD%06d", id);
    }

    public String gerarCodigoCategoria(Long id) {
        return String.format("CAT%06d", id);
    }

    public String gerarCodigoCliente(Long id) {
        return String.format("CLI%06d", id);
    }

    public String gerarCodigoParceiro(Long id) {
        return String.format("PAR%06d", id);
    }
}