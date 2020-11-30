package rogue;
public class NotEnoughDoorsException extends Exception {
    /**
     * default zero parameter constructor.
     */
    public NotEnoughDoorsException() {

    }
    /**
     * Exception constructor.
     * @param s excepetion message
     */
    public NotEnoughDoorsException(String s) {
        super(s);
    }
}
