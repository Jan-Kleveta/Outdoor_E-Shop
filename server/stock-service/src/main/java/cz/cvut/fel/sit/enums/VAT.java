package cz.cvut.fel.sit.enums;

public enum VAT {
    DPH_21(21);

    private final int value;

    VAT(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VAT fromValue(int value) {
        for (VAT vat : values()) {
            if (vat.value == value) {
                return vat;
            }
        }
        throw new IllegalArgumentException("Invalid VAT value: " + value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
