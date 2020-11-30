package rogue;

public class InvalidMoveException extends Exception {
    /**
     * Default zero parameter constructor.
     */
    public InvalidMoveException() {

    }
    /**
     * Exception constructor.
     * @param s excepetion message
     */
    public InvalidMoveException(String s) {
        super(s);
    }
}
