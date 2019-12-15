package br.com.dicommunitas.controleempregados.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dicommunitas.controleempregados.web.rest.TestUtil;

public class TarefaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarefa.class);
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setId(1L);
        Tarefa tarefa2 = new Tarefa();
        tarefa2.setId(tarefa1.getId());
        assertThat(tarefa1).isEqualTo(tarefa2);
        tarefa2.setId(2L);
        assertThat(tarefa1).isNotEqualTo(tarefa2);
        tarefa1.setId(null);
        assertThat(tarefa1).isNotEqualTo(tarefa2);
    }
}
