package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.ControleEmpregadosApp;
import br.com.dicommunitas.controleempregados.domain.HistoricoDeTrabalho;
import br.com.dicommunitas.controleempregados.repository.HistoricoDeTrabalhoRepository;
import br.com.dicommunitas.controleempregados.repository.search.HistoricoDeTrabalhoSearchRepository;
import br.com.dicommunitas.controleempregados.service.HistoricoDeTrabalhoService;
import br.com.dicommunitas.controleempregados.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static br.com.dicommunitas.controleempregados.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.dicommunitas.controleempregados.domain.enumeration.Lingua;
/**
 * Integration tests for the {@link HistoricoDeTrabalhoResource} REST controller.
 */
@SpringBootTest(classes = ControleEmpregadosApp.class)
public class HistoricoDeTrabalhoResourceIT {

    private static final Instant DEFAULT_DATA_INICIAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Lingua DEFAULT_LINGUA = Lingua.PORTUGUESE;
    private static final Lingua UPDATED_LINGUA = Lingua.ENGLISH;

    @Autowired
    private HistoricoDeTrabalhoRepository historicoDeTrabalhoRepository;

    @Autowired
    private HistoricoDeTrabalhoService historicoDeTrabalhoService;

    /**
     * This repository is mocked in the br.com.dicommunitas.controleempregados.repository.search test package.
     *
     * @see br.com.dicommunitas.controleempregados.repository.search.HistoricoDeTrabalhoSearchRepositoryMockConfiguration
     */
    @Autowired
    private HistoricoDeTrabalhoSearchRepository mockHistoricoDeTrabalhoSearchRepository;

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

    private MockMvc restHistoricoDeTrabalhoMockMvc;

