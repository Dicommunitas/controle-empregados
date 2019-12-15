package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.ControleEmpregadosApp;
import br.com.dicommunitas.controleempregados.domain.Tarefa;
import br.com.dicommunitas.controleempregados.repository.TarefaRepository;
import br.com.dicommunitas.controleempregados.repository.search.TarefaSearchRepository;
import br.com.dicommunitas.controleempregados.service.TarefaService;
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
 * Integration tests for the {@link TarefaResource} REST controller.
 */
@SpringBootTest(classes = ControleEmpregadosApp.class)
public class TarefaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaService tarefaService;

    /**
     * This repository is mocked in the br.com.dicommunitas.controleempregados.repository.search test package.
     *
     * @see br.com.dicommunitas.controleempregados.repository.search.TarefaSearchRepositoryMockConfiguration
     */
    @Autowired
    private TarefaSearchRepository mockTarefaSearchRepository;

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

    private MockMvc restTarefaMockMvc;

    private Tarefa tarefa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarefaResource tarefaResource = new TarefaResource(tarefaService);
        this.restTarefaMockMvc = MockMvcBuilders.standaloneSetup(tarefaResource)
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
    public static Tarefa createEntity(EntityManager em) {
        Tarefa tarefa = new Tarefa()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        return tarefa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarefa createUpdatedEntity(EntityManager em) {
        Tarefa tarefa = new Tarefa()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        return tarefa;
    }

    @BeforeEach
    public void initTest() {
        tarefa = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarefa() throws Exception {
        int databaseSizeBeforeCreate = tarefaRepository.findAll().size();

        // Create the Tarefa
        restTarefaMockMvc.perform(post("/api/tarefas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarefa)))
            .andExpect(status().isCreated());

        // Validate the Tarefa in the database
        List<Tarefa> tarefaList = tarefaRepository.findAll();
        assertThat(tarefaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarefa testTarefa = tarefaList.get(tarefaList.size() - 1);
        assertThat(testTarefa.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testTarefa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Tarefa in Elasticsearch
        verify(mockTarefaSearchRepository, times(1)).save(testTarefa);
    }

    @Test
    @Transactional
    public void createTarefaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarefaRepository.findAll().size();

        // Create the Tarefa with an existing ID
        tarefa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaMockMvc.perform(post("/api/tarefas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarefa)))
            .andExpect(status().isBadRequest());

        // Validate the Tarefa in the database
        List<Tarefa> tarefaList = tarefaRepository.findAll();
        assertThat(tarefaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Tarefa in Elasticsearch
        verify(mockTarefaSearchRepository, times(0)).save(tarefa);
    }


    @Test
    @Transactional
    public void getAllTarefas() throws Exception {
        // Initialize the database
        tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList
        restTarefaMockMvc.perform(get("/api/tarefas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefa.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTarefa() throws Exception {
        // Initialize the database
        tarefaRepository.saveAndFlush(tarefa);

        // Get the tarefa
        restTarefaMockMvc.perform(get("/api/tarefas/{id}", tarefa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarefa.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingTarefa() throws Exception {
        // Get the tarefa
        restTarefaMockMvc.perform(get("/api/tarefas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarefa() throws Exception {
        // Initialize the database
        tarefaService.save(tarefa);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTarefaSearchRepository);

        int databaseSizeBeforeUpdate = tarefaRepository.findAll().size();

        // Update the tarefa
        Tarefa updatedTarefa = tarefaRepository.findById(tarefa.getId()).get();
        // Disconnect from session so that the updates on updatedTarefa are not directly saved in db
        em.detach(updatedTarefa);
        updatedTarefa
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);

        restTarefaMockMvc.perform(put("/api/tarefas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarefa)))
            .andExpect(status().isOk());

        // Validate the Tarefa in the database
        List<Tarefa> tarefaList = tarefaRepository.findAll();
        assertThat(tarefaList).hasSize(databaseSizeBeforeUpdate);
        Tarefa testTarefa = tarefaList.get(tarefaList.size() - 1);
        assertThat(testTarefa.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testTarefa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Tarefa in Elasticsearch
        verify(mockTarefaSearchRepository, times(1)).save(testTarefa);
    }

    @Test
    @Transactional
    public void updateNonExistingTarefa() throws Exception {
        int databaseSizeBeforeUpdate = tarefaRepository.findAll().size();

        // Create the Tarefa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaMockMvc.perform(put("/api/tarefas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarefa)))
            .andExpect(status().isBadRequest());

        // Validate the Tarefa in the database
        List<Tarefa> tarefaList = tarefaRepository.findAll();
        assertThat(tarefaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Tarefa in Elasticsearch
        verify(mockTarefaSearchRepository, times(0)).save(tarefa);
    }

    @Test
    @Transactional
    public void deleteTarefa() throws Exception {
        // Initialize the database
        tarefaService.save(tarefa);

        int databaseSizeBeforeDelete = tarefaRepository.findAll().size();

        // Delete the tarefa
        restTarefaMockMvc.perform(delete("/api/tarefas/{id}", tarefa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tarefa> tarefaList = tarefaRepository.findAll();
        assertThat(tarefaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Tarefa in Elasticsearch
        verify(mockTarefaSearchRepository, times(1)).deleteById(tarefa.getId());
    }

    @Test
    @Transactional
    public void searchTarefa() throws Exception {
        // Initialize the database
        tarefaService.save(tarefa);
        when(mockTarefaSearchRepository.search(queryStringQuery("id:" + tarefa.getId())))
            .thenReturn(Collections.singletonList(tarefa));
        // Search the tarefa
        restTarefaMockMvc.perform(get("/api/_search/tarefas?query=id:" + tarefa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefa.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
}
