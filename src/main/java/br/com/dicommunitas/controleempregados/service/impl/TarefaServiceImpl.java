package br.com.dicommunitas.controleempregados.service.impl;

import br.com.dicommunitas.controleempregados.service.TarefaService;
import br.com.dicommunitas.controleempregados.domain.Tarefa;
import br.com.dicommunitas.controleempregados.repository.TarefaRepository;
import br.com.dicommunitas.controleempregados.repository.search.TarefaSearchRepository;
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
 * Service Implementation for managing {@link Tarefa}.
 */
@Service
@Transactional
public class TarefaServiceImpl implements TarefaService {

    private final Logger log = LoggerFactory.getLogger(TarefaServiceImpl.class);

    private final TarefaRepository tarefaRepository;

    private final TarefaSearchRepository tarefaSearchRepository;

    public TarefaServiceImpl(TarefaRepository tarefaRepository, TarefaSearchRepository tarefaSearchRepository) {
        this.tarefaRepository = tarefaRepository;
        this.tarefaSearchRepository = tarefaSearchRepository;
    }

    /**
     * Save a tarefa.
     *
     * @param tarefa the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Tarefa save(Tarefa tarefa) {
        log.debug("Request to save Tarefa : {}", tarefa);
        Tarefa result = tarefaRepository.save(tarefa);
        tarefaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tarefas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tarefa> findAll() {
        log.debug("Request to get all Tarefas");
        return tarefaRepository.findAll();
    }


    /**
     * Get one tarefa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Tarefa> findOne(Long id) {
        log.debug("Request to get Tarefa : {}", id);
        return tarefaRepository.findById(id);
    }

    /**
     * Delete the tarefa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarefa : {}", id);
        tarefaRepository.deleteById(id);
        tarefaSearchRepository.deleteById(id);
    }

    /**
     * Search for the tarefa corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tarefa> search(String query) {
        log.debug("Request to search Tarefas for query {}", query);
        return StreamSupport
            .stream(tarefaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
