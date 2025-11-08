package cz.cvut.fel.sit.enums;

public enum Prefix {
    CZ_420("+420"),
    DE_49("+49"),
    SK_421("+421"),
    US_1("+1"),
    UK_44("+44");

    private final String code;

    Prefix(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * Najde prefix podle kódu, např. "+420"
     */
    public static Prefix fromCode(String code) {
        for (Prefix prefix : Prefix.values()) {
            if (prefix.code.equals(code)) {
                return prefix;
            }
        }
        throw new IllegalArgumentException("Unknown prefix code: " + code);
    }
}
