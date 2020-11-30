package rogue;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.Random;

import java.io.Serializable;

import java.awt.Point;
import java.util.Map;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;


public class Rogue implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 3967471703202977097L;
    private Player roguePlayer;
    private Room currentRoom;
    private ArrayList<Room> rogueRooms = new ArrayList<>();
    private ArrayList<Item> rogueItems = new ArrayList<>();
    private ArrayList<Door> rogueDoors = new ArrayList<>();
    private Map<String, Character> rogueSymbols = new HashMap<String, Character>();

    public static final char UP = 'i';
    public static final char DOWN = 'k';
    public static final char LEFT = 'j';
    public static final char RIGHT = 'l';
    public static final char TOSS = 't';
    public static final char EAT = 'e';
    public static final char WEAR = 'w';
    private String nextDisplay = "";

    /**
     * default parameter
     */
    public Rogue(){
        roguePlayer = new Player("");
    }

    /**
     * this method is used to initiate a rogue game.
     * @param theDungeonInfo info read from the json files
     */
    public Rogue(RogueParser theDungeonInfo) {

        

        Map<String, String> roomInfo = theDungeonInfo.nextRoom();
        while (roomInfo != null) {
            addRoom(roomInfo);
            roomInfo = theDungeonInfo.nextRoom();
        }

        Map<String, String> itemInfo = theDungeonInfo.nextItem();
        while (itemInfo != null) {
            addItem(itemInfo);
            itemInfo = theDungeonInfo.nextItem();
        }


        Map<String, String> doorInfo = theDungeonInfo.nextDoor();
        while (doorInfo != null) {
            connectDoors(doorInfo);
            doorInfo = theDungeonInfo.nextDoor();
        }

        //addItemToRooms();
        verifyRooms();
        addSymbols(theDungeonInfo.getSymbols());

    }

    /**
     * this method is used to get all the rooms in the game.
     * @return list of rooms in the game
     */
    public ArrayList<Room> getRooms() {
        return rogueRooms;

    }

    /**
     * this method is used to get all the items in the game.
     * @return list of all the items in the game
     */
    public ArrayList<Item> getItems() {
        return rogueItems;

    }

    /**
     * this method is used to get the player currently playing the game.
     * @return the player in the game
     */
    public Player getPlayer() {
        return roguePlayer;

    }

    /**
     * this method is used to set the current player's name.
     * @param player the player's new name
     */

    public void setPlayer(Player player) {
        roguePlayer.setName(player.getName());
    }

    /**
     * this method is used to get all the doors in the game.
     * @return a list of all the doors.
     */
    public ArrayList<Door> getDoors() {
        return rogueDoors;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    /**
     * this method is used to create rooms and add them to the list of rooms in the game.
     * @param toAdd map containing the id, the start value, the hight, the width, and doors in a single room.
     */
    public void addRoom(Map<String, String> toAdd) {

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

        String[] direction = {"N", "S", "E", "W"};

        for (String letter: direction) {
            if(toAdd.get(letter) != null){
                if (Integer.parseInt(toAdd.get(letter).toString()) != -1) {
                    Door door = new Door();
                    door.setLocation(Integer.parseInt(toAdd.get(letter).toString()), letter);
                    door.connectRoom(room);
                    rogueDoors.add(door);
                }
            }
        }


        if (toAdd.get("start") != null && Boolean.parseBoolean(toAdd.get("start").toString())) {
            currentRoom = room;
            room.makeStart();
            roguePlayer = room.getPlayer();
        }
        room.setRogue(this);
        rogueRooms.add(room);
    }

    private Item createItem(String type){

        if(type.equals("Food")){
            Item food = new Food();
            return food;
        }else if(type.equals("SmallFood")){
            Item smallFood = new SmallFood();
            return smallFood;
        }else if(type.equals("Clothing")){
            Item clothing = new Clothing();
            return clothing;
        }else if(type.equals("Potion")){
            Item potion = new Potion();
            return potion;
        }else if(type.equals("Ring")){
            Item ring = new Ring();
            return ring;
        }else{
            Item item = new Item();
            return item;
        }
    }
    /**
     * this method is used to add items to the game.
     * @param toAdd map containing the id, the position, the room, the name, and the type of the item.
     */
    public void addItem(Map<String, String> toAdd) {
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
        for (Room room: rogueRooms) {
            if (toAdd.get("room") != null && room.getId() == Integer.parseInt(toAdd.get("room").toString())) {
                Item item = createItem(toAdd.get("type").toString());
                item.setCurrentRoom(room);
                item.setId(Integer.parseInt(toAdd.get("id").toString())); 
                item.setName(toAdd.get("name").toString());
                item.setType(toAdd.get("type").toString());
                item.setDescription(toAdd.get("description").toString());
                Point position = new Point();
                Integer x = Integer.parseInt(toAdd.get("x").toString());
                Integer y = Integer.parseInt(toAdd.get("y").toString());
                position.setLocation(x, y);
                item.setXyLocation(position);
                rogueItems.add(item);
                addItemToRooms(item);
            }
        }
    }

    private Room getRoomById(int id){
        for(Room room: rogueRooms){
            if(room.getId() == id){
                return room;
            }
        }
        return null;
    }

    /**
     * this method is used to connect rooms through doors.
     * @param toAdd map containing which room doors connect to others
     */
    public void connectDoors(Map<String, String> toAdd) {
        ArrayList<Door> doorsAdded = new ArrayList<Door>();
        for (Room room: rogueRooms) {
            doorsAdded.clear();
            if (Integer.parseInt(toAdd.get("id").toString()) == room.getId()) {
                for(Door door: rogueDoors) {
                    if(door.getConnectedRooms().get(0).getId() == room.getId() && !(doorsAdded.contains(door))){
                        Integer connectRoom = Integer.parseInt(toAdd.get(door.getDirection()+ "_Con").toString());
                        Room room2 = getRoomById(connectRoom);
                        door.connectRoom(room2);
                        room.addDoor(door);
                        doorsAdded.add(door);
                    }
                }
            }
        }
    }

    /**
     * this method is used to add items of the game to the respective rooms.
     */
    public void addItemToRooms(Item item) {
        for (Room room: rogueRooms) {
            if (item.getCurrentRoom().getId() == room.getId()) {
                try {
                    room.addItem(item);
                } catch (ImpossiblePositionException e) {
                    Point position = new Point();
                    int randX = generateRandomInt(1, room.getWidth() - 2);
                    int randY = generateRandomInt(1, room.getHeight() - 2);
                    position.setLocation(randX, randY);
                    item.setXyLocation(position);
                    room.addSingleItem(item);
                } catch (NoSuchItemException ex) {
                    System.out.println(ex.getMessage());
                    room.removeItem(ex.getItem());
                }
            }
        }
    }

    /**
     * this method is used to generate integer between the given intgers.
     * @param min smallest number
     * @param max biggest number
     * @return a randomn number between min and max
     */
    public int generateRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * this method is used to verify the rooms before starting the game (items, door, and player in appropriate places).
     */
    public void verifyRooms() {
        int totalDoors = 2 + 2; //two doors on N and S, two on E and W.
        for (Room room: rogueRooms) {
            try {
                room.verifyRoom();
            } catch (NotEnoughDoorsException e) {
                System.out.println(e.getMessage());
                Door newDoor = new Door();

                newDoor.connectRoom(room);
                String[] direction = {"N", "S", "E", "W"};
                String randomDirection = direction[generateRandomInt(0, (direction.length) - 1)];
                if (randomDirection == "N" || randomDirection == "S") {
                    newDoor.setLocation(generateRandomInt(1, room.getWidth() - 2), randomDirection);
                }
                if (randomDirection == "E" || randomDirection == "W") {
                    newDoor.setLocation(generateRandomInt(2, room.getHeight() - 2 - 1), randomDirection);
                }

                for (Room room2: rogueRooms) {
                    if (room2.getDoors().size() < totalDoors && room2.getId() != room.getId()) {
                        newDoor.connectRoom(room2);
                    }
                }
                if (newDoor.getConnectedRooms().size() < 2) {
                    System.out.println("Dungeon file cannot be used!");
                    System.exit(0);
                }
                room.addDoor(newDoor);
                rogueDoors.add(newDoor);
            }
        }
    }

    /**
     * this method is used to set charchters that are going to build the game.
     * @param toAdd map containing the charachter and what it corresponds to
     */
    public void addSymbols(Map<String, Character> toAdd) {
        rogueSymbols = toAdd;
    }

    /**
     * this method is used to display all the rooms in the game.
     * @return a String that represents all the rooms
     */
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

    /**
     * this method is used to check if there's a door at a given location.
     * @param newPosition the new position
     * @return the direction at which the door is situated (NSEW)
     */
    private String checkDoor(Point newPosition) {
        double x;
        double y;
        x = newPosition.getX();
        y = newPosition.getY();

        String direction = null;
        if (x == 0) {
            if (currentRoom.getDoorLocation("W") == y) {
                direction = "W";
            }
        } else if (x == currentRoom.getWidth() - 1) {
            if (currentRoom.getDoorLocation("E") == y) {
                direction = "E";
            }
        } else if (y == 0) {
            if (currentRoom.getDoorLocation("N") == x) {
                direction = "N";
            }
        } else if (y == currentRoom.getHeight() - 1) {
            if (currentRoom.getDoorLocation("S") == x) {
                direction = "S";
            }
        }
        return direction;
    }

    /**
     * this method is used to check if there's a wall at a given location.
     * @param newPosition the new Position
     * @return a boolean that tells if ther's a wall or not
     */
    private boolean checkWall(Point newPosition) {
        double x;
        double y;
        x = newPosition.getX();
        y = newPosition.getY();

        boolean wall = false;
        if (x == 0) {
            if (currentRoom.getDoorLocation("W") != y) {
                wall = true;
            }
        } else if (x == currentRoom.getWidth() - 1) {
            if (currentRoom.getDoorLocation("E") != y) {
                wall = true;
            }
        } else if (y == 0) {
            if (currentRoom.getDoorLocation("N") != x) {
                wall = true;
            }
        } else if (y == currentRoom.getHeight() - 1) {
            if (currentRoom.getDoorLocation("S") != x) {
                wall = true;
            }
        }
        return wall;
    }

    /**
     * this method is used to check if there's an item at the given location.
     * @param newPosition the new position
     * @return which item the player is standing om
     */
    private Item checkItem(Point newPosition) {
        double x;
        double y;
        x = newPosition.getX();
        y = newPosition.getY();

        for (Item item: currentRoom.getRoomItems()) {
            if (item.getXyLocation().getX() == x && item.getXyLocation().getY() == y) {
                return item;
            }
        }
        return null;
    }

    /**
     * this method is used to get the player's new position according to their move.
     * @param input direction charachter
     * @param curPosition player's current position
     * @return player's new postion
     */
    private Point getNewPosition(char input, Point curPosition) {
        Point newPosition = new Point();

        if (input == UP) {
            newPosition.setLocation(curPosition.getX(), curPosition.getY() - 1);
        } else if (input == DOWN) {
            newPosition.setLocation(curPosition.getX(), curPosition.getY() + 1);
        } else if (input == LEFT) {
            newPosition.setLocation(curPosition.getX() - 1, curPosition.getY());
        } else if (input == RIGHT) {
            newPosition.setLocation(curPosition.getX() + 1, curPosition.getY());
        }
        return newPosition;
    }

    /**
     * this method assesses a move to ensure it is valid.
     * If the move is valid, then the display resulting from the move is calculated
     * and set as the 'nextDisplay' (probably a private member variable).
     * If the move is not valid, an InvalidMoveException is thrown and the nextDisplay is unchanged
     * @param input direction in which the player wants to go
     * @return a string to print on each move
     * @throws InvalidMoveException if the move is invalid
     */
    public String makeMove(char input) throws InvalidMoveException {
        String toPrint = null;
        currentRoom = roguePlayer.getCurrentRoom();
        if (input != UP && input != DOWN && input != LEFT && input != RIGHT) {
            throw new InvalidMoveException("I don't know this move");
        }

        Point curPosition = roguePlayer.getXyLocation();
        Point newPosition = getNewPosition(input, curPosition);
        boolean doorFound = false;
        Item itemFound = new Item();
        String doorDir = checkDoor(newPosition);
        boolean wall = checkWall(newPosition);
        itemFound = checkItem(newPosition);
        if (wall) {
            newPosition = curPosition;
            throw new InvalidMoveException("There's a wall here!");
        }
        double x;
        double y;
        x = newPosition.getX();
        y = newPosition.getY();
        if (doorDir != null) {
            doorFound = true;
        }
        if (doorFound) {
            x = 1;
            y = 1;
            currentRoom = rogueDoors.get(rogueDoors.indexOf(currentRoom.getDoor(doorDir))).getOtherRoom(currentRoom);
        }
        newPosition.setLocation(x, y);
        roguePlayer.setXyLocation(newPosition);
        roguePlayer.setCurrentRoom(currentRoom);
        if (itemFound != null) {
            currentRoom.removeItem(itemFound);
            roguePlayer.pickItem(itemFound);
            toPrint = "Picked " + itemFound.getName();
        } else {
            toPrint = "That's a lovely move: " +  Character.toString(input);
        }
        currentRoom.setPlayer(roguePlayer);
        currentRoom.setSymbols(rogueSymbols);
        return toPrint;
    }

    /**
     * this method is used to get the next string to be displayed on the screen.
     * @return the new/next display
     */
    public String getNextDisplay() {
        currentRoom.setPlayer(roguePlayer);
        currentRoom.setSymbols(rogueSymbols);
        nextDisplay = currentRoom.displayRoom();
        return nextDisplay;
    }
}
