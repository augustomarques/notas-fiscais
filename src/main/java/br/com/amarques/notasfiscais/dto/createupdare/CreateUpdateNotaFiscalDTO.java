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

    @NotNull(message = "O número é obrigatório")
    public final Integer numero;
    @NotEmpty(message = "A data é obrigatória")
    public final String data;
    @NotNull(message = "O valor é obrigatório")
    public final BigDecimal valor;

    @NotNull(message = "O ID do tomador é obrigatório")
    public final Long tomadorId;
    @NotNull(message = "O ID do prestador é obrigatório")
    public final Long prestadorId;

}
