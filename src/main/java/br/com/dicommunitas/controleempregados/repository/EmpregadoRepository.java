package br.com.dicommunitas.controleempregados.repository;
import br.com.dicommunitas.controleempregados.domain.Empregado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Empregado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {

}
