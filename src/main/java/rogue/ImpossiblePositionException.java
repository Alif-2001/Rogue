package rogue;
public class ImpossiblePositionException extends Exception {
    /**
     * default zero parameter constructor.
     */
    public ImpossiblePositionException() {

    }
    /**
     * Exception constructor.
     * @param s excepetion message
     */
    public ImpossiblePositionException(String s) {
        super(s);
    }
}
