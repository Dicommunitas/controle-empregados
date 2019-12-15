package br.com.dicommunitas.controleempregados.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dicommunitas.controleempregados.web.rest.TestUtil;

public class RegiaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regiao.class);
        Regiao regiao1 = new Regiao();
        regiao1.setId(1L);
        Regiao regiao2 = new Regiao();
        regiao2.setId(regiao1.getId());
        assertThat(regiao1).isEqualTo(regiao2);
        regiao2.setId(2L);
        assertThat(regiao1).isNotEqualTo(regiao2);
        regiao1.setId(null);
        assertThat(regiao1).isNotEqualTo(regiao2);
    }
}
