package br.com.inova.sigin.shared.util;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringUtil {

    private StringUtil() {
    }

    public static String normalizarNome(String valor) {

        if (valor == null || valor.isBlank()) {
            return valor;
        }

        valor = valor.trim().toLowerCase(Locale.of("pt", "BR"));

        return Arrays.stream(valor.split(" "))
                .filter(p -> !p.isBlank())
                .map(p -> p.substring(0, 1).toUpperCase()
                        + p.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static String removerAcentos(String valor) {

        if (valor == null) {
            return null;
        }

        return Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}