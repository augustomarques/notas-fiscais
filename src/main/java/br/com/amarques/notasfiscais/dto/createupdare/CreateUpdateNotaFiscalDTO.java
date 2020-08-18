package br.com.amarques.notasfiscais.dto.createupdare;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateUpdateNotaFiscalDTO {

    @NotNull
    public final Integer numero;
    @NotEmpty
    public final String data;
    @NotNull
    public final BigDecimal valor;

    @NotNull
    public final Long tomadorId;
    @NotNull
    public final Long prestadorId;

}
