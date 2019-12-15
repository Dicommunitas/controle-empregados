package br.com.dicommunitas.controleempregados.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dicommunitas.controleempregados.web.rest.TestUtil;

public class TrabalhoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trabalho.class);
        Trabalho trabalho1 = new Trabalho();
        trabalho1.setId(1L);
        Trabalho trabalho2 = new Trabalho();
        trabalho2.setId(trabalho1.getId());
        assertThat(trabalho1).isEqualTo(trabalho2);
        trabalho2.setId(2L);
        assertThat(trabalho1).isNotEqualTo(trabalho2);
        trabalho1.setId(null);
        assertThat(trabalho1).isNotEqualTo(trabalho2);
    }
}
