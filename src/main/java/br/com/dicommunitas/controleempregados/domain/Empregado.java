package br.com.dicommunitas.controleempregados.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade Empregado.
 */
@ApiModel(description = "Entidade Empregado.")
@Entity
@Table(name = "empregado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "empregado")
public class Empregado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    /**
     * Atributo primeiroNome.
     */
    @ApiModelProperty(value = "Atributo primeiroNome.")
    @Column(name = "primeiro_nome")
    private String primeiroNome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "data_contratacao")
    private Instant dataContratacao;

    @Column(name = "salario")
    private Long salario;

    @Column(name = "comissao")
    private Long comissao;

    @OneToMany(mappedBy = "empregado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trabalho> trabalhos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("empregados")
    private Empregado gerente;

    /**
     * Outro lado do mesmo relacionamento
     */
    @ApiModelProperty(value = "Outro lado do mesmo relacionamento")
    @ManyToOne
    @JsonIgnoreProperties("empregados")
    private Departamento departamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public Empregado primeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
        return this;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Empregado sobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return this;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public Empregado email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Empregado telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Instant getDataContratacao() {
        return dataContratacao;
    }

    public Empregado dataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
        return this;
    }

    public void setDataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Long getSalario() {
        return salario;
    }

    public Empregado salario(Long salario) {
        this.salario = salario;
        return this;
    }

    public void setSalario(Long salario) {
        this.salario = salario;
    }

    public Long getComissao() {
        return comissao;
    }

    public Empregado comissao(Long comissao) {
        this.comissao = comissao;
        return this;
    }

    public void setComissao(Long comissao) {
        this.comissao = comissao;
    }

    public Set<Trabalho> getTrabalhos() {
        return trabalhos;
    }

    public Empregado trabalhos(Set<Trabalho> trabalhos) {
        this.trabalhos = trabalhos;
        return this;
    }

    public Empregado addTrabalho(Trabalho trabalho) {
        this.trabalhos.add(trabalho);
        trabalho.setEmpregado(this);
        return this;
    }

    public Empregado removeTrabalho(Trabalho trabalho) {
        this.trabalhos.remove(trabalho);
        trabalho.setEmpregado(null);
        return this;
    }

    public void setTrabalhos(Set<Trabalho> trabalhos) {
        this.trabalhos = trabalhos;
    }

    public Empregado getGerente() {
        return gerente;
    }

    public Empregado gerente(Empregado empregado) {
        this.gerente = empregado;
        return this;
    }

    public void setGerente(Empregado empregado) {
        this.gerente = empregado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Empregado departamento(Departamento departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empregado)) {
            return false;
        }
        return id != null && id.equals(((Empregado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Empregado{" +
            "id=" + getId() +
            ", primeiroNome='" + getPrimeiroNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", salario=" + getSalario() +
            ", comissao=" + getComissao() +
            "}";
    }
}
