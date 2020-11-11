package rogue.rogueExceptions;

import rogue.Item;

public class NoSuchItemException extends Exception {
    private Item item = new Item();
    public NoSuchItemException(String s) {
        super(s);
    }
    public void setMissingItem(Item noItem) {
        item = noItem;
    }
    public Item getItem() {
        return item;
    }
}
