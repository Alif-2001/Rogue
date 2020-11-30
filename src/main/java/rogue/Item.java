package rogue;
import java.awt.Point;
import java.io.Serializable;
/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item implements Serializable{

    private int itemId;
    private String itemName;
    private String itemType;
    private Point itemXyLocation;
    private Character itemDisplay;
    private String itemDescription;
    private Room itemRoom;
    private Player player;

    /**
     * Item constructor.
     */
    public Item() {
    }

    /**
     * Item constructor, overload.
     * @param id item ID
     * @param name item name
     * @param type item type
     * @param xyLocation item loaction in room
     */
    public Item(int id, String name, String type, Point xyLocation) {
        itemId = id;
        itemName = name;
        itemType = type;
        itemXyLocation = xyLocation;
    }
    // Getters and setters

    /**
     * get item's id.
     * @return Item ID
     */
    public int getId() {
        return itemId;
    }

    /**
     * set item's id.
     * @param id item's new ID
     */
    public void setId(int id) {
        itemId = id;
    }

    /**
     * get item's name.
     * @return item's name
     */
    public String getName() {
        return itemName;
    }

    /**
     * set item's name.
     * @param name item's new name
     */
    public void setName(String name) {
        itemName = name;
    }

    /**
     * get item's type.
     * @return item's type
     */
    public String getType() {
        return itemType;

    }

    /**
     * set item's type.
     * @param type item's new type
     */
    public void setType(String type) {
        itemType = type;
    }

    /**
     * get the char that represents the item.
     * @return char that represents thhe item
     */
    public Character getDisplayCharacter() {
        return itemDisplay;
    }

    /**
     * set item's display char.
     * @param newDisplayCharacter new display char
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        itemDisplay = newDisplayCharacter;
    }

    /**
     * get item's description.
     * @return item's desription
     */
    public String getDescription() {
        return itemDescription;
    }

    /**
     * set item's description.
     * @param newDescription item's new description
     */
    public void setDescription(String newDescription) {
        itemDescription = newDescription;
    }

    /**
     * get item's location.
     * @return item's curent location in room
     */
    public Point getXyLocation() {
        return itemXyLocation;
    }

    /**
     * set item's location.
     * @param newXyLocation item's new location
     */
    public void setXyLocation(Point newXyLocation) {
        itemXyLocation = newXyLocation;
    }

    /**
     * get which room the item is right now.
     * @return current room
     */
    public Room getCurrentRoom() {
        return itemRoom;
    }

    /**
     * set which room the item is in.
     * @param newCurrentRoom item's new room
     */
    public void setCurrentRoom(Room newCurrentRoom) {
        itemRoom = newCurrentRoom;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player newPlayer){
        player = newPlayer;
    }

}
