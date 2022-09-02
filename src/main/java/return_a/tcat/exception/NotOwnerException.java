package return_a.tcat.exception;

public class NotOwnerException extends RuntimeException{
    public NotOwnerException() {
        super("티켓의 소유자가 아닙니다.");
    }
}