    private HistoricoDeTrabalho historicoDeTrabalho;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoricoDeTrabalhoResource historicoDeTrabalhoResource = new HistoricoDeTrabalhoResource(historicoDeTrabalhoService);
        this.restHistoricoDeTrabalhoMockMvc = MockMvcBuilders.standaloneSetup(historicoDeTrabalhoResource)
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
    public static HistoricoDeTrabalho createEntity(EntityManager em) {
        HistoricoDeTrabalho historicoDeTrabalho = new HistoricoDeTrabalho()
            .dataInicial(DEFAULT_DATA_INICIAL)
            .dataFinal(DEFAULT_DATA_FINAL)
            .lingua(DEFAULT_LINGUA);
        return historicoDeTrabalho;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoricoDeTrabalho createUpdatedEntity(EntityManager em) {
        HistoricoDeTrabalho historicoDeTrabalho = new HistoricoDeTrabalho()
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .lingua(UPDATED_LINGUA);
        return historicoDeTrabalho;
    }

    @BeforeEach
    public void initTest() {
        historicoDeTrabalho = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoricoDeTrabalho() throws Exception {
        int databaseSizeBeforeCreate = historicoDeTrabalhoRepository.findAll().size();

        // Create the HistoricoDeTrabalho
        restHistoricoDeTrabalhoMockMvc.perform(post("/api/historico-de-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicoDeTrabalho)))
            .andExpect(status().isCreated());

        // Validate the HistoricoDeTrabalho in the database
        List<HistoricoDeTrabalho> historicoDeTrabalhoList = historicoDeTrabalhoRepository.findAll();
        assertThat(historicoDeTrabalhoList).hasSize(databaseSizeBeforeCreate + 1);
        HistoricoDeTrabalho testHistoricoDeTrabalho = historicoDeTrabalhoList.get(historicoDeTrabalhoList.size() - 1);
        assertThat(testHistoricoDeTrabalho.getDataInicial()).isEqualTo(DEFAULT_DATA_INICIAL);
        assertThat(testHistoricoDeTrabalho.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
        assertThat(testHistoricoDeTrabalho.getLingua()).isEqualTo(DEFAULT_LINGUA);

        // Validate the HistoricoDeTrabalho in Elasticsearch
        verify(mockHistoricoDeTrabalhoSearchRepository, times(1)).save(testHistoricoDeTrabalho);
    }

    @Test
    @Transactional
    public void createHistoricoDeTrabalhoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historicoDeTrabalhoRepository.findAll().size();

        // Create the HistoricoDeTrabalho with an existing ID
        historicoDeTrabalho.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoricoDeTrabalhoMockMvc.perform(post("/api/historico-de-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicoDeTrabalho)))
            .andExpect(status().isBadRequest());

        // Validate the HistoricoDeTrabalho in the database
        List<HistoricoDeTrabalho> historicoDeTrabalhoList = historicoDeTrabalhoRepository.findAll();
        assertThat(historicoDeTrabalhoList).hasSize(databaseSizeBeforeCreate);

        // Validate the HistoricoDeTrabalho in Elasticsearch
        verify(mockHistoricoDeTrabalhoSearchRepository, times(0)).save(historicoDeTrabalho);
    }


    @Test
    @Transactional
    public void getAllHistoricoDeTrabalhos() throws Exception {
        // Initialize the database
        historicoDeTrabalhoRepository.saveAndFlush(historicoDeTrabalho);

        // Get all the historicoDeTrabalhoList
        restHistoricoDeTrabalhoMockMvc.perform(get("/api/historico-de-trabalhos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicoDeTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(DEFAULT_DATA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(DEFAULT_DATA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].lingua").value(hasItem(DEFAULT_LINGUA.toString())));
    }
    
    @Test
    @Transactional
    public void getHistoricoDeTrabalho() throws Exception {
        // Initialize the database
        historicoDeTrabalhoRepository.saveAndFlush(historicoDeTrabalho);

        // Get the historicoDeTrabalho
        restHistoricoDeTrabalhoMockMvc.perform(get("/api/historico-de-trabalhos/{id}", historicoDeTrabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historicoDeTrabalho.getId().intValue()))
            .andExpect(jsonPath("$.dataInicial").value(DEFAULT_DATA_INICIAL.toString()))
            .andExpect(jsonPath("$.dataFinal").value(DEFAULT_DATA_FINAL.toString()))
            .andExpect(jsonPath("$.lingua").value(DEFAULT_LINGUA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoricoDeTrabalho() throws Exception {
        // Get the historicoDeTrabalho
        restHistoricoDeTrabalhoMockMvc.perform(get("/api/historico-de-trabalhos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoricoDeTrabalho() throws Exception {
        // Initialize the database
        historicoDeTrabalhoService.save(historicoDeTrabalho);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockHistoricoDeTrabalhoSearchRepository);

        int databaseSizeBeforeUpdate = historicoDeTrabalhoRepository.findAll().size();

        // Update the historicoDeTrabalho
        HistoricoDeTrabalho updatedHistoricoDeTrabalho = historicoDeTrabalhoRepository.findById(historicoDeTrabalho.getId()).get();
        // Disconnect from session so that the updates on updatedHistoricoDeTrabalho are not directly saved in db
        em.detach(updatedHistoricoDeTrabalho);
        updatedHistoricoDeTrabalho
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .lingua(UPDATED_LINGUA);

        restHistoricoDeTrabalhoMockMvc.perform(put("/api/historico-de-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistoricoDeTrabalho)))
            .andExpect(status().isOk());

        // Validate the HistoricoDeTrabalho in the database
        List<HistoricoDeTrabalho> historicoDeTrabalhoList = historicoDeTrabalhoRepository.findAll();
        assertThat(historicoDeTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        HistoricoDeTrabalho testHistoricoDeTrabalho = historicoDeTrabalhoList.get(historicoDeTrabalhoList.size() - 1);
        assertThat(testHistoricoDeTrabalho.getDataInicial()).isEqualTo(UPDATED_DATA_INICIAL);
        assertThat(testHistoricoDeTrabalho.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
        assertThat(testHistoricoDeTrabalho.getLingua()).isEqualTo(UPDATED_LINGUA);

        // Validate the HistoricoDeTrabalho in Elasticsearch
        verify(mockHistoricoDeTrabalhoSearchRepository, times(1)).save(testHistoricoDeTrabalho);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoricoDeTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = historicoDeTrabalhoRepository.findAll().size();

        // Create the HistoricoDeTrabalho

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricoDeTrabalhoMockMvc.perform(put("/api/historico-de-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicoDeTrabalho)))
            .andExpect(status().isBadRequest());

        // Validate the HistoricoDeTrabalho in the database
        List<HistoricoDeTrabalho> historicoDeTrabalhoList = historicoDeTrabalhoRepository.findAll();
        assertThat(historicoDeTrabalhoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HistoricoDeTrabalho in Elasticsearch
        verify(mockHistoricoDeTrabalhoSearchRepository, times(0)).save(historicoDeTrabalho);
    }

    @Test
    @Transactional
    public void deleteHistoricoDeTrabalho() throws Exception {
        // Initialize the database
        historicoDeTrabalhoService.save(historicoDeTrabalho);

        int databaseSizeBeforeDelete = historicoDeTrabalhoRepository.findAll().size();

        // Delete the historicoDeTrabalho
        restHistoricoDeTrabalhoMockMvc.perform(delete("/api/historico-de-trabalhos/{id}", historicoDeTrabalho.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoricoDeTrabalho> historicoDeTrabalhoList = historicoDeTrabalhoRepository.findAll();
        assertThat(historicoDeTrabalhoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HistoricoDeTrabalho in Elasticsearch
        verify(mockHistoricoDeTrabalhoSearchRepository, times(1)).deleteById(historicoDeTrabalho.getId());
    }

    @Test
    @Transactional
    public void searchHistoricoDeTrabalho() throws Exception {
        // Initialize the database
        historicoDeTrabalhoService.save(historicoDeTrabalho);
        when(mockHistoricoDeTrabalhoSearchRepository.search(queryStringQuery("id:" + historicoDeTrabalho.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(historicoDeTrabalho), PageRequest.of(0, 1), 1));
        // Search the historicoDeTrabalho
        restHistoricoDeTrabalhoMockMvc.perform(get("/api/_search/historico-de-trabalhos?query=id:" + historicoDeTrabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicoDeTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(DEFAULT_DATA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(DEFAULT_DATA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].lingua").value(hasItem(DEFAULT_LINGUA.toString())));
    }
}
