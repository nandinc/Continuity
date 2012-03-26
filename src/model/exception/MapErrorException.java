package model.exception;

public class MapErrorException extends Exception {
    private static final long serialVersionUID = 1L;

    public MapErrorException() {
        super();
    }

    public MapErrorException(String message) {
        super(message);
    }

    public MapErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapErrorException(Throwable cause) {
        super(cause);
    }
}
