package controller.console.exception;

/**
 * Parancs hibáját jelző osztály
 * 
 * @responsibility Hibás parancs jelzése
 */
public class InvalidArgumentException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }
}
