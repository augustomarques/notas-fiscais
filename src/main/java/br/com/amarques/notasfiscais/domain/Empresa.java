package br.com.amarques.notasfiscais.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "empresas")
@SequenceGenerator(name = "empresa_pk", sequenceName = "empresa_id_seq", allocationSize = 1)
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_pk")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "fantasia")
    private String fantasia;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Embedded
    private Cnpj cnpj;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoEmpresaEnum tipo;

    public Empresa(String razaoSocial, String cnpj, TipoEmpresaEnum tipo) {
        this.razaoSocial = razaoSocial;
        this.cnpj = new Cnpj(cnpj);
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj.getCnpj();
    }

    public void setCnpj(String cnpj) {
        this.cnpj = new Cnpj(cnpj);
    }

    public TipoEmpresaEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoEmpresaEnum tipo) {
        this.tipo = tipo;
    }

}
