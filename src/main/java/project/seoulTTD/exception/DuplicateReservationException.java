package project.seoulTTD.exception;

public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException(String message) {
        super(message);
    }

  public DuplicateReservationException() {
    super();
  }

  public DuplicateReservationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateReservationException(Throwable cause) {
    super(cause);
  }
}
