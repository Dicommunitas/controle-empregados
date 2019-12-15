package br.com.dicommunitas.controleempregados.repository;
import br.com.dicommunitas.controleempregados.domain.Tarefa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tarefa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

}
