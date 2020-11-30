package rogue;

import java.util.ArrayList;
import java.io.Serializable;

public class Inventory implements Serializable{
    private ArrayList<Item> pickedItems = new ArrayList<>();
    public Inventory(){

    }

    public void addItem(Item item){
        pickedItems.add(item);
    }

    public ArrayList<Item> getItems(){
        return pickedItems;
    }

    public void removeItem(Item item){
        pickedItems.remove(item);
    }
}
