package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.ControleEmpregadosApp;
import br.com.dicommunitas.controleempregados.domain.Regiao;
import br.com.dicommunitas.controleempregados.repository.RegiaoRepository;
import br.com.dicommunitas.controleempregados.repository.search.RegiaoSearchRepository;
import br.com.dicommunitas.controleempregados.service.RegiaoService;
import br.com.dicommunitas.controleempregados.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static br.com.dicommunitas.controleempregados.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegiaoResource} REST controller.
 */
@SpringBootTest(classes = ControleEmpregadosApp.class)
public class RegiaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private RegiaoRepository regiaoRepository;

    @Autowired
    private RegiaoService regiaoService;

    /**
     * This repository is mocked in the br.com.dicommunitas.controleempregados.repository.search test package.
     *
     * @see br.com.dicommunitas.controleempregados.repository.search.RegiaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegiaoSearchRepository mockRegiaoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRegiaoMockMvc;

    private Regiao regiao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegiaoResource regiaoResource = new RegiaoResource(regiaoService);
        this.restRegiaoMockMvc = MockMvcBuilders.standaloneSetup(regiaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regiao createEntity(EntityManager em) {
        Regiao regiao = new Regiao()
            .nome(DEFAULT_NOME);
        return regiao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regiao createUpdatedEntity(EntityManager em) {
        Regiao regiao = new Regiao()
            .nome(UPDATED_NOME);
        return regiao;
    }

    @BeforeEach
    public void initTest() {
        regiao = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegiao() throws Exception {
        int databaseSizeBeforeCreate = regiaoRepository.findAll().size();

        // Create the Regiao
        restRegiaoMockMvc.perform(post("/api/regiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regiao)))
            .andExpect(status().isCreated());

        // Validate the Regiao in the database
        List<Regiao> regiaoList = regiaoRepository.findAll();
        assertThat(regiaoList).hasSize(databaseSizeBeforeCreate + 1);
        Regiao testRegiao = regiaoList.get(regiaoList.size() - 1);
        assertThat(testRegiao.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Regiao in Elasticsearch
        verify(mockRegiaoSearchRepository, times(1)).save(testRegiao);
    }

    @Test
    @Transactional
    public void createRegiaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regiaoRepository.findAll().size();

        // Create the Regiao with an existing ID
        regiao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegiaoMockMvc.perform(post("/api/regiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regiao)))
            .andExpect(status().isBadRequest());

        // Validate the Regiao in the database
        List<Regiao> regiaoList = regiaoRepository.findAll();
        assertThat(regiaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Regiao in Elasticsearch
        verify(mockRegiaoSearchRepository, times(0)).save(regiao);
    }


    @Test
    @Transactional
    public void getAllRegiaos() throws Exception {
        // Initialize the database
        regiaoRepository.saveAndFlush(regiao);

        // Get all the regiaoList
        restRegiaoMockMvc.perform(get("/api/regiaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regiao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getRegiao() throws Exception {
        // Initialize the database
        regiaoRepository.saveAndFlush(regiao);

        // Get the regiao
        restRegiaoMockMvc.perform(get("/api/regiaos/{id}", regiao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regiao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingRegiao() throws Exception {
        // Get the regiao
        restRegiaoMockMvc.perform(get("/api/regiaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegiao() throws Exception {
        // Initialize the database
        regiaoService.save(regiao);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRegiaoSearchRepository);

        int databaseSizeBeforeUpdate = regiaoRepository.findAll().size();

        // Update the regiao
        Regiao updatedRegiao = regiaoRepository.findById(regiao.getId()).get();
        // Disconnect from session so that the updates on updatedRegiao are not directly saved in db
        em.detach(updatedRegiao);
        updatedRegiao
            .nome(UPDATED_NOME);

        restRegiaoMockMvc.perform(put("/api/regiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegiao)))
            .andExpect(status().isOk());

        // Validate the Regiao in the database
        List<Regiao> regiaoList = regiaoRepository.findAll();
        assertThat(regiaoList).hasSize(databaseSizeBeforeUpdate);
        Regiao testRegiao = regiaoList.get(regiaoList.size() - 1);
        assertThat(testRegiao.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Regiao in Elasticsearch
        verify(mockRegiaoSearchRepository, times(1)).save(testRegiao);
    }

    @Test
    @Transactional
    public void updateNonExistingRegiao() throws Exception {
        int databaseSizeBeforeUpdate = regiaoRepository.findAll().size();

        // Create the Regiao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegiaoMockMvc.perform(put("/api/regiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regiao)))
            .andExpect(status().isBadRequest());

        // Validate the Regiao in the database
        List<Regiao> regiaoList = regiaoRepository.findAll();
        assertThat(regiaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Regiao in Elasticsearch
        verify(mockRegiaoSearchRepository, times(0)).save(regiao);
    }

    @Test
    @Transactional
    public void deleteRegiao() throws Exception {
        // Initialize the database
        regiaoService.save(regiao);

        int databaseSizeBeforeDelete = regiaoRepository.findAll().size();

        // Delete the regiao
        restRegiaoMockMvc.perform(delete("/api/regiaos/{id}", regiao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Regiao> regiaoList = regiaoRepository.findAll();
        assertThat(regiaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Regiao in Elasticsearch
        verify(mockRegiaoSearchRepository, times(1)).deleteById(regiao.getId());
    }

    @Test
    @Transactional
    public void searchRegiao() throws Exception {
        // Initialize the database
        regiaoService.save(regiao);
        when(mockRegiaoSearchRepository.search(queryStringQuery("id:" + regiao.getId())))
            .thenReturn(Collections.singletonList(regiao));
        // Search the regiao
        restRegiaoMockMvc.perform(get("/api/_search/regiaos?query=id:" + regiao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regiao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
}
