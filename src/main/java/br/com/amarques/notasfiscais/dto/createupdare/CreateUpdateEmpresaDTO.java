package br.com.amarques.notasfiscais.dto.createupdare;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateUpdateEmpresaDTO {

    public final String fantasia;
    @NotEmpty
    public final String razaoSocial;
    @NotEmpty
    public final String cnpj;
    @NotNull
    public final TipoEmpresaEnum tipo;

}
