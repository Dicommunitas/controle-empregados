package br.com.dicommunitas.controleempregados.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dicommunitas.controleempregados.web.rest.TestUtil;

public class PaisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pais.class);
        Pais pais1 = new Pais();
        pais1.setId(1L);
        Pais pais2 = new Pais();
        pais2.setId(pais1.getId());
        assertThat(pais1).isEqualTo(pais2);
        pais2.setId(2L);
        assertThat(pais1).isNotEqualTo(pais2);
        pais1.setId(null);
        assertThat(pais1).isNotEqualTo(pais2);
    }
}
