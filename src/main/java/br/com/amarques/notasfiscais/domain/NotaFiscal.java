package br.com.amarques.notasfiscais.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "notas_fiscais")
@SequenceGenerator(name = "nota_fiscal_pk", sequenceName = "nota_fiscal_id_seq", allocationSize = 1)
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_pk")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "valor")
    private BigDecimal valor;

    @OneToOne
    @JoinColumn(name = "tomador_id")
    private Empresa tomador;

    @OneToOne
    @JoinColumn(name = "prestador_id")
    private Empresa prestador;

    public NotaFiscal(Integer numero, LocalDate data, BigDecimal valor) {
        this.numero = numero;
        this.data = data;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Empresa getTomador() {
        return tomador;
    }

    public void setTomador(Empresa tomador) {
        this.tomador = tomador;
    }

    public Empresa getPrestador() {
        return prestador;
    }

    public void setPrestador(Empresa prestador) {
        this.prestador = prestador;
    }

}
