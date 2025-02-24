package project.seoulTTD.exception;

public class NoEnoughRoomException extends RuntimeException {
    public NoEnoughRoomException() {
        super();
    }

    public NoEnoughRoomException(String message) {
        super(message);
    }

    public NoEnoughRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEnoughRoomException(Throwable cause) {
        super(cause);
    }
}
