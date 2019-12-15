package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.domain.HistoricoDeTrabalho;
import br.com.dicommunitas.controleempregados.service.HistoricoDeTrabalhoService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.dicommunitas.controleempregados.domain.HistoricoDeTrabalho}.
 */
@RestController
@RequestMapping("/api")
public class HistoricoDeTrabalhoResource {

    private final Logger log = LoggerFactory.getLogger(HistoricoDeTrabalhoResource.class);

    private static final String ENTITY_NAME = "historicoDeTrabalho";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoricoDeTrabalhoService historicoDeTrabalhoService;

    public HistoricoDeTrabalhoResource(HistoricoDeTrabalhoService historicoDeTrabalhoService) {
        this.historicoDeTrabalhoService = historicoDeTrabalhoService;
    }

    /**
     * {@code POST  /historico-de-trabalhos} : Create a new historicoDeTrabalho.
     *
     * @param historicoDeTrabalho the historicoDeTrabalho to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historicoDeTrabalho, or with status {@code 400 (Bad Request)} if the historicoDeTrabalho has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historico-de-trabalhos")
    public ResponseEntity<HistoricoDeTrabalho> createHistoricoDeTrabalho(@RequestBody HistoricoDeTrabalho historicoDeTrabalho) throws URISyntaxException {
        log.debug("REST request to save HistoricoDeTrabalho : {}", historicoDeTrabalho);
        if (historicoDeTrabalho.getId() != null) {
            throw new BadRequestAlertException("A new historicoDeTrabalho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoricoDeTrabalho result = historicoDeTrabalhoService.save(historicoDeTrabalho);
        return ResponseEntity.created(new URI("/api/historico-de-trabalhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historico-de-trabalhos} : Updates an existing historicoDeTrabalho.
     *
     * @param historicoDeTrabalho the historicoDeTrabalho to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historicoDeTrabalho,
     * or with status {@code 400 (Bad Request)} if the historicoDeTrabalho is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historicoDeTrabalho couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historico-de-trabalhos")
    public ResponseEntity<HistoricoDeTrabalho> updateHistoricoDeTrabalho(@RequestBody HistoricoDeTrabalho historicoDeTrabalho) throws URISyntaxException {
        log.debug("REST request to update HistoricoDeTrabalho : {}", historicoDeTrabalho);
        if (historicoDeTrabalho.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistoricoDeTrabalho result = historicoDeTrabalhoService.save(historicoDeTrabalho);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historicoDeTrabalho.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /historico-de-trabalhos} : get all the historicoDeTrabalhos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historicoDeTrabalhos in body.
     */
    @GetMapping("/historico-de-trabalhos")
    public ResponseEntity<List<HistoricoDeTrabalho>> getAllHistoricoDeTrabalhos(Pageable pageable) {
        log.debug("REST request to get a page of HistoricoDeTrabalhos");
        Page<HistoricoDeTrabalho> page = historicoDeTrabalhoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /historico-de-trabalhos/:id} : get the "id" historicoDeTrabalho.
     *
     * @param id the id of the historicoDeTrabalho to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historicoDeTrabalho, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historico-de-trabalhos/{id}")
    public ResponseEntity<HistoricoDeTrabalho> getHistoricoDeTrabalho(@PathVariable Long id) {
        log.debug("REST request to get HistoricoDeTrabalho : {}", id);
        Optional<HistoricoDeTrabalho> historicoDeTrabalho = historicoDeTrabalhoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historicoDeTrabalho);
    }

    /**
     * {@code DELETE  /historico-de-trabalhos/:id} : delete the "id" historicoDeTrabalho.
     *
     * @param id the id of the historicoDeTrabalho to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historico-de-trabalhos/{id}")
    public ResponseEntity<Void> deleteHistoricoDeTrabalho(@PathVariable Long id) {
        log.debug("REST request to delete HistoricoDeTrabalho : {}", id);
        historicoDeTrabalhoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/historico-de-trabalhos?query=:query} : search for the historicoDeTrabalho corresponding
     * to the query.
     *
     * @param query the query of the historicoDeTrabalho search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/historico-de-trabalhos")
    public ResponseEntity<List<HistoricoDeTrabalho>> searchHistoricoDeTrabalhos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HistoricoDeTrabalhos for query {}", query);
        Page<HistoricoDeTrabalho> page = historicoDeTrabalhoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
