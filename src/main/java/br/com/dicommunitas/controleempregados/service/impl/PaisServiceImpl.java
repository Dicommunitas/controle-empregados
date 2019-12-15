package br.com.dicommunitas.controleempregados.service.impl;

import br.com.dicommunitas.controleempregados.service.PaisService;
import br.com.dicommunitas.controleempregados.domain.Pais;
import br.com.dicommunitas.controleempregados.repository.PaisRepository;
import br.com.dicommunitas.controleempregados.repository.search.PaisSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Pais}.
 */
@Service
@Transactional
public class PaisServiceImpl implements PaisService {

    private final Logger log = LoggerFactory.getLogger(PaisServiceImpl.class);

    private final PaisRepository paisRepository;

    private final PaisSearchRepository paisSearchRepository;

    public PaisServiceImpl(PaisRepository paisRepository, PaisSearchRepository paisSearchRepository) {
        this.paisRepository = paisRepository;
        this.paisSearchRepository = paisSearchRepository;
    }

    /**
     * Save a pais.
     *
     * @param pais the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pais save(Pais pais) {
        log.debug("Request to save Pais : {}", pais);
        Pais result = paisRepository.save(pais);
        paisSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the pais.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pais> findAll() {
        log.debug("Request to get all Pais");
        return paisRepository.findAll();
    }


    /**
     * Get one pais by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pais> findOne(Long id) {
        log.debug("Request to get Pais : {}", id);
        return paisRepository.findById(id);
    }

    /**
     * Delete the pais by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pais : {}", id);
        paisRepository.deleteById(id);
        paisSearchRepository.deleteById(id);
    }

    /**
     * Search for the pais corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pais> search(String query) {
        log.debug("Request to search Pais for query {}", query);
        return StreamSupport
            .stream(paisSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
