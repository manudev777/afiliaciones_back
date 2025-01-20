package com.rtecnico.afiliaciones.utils;

import java.text.Normalizer;

public class Utils {

    public static String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }
}
