package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.domain.Regiao;
import br.com.dicommunitas.controleempregados.service.RegiaoService;
import br.com.dicommunitas.controleempregados.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.dicommunitas.controleempregados.domain.Regiao}.
 */
@RestController
@RequestMapping("/api")
public class RegiaoResource {

    private final Logger log = LoggerFactory.getLogger(RegiaoResource.class);

    private static final String ENTITY_NAME = "regiao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegiaoService regiaoService;

    public RegiaoResource(RegiaoService regiaoService) {
        this.regiaoService = regiaoService;
    }

    /**
     * {@code POST  /regiaos} : Create a new regiao.
     *
     * @param regiao the regiao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regiao, or with status {@code 400 (Bad Request)} if the regiao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regiaos")
    public ResponseEntity<Regiao> createRegiao(@RequestBody Regiao regiao) throws URISyntaxException {
        log.debug("REST request to save Regiao : {}", regiao);
        if (regiao.getId() != null) {
            throw new BadRequestAlertException("A new regiao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regiao result = regiaoService.save(regiao);
        return ResponseEntity.created(new URI("/api/regiaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regiaos} : Updates an existing regiao.
     *
     * @param regiao the regiao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regiao,
     * or with status {@code 400 (Bad Request)} if the regiao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regiao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regiaos")
    public ResponseEntity<Regiao> updateRegiao(@RequestBody Regiao regiao) throws URISyntaxException {
        log.debug("REST request to update Regiao : {}", regiao);
        if (regiao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Regiao result = regiaoService.save(regiao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regiao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regiaos} : get all the regiaos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regiaos in body.
     */
    @GetMapping("/regiaos")
    public List<Regiao> getAllRegiaos() {
        log.debug("REST request to get all Regiaos");
        return regiaoService.findAll();
    }

    /**
     * {@code GET  /regiaos/:id} : get the "id" regiao.
     *
     * @param id the id of the regiao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regiao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regiaos/{id}")
    public ResponseEntity<Regiao> getRegiao(@PathVariable Long id) {
        log.debug("REST request to get Regiao : {}", id);
        Optional<Regiao> regiao = regiaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regiao);
    }

    /**
     * {@code DELETE  /regiaos/:id} : delete the "id" regiao.
     *
     * @param id the id of the regiao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regiaos/{id}")
    public ResponseEntity<Void> deleteRegiao(@PathVariable Long id) {
        log.debug("REST request to delete Regiao : {}", id);
        regiaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/regiaos?query=:query} : search for the regiao corresponding
     * to the query.
     *
     * @param query the query of the regiao search.
     * @return the result of the search.
     */
    @GetMapping("/_search/regiaos")
    public List<Regiao> searchRegiaos(@RequestParam String query) {
        log.debug("REST request to search Regiaos for query {}", query);
        return regiaoService.search(query);
    }
}
