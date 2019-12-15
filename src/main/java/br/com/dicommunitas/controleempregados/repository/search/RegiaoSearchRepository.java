package br.com.dicommunitas.controleempregados.repository.search;
import br.com.dicommunitas.controleempregados.domain.Regiao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Regiao} entity.
 */
public interface RegiaoSearchRepository extends ElasticsearchRepository<Regiao, Long> {
}
