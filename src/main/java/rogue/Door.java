package rogue;
import java.util.ArrayList;

public class Door {
    private ArrayList<Room> connectedRooms = new ArrayList<>();
    private int position;
    private String direction;

    public Door() {

    }

    public void addLocation(int newPosition, String newDirection) {
        position = newPosition;
        direction = newDirection;
    }

    public int getPosition(String dir) {
        if (dir == direction) {
            return position;
        }
        return -1;
    }

    public void connectRoom(Room r) {
        if (connectedRooms.size() < 2) {
            connectedRooms.add(r);
        }
    }

    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public Room getOtherRoom(Room currentRoom) {
        for (Room room: connectedRooms) {
            if (currentRoom.getId() != room.getId()) {
                return room;
            }
        }
        return null;
    }
}
