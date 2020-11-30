package rogue;

public class NoSuchItemException extends Exception {
    private Item item = new Item();

    /**
     * default zero parameter constructor.
     */
    public NoSuchItemException() {

    }

    /**
     * Exception constructor.
     * @param s excepetion message
     */
    public NoSuchItemException(String s) {
        super(s);
    }

    /**
     * This method is used to set the item that is missing.
     * @param noItem the missing item.
     */
    public void setMissingItem(Item noItem) {
        item = noItem;
    }

    /**
     * This method is used to get the missing item.
     * @return the missing item
     */
    public Item getItem() {
        return item;
    }
}
