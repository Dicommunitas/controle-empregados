package br.com.dicommunitas.controleempregados.repository.search;
import br.com.dicommunitas.controleempregados.domain.HistoricoDeTrabalho;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link HistoricoDeTrabalho} entity.
 */
public interface HistoricoDeTrabalhoSearchRepository extends ElasticsearchRepository<HistoricoDeTrabalho, Long> {
}
