package br.com.dicommunitas.controleempregados.service.impl;

import br.com.dicommunitas.controleempregados.service.RegiaoService;
import br.com.dicommunitas.controleempregados.domain.Regiao;
import br.com.dicommunitas.controleempregados.repository.RegiaoRepository;
import br.com.dicommunitas.controleempregados.repository.search.RegiaoSearchRepository;
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
 * Service Implementation for managing {@link Regiao}.
 */
@Service
@Transactional
public class RegiaoServiceImpl implements RegiaoService {

    private final Logger log = LoggerFactory.getLogger(RegiaoServiceImpl.class);

    private final RegiaoRepository regiaoRepository;

    private final RegiaoSearchRepository regiaoSearchRepository;

    public RegiaoServiceImpl(RegiaoRepository regiaoRepository, RegiaoSearchRepository regiaoSearchRepository) {
        this.regiaoRepository = regiaoRepository;
        this.regiaoSearchRepository = regiaoSearchRepository;
    }

    /**
     * Save a regiao.
     *
     * @param regiao the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Regiao save(Regiao regiao) {
        log.debug("Request to save Regiao : {}", regiao);
        Regiao result = regiaoRepository.save(regiao);
        regiaoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the regiaos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Regiao> findAll() {
        log.debug("Request to get all Regiaos");
        return regiaoRepository.findAll();
    }


    /**
     * Get one regiao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Regiao> findOne(Long id) {
        log.debug("Request to get Regiao : {}", id);
        return regiaoRepository.findById(id);
    }

    /**
     * Delete the regiao by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Regiao : {}", id);
        regiaoRepository.deleteById(id);
        regiaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the regiao corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Regiao> search(String query) {
        log.debug("Request to search Regiaos for query {}", query);
        return StreamSupport
            .stream(regiaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
