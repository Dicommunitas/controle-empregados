package br.com.dicommunitas.controleempregados.service;

import br.com.dicommunitas.controleempregados.domain.Pais;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pais}.
 */
public interface PaisService {

    /**
     * Save a pais.
     *
     * @param pais the entity to save.
     * @return the persisted entity.
     */
    Pais save(Pais pais);

    /**
     * Get all the pais.
     *
     * @return the list of entities.
     */
    List<Pais> findAll();


    /**
     * Get the "id" pais.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pais> findOne(Long id);

    /**
     * Delete the "id" pais.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the pais corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Pais> search(String query);
}
