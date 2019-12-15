package br.com.dicommunitas.controleempregados.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import br.com.dicommunitas.controleempregados.domain.enumeration.Lingua;

/**
 * A HistoricoDeTrabalho.
 */
@Entity
@Table(name = "historico_de_trabalho")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "historicodetrabalho")
public class HistoricoDeTrabalho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "data_inicial")
    private Instant dataInicial;

    @Column(name = "data_final")
    private Instant dataFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "lingua")
    private Lingua lingua;

    @OneToOne
    @JoinColumn(unique = true)
    private Trabalho trabalho;

    @OneToOne
    @JoinColumn(unique = true)
    private Departamento departamento;

    @OneToOne
    @JoinColumn(unique = true)
    private Empregado empregado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataInicial() {
        return dataInicial;
    }

    public HistoricoDeTrabalho dataInicial(Instant dataInicial) {
        this.dataInicial = dataInicial;
        return this;
    }

    public void setDataInicial(Instant dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Instant getDataFinal() {
        return dataFinal;
    }

    public HistoricoDeTrabalho dataFinal(Instant dataFinal) {
        this.dataFinal = dataFinal;
        return this;
    }

    public void setDataFinal(Instant dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Lingua getLingua() {
        return lingua;
    }

    public HistoricoDeTrabalho lingua(Lingua lingua) {
        this.lingua = lingua;
        return this;
    }

    public void setLingua(Lingua lingua) {
        this.lingua = lingua;
    }

    public Trabalho getTrabalho() {
        return trabalho;
    }

    public HistoricoDeTrabalho trabalho(Trabalho trabalho) {
        this.trabalho = trabalho;
        return this;
    }

    public void setTrabalho(Trabalho trabalho) {
        this.trabalho = trabalho;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public HistoricoDeTrabalho departamento(Departamento departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Empregado getEmpregado() {
        return empregado;
    }

    public HistoricoDeTrabalho empregado(Empregado empregado) {
        this.empregado = empregado;
        return this;
    }

    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoricoDeTrabalho)) {
            return false;
        }
        return id != null && id.equals(((HistoricoDeTrabalho) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HistoricoDeTrabalho{" +
            "id=" + getId() +
            ", dataInicial='" + getDataInicial() + "'" +
            ", dataFinal='" + getDataFinal() + "'" +
            ", lingua='" + getLingua() + "'" +
            "}";
    }
}
