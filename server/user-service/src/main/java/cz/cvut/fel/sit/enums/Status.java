package cz.cvut.fel.sit.enums;

public enum Status {
    REGISTERED,
    BLOCKED,
    DELETED;

    public static Status fromString(String status) {
        return Status.valueOf(status.toUpperCase());
    }
}
