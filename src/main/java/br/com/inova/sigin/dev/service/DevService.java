package br.com.inova.sigin.dev.service;

import br.com.inova.sigin.dev.seed.Seeder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevService {

    private final List<Seeder> seeders;

    public List<String> popular() {

        List<String> executados = new ArrayList<>();

        for (Seeder seeder : seeders) {
            seeder.executar();
            executados.add(seeder.getNome());
        }
        return executados;
    }
}