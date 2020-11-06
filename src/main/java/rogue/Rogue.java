package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import java.awt.Point;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import rogue.rogueExceptions.ImpossiblePositionException;
import rogue.rogueExceptions.NotEnoughDoorsException;


public class Rogue {
    private Player roguePlayer;
    private RogueParser parser;
    private ArrayList<Room> rogueRooms = new ArrayList<>();
    private ArrayList<Item> rogueItems = new ArrayList<>();
    private ArrayList<Door> rogueDoors = new ArrayList<>();
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

        
        Map <String,String> doorInfo = parser.nextDoor();
        while(doorInfo != null){
            connectDoors(doorInfo);
            doorInfo = parser.nextDoor();
        }
        
        addItemToRooms();
        verifyRooms();


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

    public ArrayList<Door> getDoors() {
        return rogueDoors;
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
                Door door = new Door();
                door.addLocation(Integer.parseInt(toAdd.get(letter).toString()), letter);
                door.connectRoom(room);
                rogueDoors.add(door);
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
            if(toAdd.get("room") != null && room.getId() == Integer.parseInt(toAdd.get("room").toString())){
                Item item = new Item();
                item.setCurrentRoom(room);
                item.setId(Integer.parseInt(toAdd.get("id").toString()));
                item.setName(toAdd.get("name").toString());
                item.setType(toAdd.get("type").toString());
                item.setDescription(toAdd.get("description").toString());
                Point position = new Point();
                position.setLocation(Integer.parseInt(toAdd.get("x").toString()), Integer.parseInt(toAdd.get("y").toString()));
                item.setXyLocation(position);

                rogueItems.add(item);
            }
        }
    }

    public void connectDoors(Map<String,String> toAdd){
        
        for(Room room: rogueRooms){
            if(Integer.parseInt(toAdd.get("id").toString()) == room.getId()){
                for (Room room2: rogueRooms){
                    String direction[] = {"N","S","E","W"};
                    for(String letter: direction){
                        if(Integer.parseInt(toAdd.get(letter+"_Con").toString()) == room2.getId()){
                            for(Door door: rogueDoors){
                                if(door.getConnectedRooms().get(0).getId() == room.getId() && room.getDoors().contains(door) == false ){
                                    door.connectRoom(room2);
                                    room.addDoor(door);
                                }
                            }
                        }
                    }
                }
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

    private int generateRandomInt(int min, int max){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
     }

    public void verifyRooms(){
        System.out.println(rogueDoors);
        for (Room room: rogueRooms){
            try{
                room.verifyRoom();
            }catch(NotEnoughDoorsException e){
                System.out.println(e.getMessage());
                System.out.println("Adding a door");
                Door newDoor = new Door();
                
                newDoor.connectRoom(room);
                String direction[] = {"N","S","E","W"};
                String randomDirection = direction[generateRandomInt(0, 3)];
                if(randomDirection == "N" || randomDirection == "S"){
                    newDoor.addLocation(generateRandomInt(1, room.getWidth()-2), randomDirection);
                }
                if(randomDirection == "E" || randomDirection == "W"){
                    newDoor.addLocation(generateRandomInt(2, room.getHeight()-3), randomDirection);
                }

                for (Room room2: rogueRooms){
                    if(room2.getDoors().size() < 4 && room2.getId() != room.getId()){
                        newDoor.connectRoom(room2);
                    }
                }
                if(newDoor.getConnectedRooms().size() < 2){
                    System.out.println("Dungeon file cannot be used!");
                    System.exit(0);
                }
                room.addDoor(newDoor);
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
