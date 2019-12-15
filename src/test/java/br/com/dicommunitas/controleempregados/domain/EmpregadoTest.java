package br.com.dicommunitas.controleempregados.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dicommunitas.controleempregados.web.rest.TestUtil;

public class EmpregadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empregado.class);
        Empregado empregado1 = new Empregado();
        empregado1.setId(1L);
        Empregado empregado2 = new Empregado();
        empregado2.setId(empregado1.getId());
        assertThat(empregado1).isEqualTo(empregado2);
        empregado2.setId(2L);
        assertThat(empregado1).isNotEqualTo(empregado2);
        empregado1.setId(null);
        assertThat(empregado1).isNotEqualTo(empregado2);
    }
}
