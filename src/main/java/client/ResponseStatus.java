package client;

public enum ResponseStatus {
    OK(0x00),
    ERROR(0x01),
    UNKNOWN(0xFFFFFF);

    private final int statusCode;

    ResponseStatus(int number) {
        statusCode = number;
    }

    public static ResponseStatus fromInt(int code) {
        for (ResponseStatus status : ResponseStatus.values()) {
            if (status.getStatusCode() == code) {
                return status;
            }
        }

        return UNKNOWN;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
