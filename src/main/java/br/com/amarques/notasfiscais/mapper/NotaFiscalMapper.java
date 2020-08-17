package br.com.amarques.notasfiscais.mapper;

import br.com.amarques.notasfiscais.converters.DateConverter;
import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.domain.NotaFiscal;
import br.com.amarques.notasfiscais.dto.NotaFiscalDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateNotaFiscalDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotaFiscalMapper {

    public static NotaFiscal toEntity(CreateUpdateNotaFiscalDTO dto, Empresa tomador, Empresa prestador) {
        NotaFiscal notaFiscal = new NotaFiscal(dto.numero, DateConverter.toLocalDate(dto.data), dto.valor);
        notaFiscal.setTomador(tomador);
        notaFiscal.setPrestador(prestador);
        return notaFiscal;
    }

    public static NotaFiscalDTO toDTO(NotaFiscal notaFiscal) {
        return new NotaFiscalDTO(notaFiscal.getId(), notaFiscal.getNumero(),
                DateConverter.toString(notaFiscal.getData()), notaFiscal.getValor(), notaFiscal.getTomador().getId(),
                notaFiscal.getPrestador().getId());
    }
}
