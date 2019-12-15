package br.com.dicommunitas.controleempregados.web.rest;

import br.com.dicommunitas.controleempregados.ControleEmpregadosApp;
import br.com.dicommunitas.controleempregados.domain.Empregado;
import br.com.dicommunitas.controleempregados.repository.EmpregadoRepository;
import br.com.dicommunitas.controleempregados.repository.search.EmpregadoSearchRepository;
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

/**
 * Integration tests for the {@link EmpregadoResource} REST controller.
 */
@SpringBootTest(classes = ControleEmpregadosApp.class)
public class EmpregadoResourceIT {

    private static final String DEFAULT_PRIMEIRO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_PRIMEIRO_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CONTRATACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONTRATACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_SALARIO = 1L;
    private static final Long UPDATED_SALARIO = 2L;

    private static final Long DEFAULT_COMISSAO = 1L;
    private static final Long UPDATED_COMISSAO = 2L;

    @Autowired
    private EmpregadoRepository empregadoRepository;

    /**
     * This repository is mocked in the br.com.dicommunitas.controleempregados.repository.search test package.
     *
     * @see br.com.dicommunitas.controleempregados.repository.search.EmpregadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmpregadoSearchRepository mockEmpregadoSearchRepository;

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

    private MockMvc restEmpregadoMockMvc;

    private Empregado empregado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpregadoResource empregadoResource = new EmpregadoResource(empregadoRepository, mockEmpregadoSearchRepository);
        this.restEmpregadoMockMvc = MockMvcBuilders.standaloneSetup(empregadoResource)
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
    public static Empregado createEntity(EntityManager em) {
        Empregado empregado = new Empregado()
            .primeiroNome(DEFAULT_PRIMEIRO_NOME)
            .sobrenome(DEFAULT_SOBRENOME)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .dataContratacao(DEFAULT_DATA_CONTRATACAO)
            .salario(DEFAULT_SALARIO)
            .comissao(DEFAULT_COMISSAO);
        return empregado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empregado createUpdatedEntity(EntityManager em) {
        Empregado empregado = new Empregado()
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .salario(UPDATED_SALARIO)
            .comissao(UPDATED_COMISSAO);
        return empregado;
    }

    @BeforeEach
    public void initTest() {
        empregado = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpregado() throws Exception {
        int databaseSizeBeforeCreate = empregadoRepository.findAll().size();

        // Create the Empregado
        restEmpregadoMockMvc.perform(post("/api/empregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empregado)))
            .andExpect(status().isCreated());

        // Validate the Empregado in the database
        List<Empregado> empregadoList = empregadoRepository.findAll();
        assertThat(empregadoList).hasSize(databaseSizeBeforeCreate + 1);
        Empregado testEmpregado = empregadoList.get(empregadoList.size() - 1);
        assertThat(testEmpregado.getPrimeiroNome()).isEqualTo(DEFAULT_PRIMEIRO_NOME);
        assertThat(testEmpregado.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testEmpregado.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmpregado.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEmpregado.getDataContratacao()).isEqualTo(DEFAULT_DATA_CONTRATACAO);
        assertThat(testEmpregado.getSalario()).isEqualTo(DEFAULT_SALARIO);
        assertThat(testEmpregado.getComissao()).isEqualTo(DEFAULT_COMISSAO);

        // Validate the Empregado in Elasticsearch
        verify(mockEmpregadoSearchRepository, times(1)).save(testEmpregado);
    }

    @Test
    @Transactional
    public void createEmpregadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empregadoRepository.findAll().size();

        // Create the Empregado with an existing ID
        empregado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpregadoMockMvc.perform(post("/api/empregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empregado)))
            .andExpect(status().isBadRequest());

        // Validate the Empregado in the database
        List<Empregado> empregadoList = empregadoRepository.findAll();
        assertThat(empregadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Empregado in Elasticsearch
        verify(mockEmpregadoSearchRepository, times(0)).save(empregado);
    }


    @Test
    @Transactional
    public void getAllEmpregados() throws Exception {
        // Initialize the database
        empregadoRepository.saveAndFlush(empregado);

        // Get all the empregadoList
        restEmpregadoMockMvc.perform(get("/api/empregados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empregado.getId().intValue())))
            .andExpect(jsonPath("$.[*].primeiroNome").value(hasItem(DEFAULT_PRIMEIRO_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.intValue())))
            .andExpect(jsonPath("$.[*].comissao").value(hasItem(DEFAULT_COMISSAO.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmpregado() throws Exception {
        // Initialize the database
        empregadoRepository.saveAndFlush(empregado);

        // Get the empregado
        restEmpregadoMockMvc.perform(get("/api/empregados/{id}", empregado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empregado.getId().intValue()))
            .andExpect(jsonPath("$.primeiroNome").value(DEFAULT_PRIMEIRO_NOME))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.dataContratacao").value(DEFAULT_DATA_CONTRATACAO.toString()))
            .andExpect(jsonPath("$.salario").value(DEFAULT_SALARIO.intValue()))
            .andExpect(jsonPath("$.comissao").value(DEFAULT_COMISSAO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpregado() throws Exception {
        // Get the empregado
        restEmpregadoMockMvc.perform(get("/api/empregados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpregado() throws Exception {
        // Initialize the database
        empregadoRepository.saveAndFlush(empregado);

        int databaseSizeBeforeUpdate = empregadoRepository.findAll().size();

        // Update the empregado
        Empregado updatedEmpregado = empregadoRepository.findById(empregado.getId()).get();
        // Disconnect from session so that the updates on updatedEmpregado are not directly saved in db
        em.detach(updatedEmpregado);
        updatedEmpregado
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .salario(UPDATED_SALARIO)
            .comissao(UPDATED_COMISSAO);

        restEmpregadoMockMvc.perform(put("/api/empregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpregado)))
            .andExpect(status().isOk());

        // Validate the Empregado in the database
        List<Empregado> empregadoList = empregadoRepository.findAll();
        assertThat(empregadoList).hasSize(databaseSizeBeforeUpdate);
        Empregado testEmpregado = empregadoList.get(empregadoList.size() - 1);
        assertThat(testEmpregado.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testEmpregado.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testEmpregado.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmpregado.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEmpregado.getDataContratacao()).isEqualTo(UPDATED_DATA_CONTRATACAO);
        assertThat(testEmpregado.getSalario()).isEqualTo(UPDATED_SALARIO);
        assertThat(testEmpregado.getComissao()).isEqualTo(UPDATED_COMISSAO);

        // Validate the Empregado in Elasticsearch
        verify(mockEmpregadoSearchRepository, times(1)).save(testEmpregado);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpregado() throws Exception {
        int databaseSizeBeforeUpdate = empregadoRepository.findAll().size();

        // Create the Empregado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpregadoMockMvc.perform(put("/api/empregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empregado)))
            .andExpect(status().isBadRequest());

        // Validate the Empregado in the database
        List<Empregado> empregadoList = empregadoRepository.findAll();
        assertThat(empregadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Empregado in Elasticsearch
        verify(mockEmpregadoSearchRepository, times(0)).save(empregado);
    }

    @Test
    @Transactional
    public void deleteEmpregado() throws Exception {
        // Initialize the database
        empregadoRepository.saveAndFlush(empregado);

        int databaseSizeBeforeDelete = empregadoRepository.findAll().size();

        // Delete the empregado
        restEmpregadoMockMvc.perform(delete("/api/empregados/{id}", empregado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empregado> empregadoList = empregadoRepository.findAll();
        assertThat(empregadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Empregado in Elasticsearch
        verify(mockEmpregadoSearchRepository, times(1)).deleteById(empregado.getId());
    }

    @Test
    @Transactional
    public void searchEmpregado() throws Exception {
        // Initialize the database
        empregadoRepository.saveAndFlush(empregado);
        when(mockEmpregadoSearchRepository.search(queryStringQuery("id:" + empregado.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(empregado), PageRequest.of(0, 1), 1));
        // Search the empregado
        restEmpregadoMockMvc.perform(get("/api/_search/empregados?query=id:" + empregado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empregado.getId().intValue())))
            .andExpect(jsonPath("$.[*].primeiroNome").value(hasItem(DEFAULT_PRIMEIRO_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.intValue())))
            .andExpect(jsonPath("$.[*].comissao").value(hasItem(DEFAULT_COMISSAO.intValue())));
    }
}
