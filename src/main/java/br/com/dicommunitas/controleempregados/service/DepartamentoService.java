package br.com.dicommunitas.controleempregados.service;

import br.com.dicommunitas.controleempregados.domain.Departamento;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Departamento}.
 */
public interface DepartamentoService {

    /**
     * Save a departamento.
     *
     * @param departamento the entity to save.
     * @return the persisted entity.
     */
    Departamento save(Departamento departamento);

    /**
     * Get all the departamentos.
     *
     * @return the list of entities.
     */
    List<Departamento> findAll();


    /**
     * Get the "id" departamento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Departamento> findOne(Long id);

    /**
     * Delete the "id" departamento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the departamento corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Departamento> search(String query);
}
