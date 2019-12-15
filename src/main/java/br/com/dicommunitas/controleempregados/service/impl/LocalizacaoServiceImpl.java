package br.com.dicommunitas.controleempregados.service.impl;

import br.com.dicommunitas.controleempregados.service.LocalizacaoService;
import br.com.dicommunitas.controleempregados.domain.Localizacao;
import br.com.dicommunitas.controleempregados.repository.LocalizacaoRepository;
import br.com.dicommunitas.controleempregados.repository.search.LocalizacaoSearchRepository;
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
 * Service Implementation for managing {@link Localizacao}.
 */
@Service
@Transactional
public class LocalizacaoServiceImpl implements LocalizacaoService {

    private final Logger log = LoggerFactory.getLogger(LocalizacaoServiceImpl.class);

    private final LocalizacaoRepository localizacaoRepository;

    private final LocalizacaoSearchRepository localizacaoSearchRepository;

    public LocalizacaoServiceImpl(LocalizacaoRepository localizacaoRepository, LocalizacaoSearchRepository localizacaoSearchRepository) {
        this.localizacaoRepository = localizacaoRepository;
        this.localizacaoSearchRepository = localizacaoSearchRepository;
    }

    /**
     * Save a localizacao.
     *
     * @param localizacao the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Localizacao save(Localizacao localizacao) {
        log.debug("Request to save Localizacao : {}", localizacao);
        Localizacao result = localizacaoRepository.save(localizacao);
        localizacaoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the localizacaos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Localizacao> findAll() {
        log.debug("Request to get all Localizacaos");
        return localizacaoRepository.findAll();
    }


    /**
     * Get one localizacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Localizacao> findOne(Long id) {
        log.debug("Request to get Localizacao : {}", id);
        return localizacaoRepository.findById(id);
    }

    /**
     * Delete the localizacao by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Localizacao : {}", id);
        localizacaoRepository.deleteById(id);
        localizacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the localizacao corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Localizacao> search(String query) {
        log.debug("Request to search Localizacaos for query {}", query);
        return StreamSupport
            .stream(localizacaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
