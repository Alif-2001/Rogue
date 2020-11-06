package rogue;
import java.util.ArrayList;

public class Door {
    ArrayList<Room> connectedRooms = new ArrayList<>();
    public Door(){

    }

    public void connectRoom(Room r){
        if(connectedRooms.size()<3){
            connectedRooms.add(r);
        }
    }

    public ArrayList<Room> getConnectedRooms(){
        return connectedRooms;
    }

    public Room getOtherRoom(Room currentRoom){
        for(Room room: connectedRooms){
            if (currentRoom.getId() != room.getId()){
                return room;
            }
        }
        return null;
    }
}
