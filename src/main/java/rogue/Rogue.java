package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Point;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import rogue.rogueExceptions.ImpossiblePositionException;


public class Rogue {
    private Player roguePlayer;
    private RogueParser parser;
    private ArrayList<Room> rogueRooms = new ArrayList<>();
    private ArrayList<Item> rogueItems = new ArrayList<>();
    private Map<String, Character> rogueSymbols = new HashMap<String, Character>();


    public Rogue(RogueParser theDungeonInfo){

        parser = theDungeonInfo;

        Map <String, String> roomInfo = parser.nextRoom();
        while(roomInfo !=null){
            addRoom(roomInfo);
            roomInfo = parser.nextRoom();
        }

        Map <String,String> itemInfo = parser.nextItem();
        while(itemInfo !=null){
            addItem(itemInfo);
            itemInfo = parser.nextItem();
        }

        /*
        Map <String,String> itemLocationInfo = parser.nextItemLocation();
        while(itemLocationInfo !=null){
            //System.out.println(itemLocationInfo);
            itemLocationInfo = parser.nextItem();
        }
        */

        

        addItemToRooms();
        addSymbols(parser.getSymbols());

    }

    public ArrayList<Room> getRooms() {
        return rogueRooms;

    }

    public ArrayList<Item> getItems() {
        return rogueItems;

    }
    public Player getPlayer() {
        return roguePlayer;

    }


    public void addRoom(Map<String, String> toAdd){

        /* allocate memory for a room object
            look up the attributes of the room in the map
            set the fields for the room object you just created using the values you looked up
                
            int theWidth = someow_convert_to_int(toAdd.get("width"));
            theRoom.setWidth(theWidth)
            do this for every attribute

            add the room object to the list of dungeon rooms
            myRooms.add(theRoom);

            room fields in the hashmap include:
            id
            start
            height
            width
            N
            S
            E
            W

            directions are -1 if there is no door there.

            */

        Room room = new Room();

        room.setHeight(Integer.parseInt(toAdd.get("height").toString()));
        room.setWidth(Integer.parseInt(toAdd.get("width").toString()));
        room.setId(Integer.parseInt(toAdd.get("id").toString()));

        String direction[] = {"N","S","E","W"};

        for(String letter: direction){
            if (Integer.parseInt(toAdd.get(letter).toString()) != -1){
                room.setDoor(letter, Integer.parseInt(toAdd.get(letter).toString()));
            }
        }

        if(Boolean.parseBoolean(toAdd.get("start").toString())){
            room.makeStart();
        }

        rogueRooms.add(room);
    }

    public void addItem(Map<String,String> toAdd){
        /* 
        allocate memory for the item object
        look up the attributes of the item in the map
        set the fields in the object you just created using those values
        add the item object to the list of items in the dungeon
        add the item to the room it is currently located 
            by creating a void addItem(Item toAdd) method in Room.java and using it

        fields in the item map include

        id
        x
        y
        room
        name
        type


        */

        for(Room room: rogueRooms){
            if(room.getId() == Integer.parseInt(toAdd.get("room").toString())){
                Item item = new Item();
                item.setCurrentRoom(room);
                item.setId(Integer.parseInt(toAdd.get("id").toString()));
                item.setName(toAdd.get("name").toString());
                item.setType(toAdd.get("type").toString());
                Point position = new Point();
                position.setLocation(Integer.parseInt(toAdd.get("x").toString()), Integer.parseInt(toAdd.get("y").toString()));
                item.setXyLocation(position);

                rogueItems.add(item);
            }
        }
    }

    public void addItemToRooms(){
    
        for(Item item: rogueItems){
            for(Room room: rogueRooms){
                if(item.getCurrentRoom().getId() == room.getId()){
                    try{
                        room.addItem(item);
                    }catch(ImpossiblePositionException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            
        }
    }

    public void addSymbols(Map<String, Character> toAdd){
        rogueSymbols = toAdd;
    }

    public String displayAll() {
        //creates a string that displays all the rooms in the dungeon
        String disp = "";
        for (Room room: rogueRooms) {
            room.setSymbols(rogueSymbols);
            disp += "<---- [Room " + room.getId() + "] ---->\n";
            if (room.isStart()) {
                disp += "- Starting Room\n";
            }
            disp += room.displayRoom();
            
        }
        return disp;
    }
}
