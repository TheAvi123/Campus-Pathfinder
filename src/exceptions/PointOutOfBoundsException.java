package exceptions;

public class PointOutOfBoundsException extends Exception {

    public PointOutOfBoundsException() {
        super();
    }

    public PointOutOfBoundsException(String msg) {
        super(msg);
    }
}
