package br.com.dicommunitas.controleempregados.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link EmpregadoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EmpregadoSearchRepositoryMockConfiguration {

    @MockBean
    private EmpregadoSearchRepository mockEmpregadoSearchRepository;

}
