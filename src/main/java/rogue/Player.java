package rogue;
import java.awt.Point;
/**
 * The player character
 */
public class Player {

    private String playerName;
    private Point playerXyLocation;
    private Room playerRoom;


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
}
