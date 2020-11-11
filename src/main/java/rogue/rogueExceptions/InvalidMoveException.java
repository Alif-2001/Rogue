package rogue.rogueExceptions;

public class InvalidMoveException extends Exception {
    /**
     * Exception constructor.
     * @param s excepetion message
     */
    public InvalidMoveException(String s) {
        super(s);
    }
}
