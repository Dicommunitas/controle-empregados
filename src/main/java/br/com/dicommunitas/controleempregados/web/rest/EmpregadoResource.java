package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.domain.Empregado;
import br.com.dicommunitas.controleempregados.repository.EmpregadoRepository;
import br.com.dicommunitas.controleempregados.repository.search.EmpregadoSearchRepository;
import br.com.dicommunitas.controleempregados.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.dicommunitas.controleempregados.domain.Empregado}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmpregadoResource {

    private final Logger log = LoggerFactory.getLogger(EmpregadoResource.class);

    private static final String ENTITY_NAME = "empregado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpregadoRepository empregadoRepository;

    private final EmpregadoSearchRepository empregadoSearchRepository;

    public EmpregadoResource(EmpregadoRepository empregadoRepository, EmpregadoSearchRepository empregadoSearchRepository) {
        this.empregadoRepository = empregadoRepository;
        this.empregadoSearchRepository = empregadoSearchRepository;
    }

    /**
     * {@code POST  /empregados} : Create a new empregado.
     *
     * @param empregado the empregado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empregado, or with status {@code 400 (Bad Request)} if the empregado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empregados")
    public ResponseEntity<Empregado> createEmpregado(@RequestBody Empregado empregado) throws URISyntaxException {
        log.debug("REST request to save Empregado : {}", empregado);
        if (empregado.getId() != null) {
            throw new BadRequestAlertException("A new empregado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Empregado result = empregadoRepository.save(empregado);
        empregadoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/empregados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empregados} : Updates an existing empregado.
     *
     * @param empregado the empregado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empregado,
     * or with status {@code 400 (Bad Request)} if the empregado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empregado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empregados")
    public ResponseEntity<Empregado> updateEmpregado(@RequestBody Empregado empregado) throws URISyntaxException {
        log.debug("REST request to update Empregado : {}", empregado);
        if (empregado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Empregado result = empregadoRepository.save(empregado);
        empregadoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empregado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empregados} : get all the empregados.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empregados in body.
     */
    @GetMapping("/empregados")
    public ResponseEntity<List<Empregado>> getAllEmpregados(Pageable pageable) {
        log.debug("REST request to get a page of Empregados");
        Page<Empregado> page = empregadoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /empregados/:id} : get the "id" empregado.
     *
     * @param id the id of the empregado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empregado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empregados/{id}")
    public ResponseEntity<Empregado> getEmpregado(@PathVariable Long id) {
        log.debug("REST request to get Empregado : {}", id);
        Optional<Empregado> empregado = empregadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(empregado);
    }

    /**
     * {@code DELETE  /empregados/:id} : delete the "id" empregado.
     *
     * @param id the id of the empregado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empregados/{id}")
    public ResponseEntity<Void> deleteEmpregado(@PathVariable Long id) {
        log.debug("REST request to delete Empregado : {}", id);
        empregadoRepository.deleteById(id);
        empregadoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/empregados?query=:query} : search for the empregado corresponding
     * to the query.
     *
     * @param query the query of the empregado search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/empregados")
    public ResponseEntity<List<Empregado>> searchEmpregados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Empregados for query {}", query);
        Page<Empregado> page = empregadoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
