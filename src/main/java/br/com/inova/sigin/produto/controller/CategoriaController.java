package br.com.inova.sigin.produto.controller;

import br.com.inova.sigin.produto.entity.Categoria;
import br.com.inova.sigin.produto.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;


    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }


    @PostMapping
    public Categoria salvar(@RequestBody Categoria categoria) {

        if (categoria.getAtivo() == null) {
            categoria.setAtivo(true);
        }

        if (categoria.getDataCriacao() == null) {
            categoria.setDataCriacao(LocalDateTime.now());
        }

        return categoriaRepository.save(categoria);
    }

}