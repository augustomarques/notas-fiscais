package br.com.amarques.notasfiscais.dto;

import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class EmpresaDTO {

    public final Long id;
    public final String fantasia;
    public final String razaoSocial;
    public final String cnpj;
    public final TipoEmpresaEnum tipo;

}
