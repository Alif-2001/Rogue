package rogue;
import java.awt.Point;
import java.util.ArrayList;
/**
 * The player character.
 */
public class Player {

    private String playerName;
    private Point playerXyLocation;
    private Room playerRoom;

    private ArrayList<Item> pickedItems = new ArrayList<>();


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
        pickedItems.add(newItem);
    }

    /**
     * this method is used to get all the items the player has picked.
     * @return an array of item the player has
     */
    public ArrayList<Item> getItems() {
        return pickedItems;
    }

    /**
     * this method is used to drop an item. the item is removed from the list of item the player currently has.
     * @param itemToDrop item the player needs to drop.
     */
    public void dropItem(Item itemToDrop) {
        pickedItems.remove(itemToDrop);
    }
}
