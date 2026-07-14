package br.com.inova.sigin.configuracao.repository;

import br.com.inova.sigin.configuracao.entity.ConfiguracaoSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoSistemaRepository
        extends JpaRepository<ConfiguracaoSistema, Long> {
}
