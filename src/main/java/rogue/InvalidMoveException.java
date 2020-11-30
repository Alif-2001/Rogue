package rogue;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(){
        
    }
    /**
     * Exception constructor.
     * @param s excepetion message
     */
    public InvalidMoveException(String s) {
        super(s);
    }
}
