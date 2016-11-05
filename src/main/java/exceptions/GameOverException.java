package exceptions;

public class GameOverException extends Exception {
    public GameOverException() {
        super("The game is over and no more rolls can be played");
    }
}
