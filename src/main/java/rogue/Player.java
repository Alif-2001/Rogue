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


    // Default constructor
    public Player() {

    }


    public Player(String name) {
        playerName = name;
    }


    public String getName() {
        return playerName;
    }


    public void setName(String newName) {
        playerName = newName;
    }

    public Point getXyLocation() {
        return playerXyLocation;

    }


    public void setXyLocation(Point newXyLocation) {
        playerXyLocation = newXyLocation;
    }


    public Room getCurrentRoom() {
        return playerRoom;

    }


    public void setCurrentRoom(Room newRoom) {
        playerRoom = newRoom;
    }

    public void pickItem(Item newItem) {
        pickedItems.add(newItem);
    }

    public ArrayList<Item> getItems() {
        return pickedItems;
    }

    public void dropItem(Item itemToDrop) {
        pickedItems.remove(itemToDrop);
    }
}
