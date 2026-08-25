package br.com.inova.sigin.produto.repository;

import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.enums.Setor;
import org.springframework.data.jpa.domain.Specification;

public final class ProdutoSpecification {

    private ProdutoSpecification() {
    }

    public static Specification<Produto> busca(String busca) {
        return (root, query, cb) -> {
            if (busca == null || busca.isBlank()) {
                return null;
            }

            String termo = "%" + busca.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("codigo")), termo),
                    cb.like(cb.lower(root.get("nome")), termo),
                    cb.like(cb.lower(root.get("descricao")), termo)
            );
        };
    }

    public static Specification<Produto> categoria(Long categoriaId) {
        return (root, query, cb) -> {
            if (categoriaId == null) {
                return null;
            }

            return cb.equal(
                    root.get("categoria").get("id"),
                    categoriaId
            );
        };
    }

    public static Specification<Produto> semCategoria(boolean ativo) {
        return (root, query, cb) ->
                ativo
                        ? cb.isNull(root.get("categoria"))
                        : null;
    }

    public static Specification<Produto> setor(Setor setor) {
        return (root, query, cb) -> {
            if (setor == null) {
                return null;
            }

            return cb.equal(root.get("setor"), setor);
        };
    }

    public static Specification<Produto> semSetor(boolean ativo) {
        return (root, query, cb) ->
                ativo
                        ? cb.isNull(root.get("setor"))
                        : null;
    }

    public static Specification<Produto> disponivelVenda(Boolean disponivelVenda) {
        return (root, query, cb) -> {
            if (disponivelVenda == null) {
                return null;
            }

            return cb.equal(
                    root.get("disponivelVenda"),
                    disponivelVenda
            );
        };
    }

    public static Specification<Produto> ativo(Boolean ativo) {
        return (root, query, cb) -> {
            if (ativo == null) {
                return null;
            }

            return cb.equal(root.get("ativo"), ativo);
        };
    }
}