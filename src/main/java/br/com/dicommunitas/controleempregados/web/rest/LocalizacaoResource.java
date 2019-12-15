package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.domain.Localizacao;
import br.com.dicommunitas.controleempregados.service.LocalizacaoService;
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
 * REST controller for managing {@link br.com.dicommunitas.controleempregados.domain.Localizacao}.
 */
@RestController
@RequestMapping("/api")
public class LocalizacaoResource {

    private final Logger log = LoggerFactory.getLogger(LocalizacaoResource.class);

    private static final String ENTITY_NAME = "localizacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalizacaoService localizacaoService;

    public LocalizacaoResource(LocalizacaoService localizacaoService) {
        this.localizacaoService = localizacaoService;
    }

    /**
     * {@code POST  /localizacaos} : Create a new localizacao.
     *
     * @param localizacao the localizacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localizacao, or with status {@code 400 (Bad Request)} if the localizacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localizacaos")
    public ResponseEntity<Localizacao> createLocalizacao(@RequestBody Localizacao localizacao) throws URISyntaxException {
        log.debug("REST request to save Localizacao : {}", localizacao);
        if (localizacao.getId() != null) {
            throw new BadRequestAlertException("A new localizacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Localizacao result = localizacaoService.save(localizacao);
        return ResponseEntity.created(new URI("/api/localizacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localizacaos} : Updates an existing localizacao.
     *
     * @param localizacao the localizacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localizacao,
     * or with status {@code 400 (Bad Request)} if the localizacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localizacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localizacaos")
    public ResponseEntity<Localizacao> updateLocalizacao(@RequestBody Localizacao localizacao) throws URISyntaxException {
        log.debug("REST request to update Localizacao : {}", localizacao);
        if (localizacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Localizacao result = localizacaoService.save(localizacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localizacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localizacaos} : get all the localizacaos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localizacaos in body.
     */
    @GetMapping("/localizacaos")
    public List<Localizacao> getAllLocalizacaos() {
        log.debug("REST request to get all Localizacaos");
        return localizacaoService.findAll();
    }

    /**
     * {@code GET  /localizacaos/:id} : get the "id" localizacao.
     *
     * @param id the id of the localizacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localizacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localizacaos/{id}")
    public ResponseEntity<Localizacao> getLocalizacao(@PathVariable Long id) {
        log.debug("REST request to get Localizacao : {}", id);
        Optional<Localizacao> localizacao = localizacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localizacao);
    }

    /**
     * {@code DELETE  /localizacaos/:id} : delete the "id" localizacao.
     *
     * @param id the id of the localizacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localizacaos/{id}")
    public ResponseEntity<Void> deleteLocalizacao(@PathVariable Long id) {
        log.debug("REST request to delete Localizacao : {}", id);
        localizacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/localizacaos?query=:query} : search for the localizacao corresponding
     * to the query.
     *
     * @param query the query of the localizacao search.
     * @return the result of the search.
     */
    @GetMapping("/_search/localizacaos")
    public List<Localizacao> searchLocalizacaos(@RequestParam String query) {
        log.debug("REST request to search Localizacaos for query {}", query);
        return localizacaoService.search(query);
    }
}
