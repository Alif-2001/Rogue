package rogue;
import java.util.ArrayList;

public class Door {
    private ArrayList<Room> connectedRooms = new ArrayList<>();
    private int position;
    private String direction;

    /**
     * Door constructor.
     */
    public Door() {

    }

    /**
     * Set the door's location.
     * @param newPosition poisition on the wall
     * @param newDirection direction, one of NSEW
     */
    public void setLocation(int newPosition, String newDirection) {
        position = newPosition;
        direction = newDirection;
    }

    /**
     * Get the position on the wall according to the direction (NSEW).
     * @param dir direction, one of NSEW
     * @return the position on the wall
     */
    public int getPosition(String dir) {
        if (dir == direction) {
            return position;
        }
        return -1;
    }

    /**
     * coonect a second room to the door.
     * @param r room to link
     */
    public void connectRoom(Room r) {
        if (connectedRooms.size() < 2) {
            connectedRooms.add(r);
        }
    }

    /**
     * get the two rooms the door links.
     * @return an Array of rooms the door connects
     */
    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    /**
     * get the other room based on which room you are in right now.
     * @param currentRoom the room we are in right now
     * @return the room on the other side of the door
     */
    public Room getOtherRoom(Room currentRoom) {
        for (Room room: connectedRooms) {
            if (currentRoom.getId() != room.getId()) {
                return room;
            }
        }
        return null;
    }
}
