package br.com.dicommunitas.controleempregados.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dicommunitas.controleempregados.web.rest.TestUtil;

public class HistoricoDeTrabalhoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricoDeTrabalho.class);
        HistoricoDeTrabalho historicoDeTrabalho1 = new HistoricoDeTrabalho();
        historicoDeTrabalho1.setId(1L);
        HistoricoDeTrabalho historicoDeTrabalho2 = new HistoricoDeTrabalho();
        historicoDeTrabalho2.setId(historicoDeTrabalho1.getId());
        assertThat(historicoDeTrabalho1).isEqualTo(historicoDeTrabalho2);
        historicoDeTrabalho2.setId(2L);
        assertThat(historicoDeTrabalho1).isNotEqualTo(historicoDeTrabalho2);
        historicoDeTrabalho1.setId(null);
        assertThat(historicoDeTrabalho1).isNotEqualTo(historicoDeTrabalho2);
    }
}
