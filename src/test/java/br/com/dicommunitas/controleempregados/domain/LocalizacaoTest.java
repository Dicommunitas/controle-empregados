package br.com.dicommunitas.controleempregados.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dicommunitas.controleempregados.web.rest.TestUtil;

public class LocalizacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localizacao.class);
        Localizacao localizacao1 = new Localizacao();
        localizacao1.setId(1L);
        Localizacao localizacao2 = new Localizacao();
        localizacao2.setId(localizacao1.getId());
        assertThat(localizacao1).isEqualTo(localizacao2);
        localizacao2.setId(2L);
        assertThat(localizacao1).isNotEqualTo(localizacao2);
        localizacao1.setId(null);
        assertThat(localizacao1).isNotEqualTo(localizacao2);
    }
}
