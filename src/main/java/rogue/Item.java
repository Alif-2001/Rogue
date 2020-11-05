package rogue;
import java.awt.Point;

/**
 * A basic Item class; basic functionality for both consumables and equipment
 */
public class Item  {

    private int itemId;
    private String itemName;
    private String itemType;
    private Point itemXyLocation;
    private Character itemDisplay;
    private String itemDescription;
    private Room itemRoom;

    //Constructors
    public Item() {
    }

    public Item(int id, String name, String type, Point xyLocation) {
        itemId = id;
        itemName = name;
        itemType = type;
        itemXyLocation = xyLocation;
    }
    // Getters and setters


    public int getId() {
        return itemId;
    }


    public void setId(int id) {
        itemId = id;
    }


    public String getName() {
        return itemName;
    }


    public void setName(String name) {
        itemName = name;
    }


    public String getType() {
        return itemType;

    }


    public void setType(String type) {
        itemType = type;
    }


    public Character getDisplayCharacter() {
        return itemDisplay;
    }


    public void setDisplayCharacter(Character newDisplayCharacter) {
        itemDisplay = newDisplayCharacter;
    }


    public String getDescription() {
        return itemDescription;
    }


    public void setDescription(String newDescription) {
        itemDescription = newDescription;
    }


    public Point getXyLocation() {
        return itemXyLocation;
    }

    public void setXyLocation(Point newXyLocation) {
        itemXyLocation = newXyLocation;
    }


    public Room getCurrentRoom() {
        return itemRoom;
    }


    public void setCurrentRoom(Room newCurrentRoom) {
        itemRoom = newCurrentRoom;
    }
}
