package exceptions;

public class InvalidScoreException extends Exception{
    public InvalidScoreException() {
        super("Invalid score");
    }
}
