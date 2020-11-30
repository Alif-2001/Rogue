package rogue;

import java.util.ArrayList;
import java.io.Serializable;

public class Inventory implements Serializable {
    private ArrayList<Item> pickedItems = new ArrayList<>();

    /**
     * default zero parameter constructor.
     */
    public Inventory() {

    }

    /**
     * this method is used to add item to the inventory.
     * @param item the new item
     */
    public void addItem(Item item) {
        pickedItems.add(item);
    }

    /**
     * this method is used to get all the item in the inventory.
     * @return an array of Items in the inventory
     */
    public ArrayList<Item> getItems() {
        return pickedItems;
    }

    /**
     * this method is used to remove an item in the inventory.
     * @param item it's the item we want to remove
     */
    public void removeItem(Item item) {
        pickedItems.remove(item);
    }
}
