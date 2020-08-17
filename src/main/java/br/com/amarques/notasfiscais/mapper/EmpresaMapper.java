package br.com.amarques.notasfiscais.mapper;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.dto.EmpresaDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateEmpresaDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmpresaMapper {

    public static Empresa toEntity(final CreateUpdateEmpresaDTO dto) {
        Empresa empresa = new Empresa(dto.razaoSocial, dto.cnpj, dto.tipo);
        empresa.setFantasia(dto.fantasia);
        return empresa;
    }

    public static EmpresaDTO toDTO(final Empresa empresa) {
        return new EmpresaDTO(empresa.getId(), empresa.getFantasia(), empresa.getRazaoSocial(), empresa.getCnpj(),
                empresa.getTipo());
    }
}
