package br.com.dicommunitas.controleempregados.repository.search;
import br.com.dicommunitas.controleempregados.domain.Empregado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Empregado} entity.
 */
public interface EmpregadoSearchRepository extends ElasticsearchRepository<Empregado, Long> {
}
