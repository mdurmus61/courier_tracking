package md.ct.exception;

public class CourierTrackingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CourierTrackingException(String message) {
        super(message);
    }

    public CourierTrackingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourierTrackingException(Throwable cause) {
        super(cause);
    }
}
