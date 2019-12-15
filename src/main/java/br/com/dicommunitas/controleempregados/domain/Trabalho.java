package br.com.dicommunitas.controleempregados.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Trabalho.
 */
@Entity
@Table(name = "trabalho")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trabalho")
public class Trabalho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "salario_minimo")
    private Long salarioMinimo;

    @Column(name = "salario_maximo")
    private Long salarioMaximo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "trabalho_tarefa",
               joinColumns = @JoinColumn(name = "trabalho_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tarefa_id", referencedColumnName = "id"))
    private Set<Tarefa> tarefas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("trabalhos")
    private Empregado empregado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Trabalho titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getSalarioMinimo() {
        return salarioMinimo;
    }

    public Trabalho salarioMinimo(Long salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
        return this;
    }

    public void setSalarioMinimo(Long salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public Long getSalarioMaximo() {
        return salarioMaximo;
    }

    public Trabalho salarioMaximo(Long salarioMaximo) {
        this.salarioMaximo = salarioMaximo;
        return this;
    }

    public void setSalarioMaximo(Long salarioMaximo) {
        this.salarioMaximo = salarioMaximo;
    }

    public Set<Tarefa> getTarefas() {
        return tarefas;
    }

    public Trabalho tarefas(Set<Tarefa> tarefas) {
        this.tarefas = tarefas;
        return this;
    }

    public Trabalho addTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
        tarefa.getTrabalhos().add(this);
        return this;
    }

    public Trabalho removeTarefa(Tarefa tarefa) {
        this.tarefas.remove(tarefa);
        tarefa.getTrabalhos().remove(this);
        return this;
    }

    public void setTarefas(Set<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public Empregado getEmpregado() {
        return empregado;
    }

    public Trabalho empregado(Empregado empregado) {
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
        if (!(o instanceof Trabalho)) {
            return false;
        }
        return id != null && id.equals(((Trabalho) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trabalho{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", salarioMinimo=" + getSalarioMinimo() +
            ", salarioMaximo=" + getSalarioMaximo() +
            "}";
    }
}
