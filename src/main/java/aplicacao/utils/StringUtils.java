package aplicacao.utils;

public class StringUtils {

    public static String retirarCaracteresNaoNumericos(String str) {
        if (str == null) {
            return "";
        }
        return str.replaceAll("[^\\d]", "");
    }

    public static String retirarCaracteresNumericos(String str) {
        if (str == null) {
            return "";
        }
        return str.replaceAll("[\\d]", "");
    }
}
