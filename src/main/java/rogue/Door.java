package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Door {
    private ArrayList<Room> connectedRooms = new ArrayList<>();
    
    public Door(){

    }

    public void connectRoom(Room r){
        if(connectedRooms.size()<2){
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
