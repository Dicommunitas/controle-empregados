package br.com.dicommunitas.controleempregados.repository.search;
import br.com.dicommunitas.controleempregados.domain.Trabalho;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Trabalho} entity.
 */
public interface TrabalhoSearchRepository extends ElasticsearchRepository<Trabalho, Long> {
}
