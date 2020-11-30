package rogue;
public class NotEnoughDoorsException extends Exception {
    public NotEnoughDoorsException(){
        
    }
    /**
     * Exception constructor.
     * @param s excepetion message
     */
    public NotEnoughDoorsException(String s) {
        super(s);
    }
}
