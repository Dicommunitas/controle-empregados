package br.com.dicommunitas.controleempregados.repository.search;
import br.com.dicommunitas.controleempregados.domain.Pais;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pais} entity.
 */
public interface PaisSearchRepository extends ElasticsearchRepository<Pais, Long> {
}
