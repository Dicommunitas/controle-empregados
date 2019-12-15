package br.com.dicommunitas.controleempregados.repository.search;
import br.com.dicommunitas.controleempregados.domain.Localizacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Localizacao} entity.
 */
public interface LocalizacaoSearchRepository extends ElasticsearchRepository<Localizacao, Long> {
}
