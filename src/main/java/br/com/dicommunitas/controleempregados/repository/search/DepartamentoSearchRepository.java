package br.com.dicommunitas.controleempregados.repository.search;
import br.com.dicommunitas.controleempregados.domain.Departamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Departamento} entity.
 */
public interface DepartamentoSearchRepository extends ElasticsearchRepository<Departamento, Long> {
}
