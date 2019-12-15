package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.ControleEmpregadosApp;
import br.com.dicommunitas.controleempregados.domain.Trabalho;
import br.com.dicommunitas.controleempregados.repository.TrabalhoRepository;
import br.com.dicommunitas.controleempregados.repository.search.TrabalhoSearchRepository;
import br.com.dicommunitas.controleempregados.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.ArrayList;
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
 * Integration tests for the {@link TrabalhoResource} REST controller.
 */
@SpringBootTest(classes = ControleEmpregadosApp.class)
public class TrabalhoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Long DEFAULT_SALARIO_MINIMO = 1L;
    private static final Long UPDATED_SALARIO_MINIMO = 2L;

    private static final Long DEFAULT_SALARIO_MAXIMO = 1L;
    private static final Long UPDATED_SALARIO_MAXIMO = 2L;

    @Autowired
    private TrabalhoRepository trabalhoRepository;

    @Mock
    private TrabalhoRepository trabalhoRepositoryMock;

    /**
     * This repository is mocked in the br.com.dicommunitas.controleempregados.repository.search test package.
     *
     * @see br.com.dicommunitas.controleempregados.repository.search.TrabalhoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrabalhoSearchRepository mockTrabalhoSearchRepository;

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

    private MockMvc restTrabalhoMockMvc;

    private Trabalho trabalho;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrabalhoResource trabalhoResource = new TrabalhoResource(trabalhoRepository, mockTrabalhoSearchRepository);
        this.restTrabalhoMockMvc = MockMvcBuilders.standaloneSetup(trabalhoResource)
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
    public static Trabalho createEntity(EntityManager em) {
        Trabalho trabalho = new Trabalho()
            .titulo(DEFAULT_TITULO)
            .salarioMinimo(DEFAULT_SALARIO_MINIMO)
            .salarioMaximo(DEFAULT_SALARIO_MAXIMO);
        return trabalho;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trabalho createUpdatedEntity(EntityManager em) {
        Trabalho trabalho = new Trabalho()
            .titulo(UPDATED_TITULO)
            .salarioMinimo(UPDATED_SALARIO_MINIMO)
            .salarioMaximo(UPDATED_SALARIO_MAXIMO);
        return trabalho;
    }

    @BeforeEach
    public void initTest() {
        trabalho = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrabalho() throws Exception {
        int databaseSizeBeforeCreate = trabalhoRepository.findAll().size();

        // Create the Trabalho
        restTrabalhoMockMvc.perform(post("/api/trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabalho)))
            .andExpect(status().isCreated());

        // Validate the Trabalho in the database
        List<Trabalho> trabalhoList = trabalhoRepository.findAll();
        assertThat(trabalhoList).hasSize(databaseSizeBeforeCreate + 1);
        Trabalho testTrabalho = trabalhoList.get(trabalhoList.size() - 1);
        assertThat(testTrabalho.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testTrabalho.getSalarioMinimo()).isEqualTo(DEFAULT_SALARIO_MINIMO);
        assertThat(testTrabalho.getSalarioMaximo()).isEqualTo(DEFAULT_SALARIO_MAXIMO);

        // Validate the Trabalho in Elasticsearch
        verify(mockTrabalhoSearchRepository, times(1)).save(testTrabalho);
    }

    @Test
    @Transactional
    public void createTrabalhoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trabalhoRepository.findAll().size();

        // Create the Trabalho with an existing ID
        trabalho.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrabalhoMockMvc.perform(post("/api/trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabalho)))
            .andExpect(status().isBadRequest());

        // Validate the Trabalho in the database
        List<Trabalho> trabalhoList = trabalhoRepository.findAll();
        assertThat(trabalhoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Trabalho in Elasticsearch
        verify(mockTrabalhoSearchRepository, times(0)).save(trabalho);
    }


    @Test
    @Transactional
    public void getAllTrabalhos() throws Exception {
        // Initialize the database
        trabalhoRepository.saveAndFlush(trabalho);

        // Get all the trabalhoList
        restTrabalhoMockMvc.perform(get("/api/trabalhos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].salarioMinimo").value(hasItem(DEFAULT_SALARIO_MINIMO.intValue())))
            .andExpect(jsonPath("$.[*].salarioMaximo").value(hasItem(DEFAULT_SALARIO_MAXIMO.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTrabalhosWithEagerRelationshipsIsEnabled() throws Exception {
        TrabalhoResource trabalhoResource = new TrabalhoResource(trabalhoRepositoryMock, mockTrabalhoSearchRepository);
        when(trabalhoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTrabalhoMockMvc = MockMvcBuilders.standaloneSetup(trabalhoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTrabalhoMockMvc.perform(get("/api/trabalhos?eagerload=true"))
        .andExpect(status().isOk());

        verify(trabalhoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTrabalhosWithEagerRelationshipsIsNotEnabled() throws Exception {
        TrabalhoResource trabalhoResource = new TrabalhoResource(trabalhoRepositoryMock, mockTrabalhoSearchRepository);
            when(trabalhoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTrabalhoMockMvc = MockMvcBuilders.standaloneSetup(trabalhoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTrabalhoMockMvc.perform(get("/api/trabalhos?eagerload=true"))
        .andExpect(status().isOk());

            verify(trabalhoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTrabalho() throws Exception {
        // Initialize the database
        trabalhoRepository.saveAndFlush(trabalho);

        // Get the trabalho
        restTrabalhoMockMvc.perform(get("/api/trabalhos/{id}", trabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trabalho.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.salarioMinimo").value(DEFAULT_SALARIO_MINIMO.intValue()))
            .andExpect(jsonPath("$.salarioMaximo").value(DEFAULT_SALARIO_MAXIMO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrabalho() throws Exception {
        // Get the trabalho
        restTrabalhoMockMvc.perform(get("/api/trabalhos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrabalho() throws Exception {
        // Initialize the database
        trabalhoRepository.saveAndFlush(trabalho);

        int databaseSizeBeforeUpdate = trabalhoRepository.findAll().size();

        // Update the trabalho
        Trabalho updatedTrabalho = trabalhoRepository.findById(trabalho.getId()).get();
        // Disconnect from session so that the updates on updatedTrabalho are not directly saved in db
        em.detach(updatedTrabalho);
        updatedTrabalho
            .titulo(UPDATED_TITULO)
            .salarioMinimo(UPDATED_SALARIO_MINIMO)
            .salarioMaximo(UPDATED_SALARIO_MAXIMO);

        restTrabalhoMockMvc.perform(put("/api/trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrabalho)))
            .andExpect(status().isOk());

        // Validate the Trabalho in the database
        List<Trabalho> trabalhoList = trabalhoRepository.findAll();
        assertThat(trabalhoList).hasSize(databaseSizeBeforeUpdate);
        Trabalho testTrabalho = trabalhoList.get(trabalhoList.size() - 1);
        assertThat(testTrabalho.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testTrabalho.getSalarioMinimo()).isEqualTo(UPDATED_SALARIO_MINIMO);
        assertThat(testTrabalho.getSalarioMaximo()).isEqualTo(UPDATED_SALARIO_MAXIMO);

        // Validate the Trabalho in Elasticsearch
        verify(mockTrabalhoSearchRepository, times(1)).save(testTrabalho);
    }

    @Test
    @Transactional
    public void updateNonExistingTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = trabalhoRepository.findAll().size();

        // Create the Trabalho

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrabalhoMockMvc.perform(put("/api/trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabalho)))
            .andExpect(status().isBadRequest());

        // Validate the Trabalho in the database
        List<Trabalho> trabalhoList = trabalhoRepository.findAll();
        assertThat(trabalhoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Trabalho in Elasticsearch
        verify(mockTrabalhoSearchRepository, times(0)).save(trabalho);
    }

    @Test
    @Transactional
    public void deleteTrabalho() throws Exception {
        // Initialize the database
        trabalhoRepository.saveAndFlush(trabalho);

        int databaseSizeBeforeDelete = trabalhoRepository.findAll().size();

        // Delete the trabalho
        restTrabalhoMockMvc.perform(delete("/api/trabalhos/{id}", trabalho.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trabalho> trabalhoList = trabalhoRepository.findAll();
        assertThat(trabalhoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Trabalho in Elasticsearch
        verify(mockTrabalhoSearchRepository, times(1)).deleteById(trabalho.getId());
    }

    @Test
    @Transactional
    public void searchTrabalho() throws Exception {
        // Initialize the database
        trabalhoRepository.saveAndFlush(trabalho);
        when(mockTrabalhoSearchRepository.search(queryStringQuery("id:" + trabalho.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(trabalho), PageRequest.of(0, 1), 1));
        // Search the trabalho
        restTrabalhoMockMvc.perform(get("/api/_search/trabalhos?query=id:" + trabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].salarioMinimo").value(hasItem(DEFAULT_SALARIO_MINIMO.intValue())))
            .andExpect(jsonPath("$.[*].salarioMaximo").value(hasItem(DEFAULT_SALARIO_MAXIMO.intValue())));
    }
}
