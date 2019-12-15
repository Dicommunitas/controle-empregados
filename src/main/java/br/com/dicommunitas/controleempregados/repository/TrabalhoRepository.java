package br.com.dicommunitas.controleempregados.repository;
import br.com.dicommunitas.controleempregados.domain.Trabalho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Trabalho entity.
 */
@Repository
public interface TrabalhoRepository extends JpaRepository<Trabalho, Long> {

    @Query(value = "select distinct trabalho from Trabalho trabalho left join fetch trabalho.tarefas",
        countQuery = "select count(distinct trabalho) from Trabalho trabalho")
    Page<Trabalho> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct trabalho from Trabalho trabalho left join fetch trabalho.tarefas")
    List<Trabalho> findAllWithEagerRelationships();

    @Query("select trabalho from Trabalho trabalho left join fetch trabalho.tarefas where trabalho.id =:id")
    Optional<Trabalho> findOneWithEagerRelationships(@Param("id") Long id);

}
