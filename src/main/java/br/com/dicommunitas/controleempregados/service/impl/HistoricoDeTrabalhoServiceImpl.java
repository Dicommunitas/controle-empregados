package br.com.dicommunitas.controleempregados.service.impl;

import br.com.dicommunitas.controleempregados.service.HistoricoDeTrabalhoService;
import br.com.dicommunitas.controleempregados.domain.HistoricoDeTrabalho;
import br.com.dicommunitas.controleempregados.repository.HistoricoDeTrabalhoRepository;
import br.com.dicommunitas.controleempregados.repository.search.HistoricoDeTrabalhoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link HistoricoDeTrabalho}.
 */
@Service
@Transactional
public class HistoricoDeTrabalhoServiceImpl implements HistoricoDeTrabalhoService {

    private final Logger log = LoggerFactory.getLogger(HistoricoDeTrabalhoServiceImpl.class);

    private final HistoricoDeTrabalhoRepository historicoDeTrabalhoRepository;

    private final HistoricoDeTrabalhoSearchRepository historicoDeTrabalhoSearchRepository;

    public HistoricoDeTrabalhoServiceImpl(HistoricoDeTrabalhoRepository historicoDeTrabalhoRepository, HistoricoDeTrabalhoSearchRepository historicoDeTrabalhoSearchRepository) {
        this.historicoDeTrabalhoRepository = historicoDeTrabalhoRepository;
        this.historicoDeTrabalhoSearchRepository = historicoDeTrabalhoSearchRepository;
    }

    /**
     * Save a historicoDeTrabalho.
     *
     * @param historicoDeTrabalho the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HistoricoDeTrabalho save(HistoricoDeTrabalho historicoDeTrabalho) {
        log.debug("Request to save HistoricoDeTrabalho : {}", historicoDeTrabalho);
        HistoricoDeTrabalho result = historicoDeTrabalhoRepository.save(historicoDeTrabalho);
        historicoDeTrabalhoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the historicoDeTrabalhos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoricoDeTrabalho> findAll(Pageable pageable) {
        log.debug("Request to get all HistoricoDeTrabalhos");
        return historicoDeTrabalhoRepository.findAll(pageable);
    }


    /**
     * Get one historicoDeTrabalho by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HistoricoDeTrabalho> findOne(Long id) {
        log.debug("Request to get HistoricoDeTrabalho : {}", id);
        return historicoDeTrabalhoRepository.findById(id);
    }

    /**
     * Delete the historicoDeTrabalho by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistoricoDeTrabalho : {}", id);
        historicoDeTrabalhoRepository.deleteById(id);
        historicoDeTrabalhoSearchRepository.deleteById(id);
    }

    /**
     * Search for the historicoDeTrabalho corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoricoDeTrabalho> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HistoricoDeTrabalhos for query {}", query);
        return historicoDeTrabalhoSearchRepository.search(queryStringQuery(query), pageable);    }
}
