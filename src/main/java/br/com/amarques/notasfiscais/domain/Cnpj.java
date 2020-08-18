package br.com.amarques.notasfiscais.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.amarques.notasfiscais.exception.InvalidCNPJException;
import br.com.amarques.notasfiscais.validators.CNPJValidate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Cnpj {

    @Column(name = "cnpj", unique = true)
    private String cnpj;

    protected Cnpj(String cnpj) {
        if (!CNPJValidate.isValid(cnpj)) {
            throw new InvalidCNPJException("The CNPJ is not valid");
        }
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }
}
