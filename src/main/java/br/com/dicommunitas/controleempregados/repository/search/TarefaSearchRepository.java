package br.com.dicommunitas.controleempregados.repository.search;
import br.com.dicommunitas.controleempregados.domain.Tarefa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Tarefa} entity.
 */
public interface TarefaSearchRepository extends ElasticsearchRepository<Tarefa, Long> {
}
