package rogue;
public class ImpossiblePositionException extends Exception {
    public ImpossiblePositionException(){
        
    }
    /**
     * Exception constructor.
     * @param s excepetion message
     */
    public ImpossiblePositionException(String s) {
        super(s);
    }
}
