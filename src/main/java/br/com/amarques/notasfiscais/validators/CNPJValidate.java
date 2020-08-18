package br.com.amarques.notasfiscais.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CNPJValidate {

    public static boolean isValid(String cnpj) {
        try {
            cnpj = removeSpecialCharacters(cnpj);

            if (isSequentialNumbers(cnpj) || cnpj.length() != 14) {
                return false;
            }

            int soma = 0;
            int dig;
            String cnpjCalc = cnpj.substring(0, 12);

            char[] chrCnpj = cnpj.toCharArray();

            for (int i = 0; i < 4; i++) {
                if (chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9) {
                    soma += (chrCnpj[i] - 48) * (6 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chrCnpj[i + 4] - 48 >= 0 && chrCnpj[i + 4] - 48 <= 9) {
                    soma += (chrCnpj[i + 4] - 48) * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpjCalc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

            soma = 0;
            for (int i = 0; i < 5; i++) {
                if (chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9) {
                    soma += (chrCnpj[i] - 48) * (7 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chrCnpj[i + 5] - 48 >= 0 && chrCnpj[i + 5] - 48 <= 9) {
                    soma += (chrCnpj[i + 5] - 48) * (10 - (i + 1));
                }
            }

            dig = 11 - (soma % 11);
            cnpjCalc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

            return cnpj.equals(cnpjCalc);

        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isSequentialNumbers(String cnpj) {
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                cnpj.equals("88888888888888") || cnpj.equals("99999999999999")) {
            return true;
        }
        return false;
    }

    private static String removeSpecialCharacters(String cnpj) {
        return cnpj.replace(".", "").replace("-", "").replace("/", "");
    }

}
