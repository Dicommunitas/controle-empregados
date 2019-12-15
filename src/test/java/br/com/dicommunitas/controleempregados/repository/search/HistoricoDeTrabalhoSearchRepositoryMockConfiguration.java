package br.com.dicommunitas.controleempregados.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link HistoricoDeTrabalhoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class HistoricoDeTrabalhoSearchRepositoryMockConfiguration {

    @MockBean
    private HistoricoDeTrabalhoSearchRepository mockHistoricoDeTrabalhoSearchRepository;

}
