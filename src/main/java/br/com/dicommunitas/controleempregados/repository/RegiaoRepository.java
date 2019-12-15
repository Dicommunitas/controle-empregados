package br.com.dicommunitas.controleempregados.repository;
import br.com.dicommunitas.controleempregados.domain.Regiao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Regiao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long> {

}
