package br.com.amarques.notasfiscais.dto;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class NotaFiscalDTO {

    public final Long id;
    public final Integer numero;
    public final String data;
    public final BigDecimal valor;

    public final Long tomadorId;
    public final Long prestadorId;

}
