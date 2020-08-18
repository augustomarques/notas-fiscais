package br.com.amarques.notasfiscais.formatters;

import br.com.amarques.notasfiscais.exception.InvalidCNPJException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CNPJFormatter {

    public static String format(String cnpj) {
        if (cnpj.length() != 14) {
            throw new InvalidCNPJException("The CNPJ is not valid");
        }

        return new StringBuilder()
                .append(cnpj.substring(0, 2)).append(".")
                .append(cnpj.substring(2, 5)).append(".")
                .append(cnpj.substring(5, 8)).append("/")
                .append(cnpj.substring(8, 12)).append("-")
                .append(cnpj.substring(12, 14))
                .toString();
    }
}
