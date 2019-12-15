package br.com.dicommunitas.controleempregados.service.impl;

import br.com.dicommunitas.controleempregados.service.DepartamentoService;
import br.com.dicommunitas.controleempregados.domain.Departamento;
import br.com.dicommunitas.controleempregados.repository.DepartamentoRepository;
import br.com.dicommunitas.controleempregados.repository.search.DepartamentoSearchRepository;
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
 * Service Implementation for managing {@link Departamento}.
 */
@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {

    private final Logger log = LoggerFactory.getLogger(DepartamentoServiceImpl.class);

    private final DepartamentoRepository departamentoRepository;

    private final DepartamentoSearchRepository departamentoSearchRepository;

    public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository, DepartamentoSearchRepository departamentoSearchRepository) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoSearchRepository = departamentoSearchRepository;
    }

    /**
     * Save a departamento.
     *
     * @param departamento the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Departamento save(Departamento departamento) {
        log.debug("Request to save Departamento : {}", departamento);
        Departamento result = departamentoRepository.save(departamento);
        departamentoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the departamentos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Departamento> findAll() {
        log.debug("Request to get all Departamentos");
        return departamentoRepository.findAll();
    }


    /**
     * Get one departamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Departamento> findOne(Long id) {
        log.debug("Request to get Departamento : {}", id);
        return departamentoRepository.findById(id);
    }

    /**
     * Delete the departamento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Departamento : {}", id);
        departamentoRepository.deleteById(id);
        departamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the departamento corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Departamento> search(String query) {
        log.debug("Request to search Departamentos for query {}", query);
        return StreamSupport
            .stream(departamentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
