package return_a.tcat.exception;

public class DuplicateMemberException extends RuntimeException{
    public DuplicateMemberException() {
        super("이미 있는 유저입니다.");
    }
}
