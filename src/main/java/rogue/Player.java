package rogue;
import java.awt.Point;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * The player character.
 */
public class Player implements Serializable {

    private String playerName;
    private Point playerXyLocation;
    private Room playerRoom;

    private Inventory inventory = new Inventory();
    private ArrayList<Item> clothes = new ArrayList<Item>();


    /**
     * player constructor.
     */
    public Player() {

    }

    /**
     * initiate player with a name.
     * @param name player's name.
     */
    public Player(String name) {
        playerName = name;
    }

    /**
     * this method is used to get player's name.
     * @return player's name
     */
    public String getName() {
        return playerName;
    }

    /**
     * this method is used to set the name of the player.
     * @param newName player's new name
     */
    public void setName(String newName) {
        playerName = newName;
    }

    /**
     * this method is used to get player's position.
     * @return player's position
     */
    public Point getXyLocation() {
        return playerXyLocation;

    }

    /**
     * this method is used to set the player's loaction.
     * @param newXyLocation player's new location
     */
    public void setXyLocation(Point newXyLocation) {
        playerXyLocation = newXyLocation;
    }

    /**
     * this method is used to know where the player currently is.
     * @return player's current room
     */
    public Room getCurrentRoom() {
        return playerRoom;

    }

    /**
     * this method is used to set player's current room.
     * @param newRoom player's new room.
     */
    public void setCurrentRoom(Room newRoom) {
        playerRoom = newRoom;
    }

    /**
     * this method is used to pick an item. The item is added to the list of item player has picked.
     * @param newItem item the player picks.
     */
    public void pickItem(Item newItem) {
        inventory.addItem(newItem);
        newItem.setPlayer(this);
    }

    /**
     * this method is used to get all the items the player has picked.
     * @return an array of item the player has
     */
    public ArrayList<Item> getInventory() {
        return inventory.getItems();
    }

    /**
     * this methods is used to get the clothes the player is wearing.
     * @return the array of item the player is wearing
     */
    public ArrayList<Item> getClothes() {
        return clothes;
    }

    /**
     * this method removes an item from the player's inventory.
     * @param itemToRemove the item to be removed
     */
    public void removeItemFromInventory(Item itemToRemove) {
        inventory.removeItem(itemToRemove);
    }

    /**
     * this method is used to add items to the list of clothes the player is wearing.
     * @param itemToWear the item to wear
     */
    public void wearItem(Item itemToWear) {
        clothes.add(itemToWear);
    }

    /**
     * this method is used to drop an item. the item is removed from the list of item the player currently has.
     * @param itemToDrop item the player needs to drop.
     */

    public void tossItem(Item itemToDrop) {
        inventory.removeItem(itemToDrop);
        itemToDrop.setXyLocation(this.getXyLocation());
        playerRoom.addSingleItem(itemToDrop);
    }
}
