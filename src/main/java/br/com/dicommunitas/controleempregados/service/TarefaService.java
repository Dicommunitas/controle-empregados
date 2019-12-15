package br.com.dicommunitas.controleempregados.service;

import br.com.dicommunitas.controleempregados.domain.Tarefa;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tarefa}.
 */
public interface TarefaService {

    /**
     * Save a tarefa.
     *
     * @param tarefa the entity to save.
     * @return the persisted entity.
     */
    Tarefa save(Tarefa tarefa);

    /**
     * Get all the tarefas.
     *
     * @return the list of entities.
     */
    List<Tarefa> findAll();


    /**
     * Get the "id" tarefa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tarefa> findOne(Long id);

    /**
     * Delete the "id" tarefa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the tarefa corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Tarefa> search(String query);
}
