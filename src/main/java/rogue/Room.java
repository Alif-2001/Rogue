package rogue;
import rogue.rogueExceptions.ImpossiblePositionException;
import rogue.rogueExceptions.NoSuchItemException;
import rogue.rogueExceptions.NotEnoughDoorsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;

// import sun.invoke.empty.Empty;


/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room {

   private int roomWidth;
   private int roomHeight;
   private int roomId;
   private ArrayList<Item> roomItems = new ArrayList<Item>();
   private Player roomPlayer = new Player();
   private ArrayList<Door> roomDoors = new ArrayList<Door>();
   private Map<String, Character> roomSymbols = new HashMap<String, Character>();
   private boolean playerInRoom = false;
   private boolean startRoom = false;
   private Rogue thisGame;

   /**
    * This method is used as a constructor.
    */
   public Room() {

   }

   // Required getter and setters below

   /**
    * This method is used to get the width of the room.
    * @return room's width
    */
   public int getWidth() {
      return roomWidth;
   }

   /**
    * This method is used to set the width of the room.
    * @param newWidth room's new width
    */
   public void setWidth(int newWidth) {
      roomWidth = newWidth;
   }

   /**
    * This method is used to get the room's height.
    * @return room's height
    */
   public int getHeight() {
      return roomHeight;
   }

   /**
    * This method is used to set the Height of the room.
    * @param newHeight room's new Height
    */
   public void setHeight(int newHeight) {
      roomHeight = newHeight;
   }

   /**
    * This method is used to get the room's ID.
    * @return room's ID
    */
   public int getId() {
      return roomId;
   }

   /**
    * This method is used to set the room's ID.
    *  @param newId room's new ID
    */
   public void setId(int newId) {
      roomId = newId;
   }

   /**
    * This method is used to get all the items in the room.
    * @return (ArrayList) all the items in the room
    */
   public ArrayList<Item> getRoomItems() {
      return roomItems;
   }

   /**
    * This method is used to set all the items in the room.
    * @param newRoomItems a list of the items to put in the room
    */
   public void setRoomItems(ArrayList<Item> newRoomItems) {
      roomItems = newRoomItems;
   }

   /**
    * This method is used to set the game in which the room is.
    * @param rogue the game that contains this room
    */
   public void setRogue(Rogue rogue) {
      thisGame = rogue;
   }

   /**
    * This method is used to add an item to the room.
    * @param newItem the new item to add to the room
    * @throws ImpossiblePositionException if the item can't be placed at this place
    * @throws NoSuchItemException if the item doesn't exist in the game
    */
   public void addItem(Item newItem) throws ImpossiblePositionException, NoSuchItemException {
      double x = newItem.getXyLocation().getX();
      double y = newItem.getXyLocation().getY();

      boolean itemOverlap = false;



      for (Item item: roomItems) {
         if (x == item.getXyLocation().getX() && y == item.getXyLocation().getY()) {
            itemOverlap = true;
         }
         if (!thisGame.getItems().contains(item)) {
            NoSuchItemException e = new NoSuchItemException("This Item does not exist in the room!");
            e.setMissingItem(item);
            throw(e);
         }
      }

      if (x <= 0 || y <= 0 || x >= roomWidth - 1 || y >= roomHeight - 1 || itemOverlap) {
         throw new ImpossiblePositionException("Changing " + newItem.getName() + " ID: "
                                                + String.valueOf(newItem.getId()) + " " + "position.");
      } else {
         roomItems.add(newItem);
      }
   }

   /**
    * This method is used to add a single item to the room.
    * @param newItem the new item to add
    */
   public void addSingleItem(Item newItem) {
      roomItems.add(newItem);
   }

   /**
    * This method is used to remove a single item from the room.
    * @param remItem the item to remove from the room
    */
   public void removeItem(Item remItem) {
      roomItems.remove(remItem);
   }

   /**
    * This method is used to get the player in the room.
    * @return (Player) the player in the room
    */
   public Player getPlayer() {
      return roomPlayer;
   }

   /**
    * This method is used to set the player who is in the room.
    * @param newPlayer the new player to put in the room
    */
   public void setPlayer(Player newPlayer) {
      roomPlayer = newPlayer;
      playerInRoom = true;
   }

   /**
    * This method is used to set this room as start.
    */
   public void makeStart() {
      startRoom = true;
      Point location = new Point();
      location.setLocation(1, 1);
      roomPlayer.setXyLocation(location);
      roomPlayer.setCurrentRoom(this);
      playerInRoom = true;
   }

   /**
    * This method is used to know if the room is the starting room or not.
    * @return (boolean) is the room start
    */
   public boolean isStart() {
      if (startRoom) {
         return true;
      }
      return false;
   }

   /**
    * This method is used to get one of the room's the door's loaction according to the direction NSEW.
    * @param direction one of NSEW
    * @return (int) door's position on the wall
    */
   public int getDoorLocation(String direction) {
      for (Door door: roomDoors) {
         if (door.getPosition(direction) != -1) {
            return door.getPosition(direction);
         }
      }
      return -1;
   }

   /**
    * This method is used to get the door at a given direction NSEW.
    * @param direction one of NSEW
    * @return (Door) the door in that direction
    */
   public Door getDoor(String direction) {
      for (Door door: roomDoors) {
         if (door.getPosition(direction) != -1) {
            return door;
         }
      }
      return null;
   }

   /**
    * This method is used to get all the doors in the room.
    * @return (ArrayList) list of all the doors.
    */
   public ArrayList<Door> getDoors() {
      return roomDoors;
   }

   /**
    * This method is used to add a door to the room.
    * @param newDoor new door to be added
    */
   public void addDoor(Door newDoor) {
      roomDoors.add(newDoor);
   }

   /**
    * This method is used to know if the player is in the room.
    * @return (boolean) is player in room
    */
   public boolean isPlayerInRoom() {
      if (playerInRoom) {
         return true;
      }
      return false;
   }

   /**
    * This method is used to set all the symbols to display the room.
    * @param symbols information on how to display
    */
   public void setSymbols(Map<String, Character> symbols) {
      roomSymbols = symbols;
   }

   /**
    * This method is used to verify if the room has everything (doors, items) in right place.
    * @return (boolean) is everything alright
    * @throws NotEnoughDoorsException if the room doesn't have any doors
    */
   public boolean verifyRoom() throws NotEnoughDoorsException {

      for (Item item: roomItems) {
         double x = item.getXyLocation().getX();
         double y = item.getXyLocation().getY();

         if (x <= 0 || y <= 0 || x >= roomWidth - 1 || y >= roomHeight - 1) {
            return false;
         }
      }

      if (roomDoors.size() == 0) {
         throw new NotEnoughDoorsException("Not enough Doors in room: " + String.valueOf(roomId));
      }

      String[] direction = {"N", "S", "E", "W"};
      for (String letter: direction) {
         if (letter == "N" || letter == "S") {
            if (getDoorLocation(letter) <= 0 || getDoorLocation(letter) > roomWidth - 1) {
               return false;
            }
         }
         if (letter == "E" || letter == "W") {
            if (getDoorLocation(letter) <= 1 || getDoorLocation(letter) > roomHeight - 2) {
               return false;
            }
         }
      }


      return true;
   }


   private String addDoorToDisplay(int i, int j) {
      String disp = "";
      if (i == 0 || i == roomHeight - 1) {

         if (getDoorLocation("N") != -1 || getDoorLocation("S") != -1) {
            if (getDoorLocation("N") != -1 && getDoorLocation("N") == j && i == 0) {
               disp += roomSymbols.get("DOOR");
            } else if (getDoorLocation("S") != -1 && getDoorLocation("S") == j && i == roomHeight - 1) {
               disp += roomSymbols.get("DOOR");
            } else {
               disp += roomSymbols.get("NS_WALL");
            }
         } else {
            disp += roomSymbols.get("NS_WALL");
         }
      } else if (j == 0 || j == roomWidth - 1) {
         if (getDoorLocation("W") != -1 || getDoorLocation("E") != -1) {
            if (getDoorLocation("W") != -1  && getDoorLocation("W") == i && j == 0) {
               disp += roomSymbols.get("DOOR");
            } else if (getDoorLocation("E") != -1  && getDoorLocation("E") == i && j == roomWidth - 1) {
               disp += roomSymbols.get("DOOR");
            } else {
               disp += roomSymbols.get("EW_WALL");
            }
         } else {
            disp += roomSymbols.get("EW_WALL");
         }
      }
      return disp;
   }

   private String addItemToDisplay(int i, int j) {
      String disp = "";
      Point playerLocation;
      Point itemLocation;
      boolean itemFound = false;
      playerLocation = roomPlayer.getXyLocation();
      if (playerLocation != null && i == playerLocation.getY() && j == playerLocation.getX()) {
         disp += roomSymbols.get("PLAYER");
      } else {
         Item toPut = new Item();
         for (Item item : roomItems) {
            itemLocation = item.getXyLocation();
            if (i == itemLocation.getY() && j == itemLocation.getX()) {
               itemFound = true;
               toPut = item;
            }
         }

         if (itemFound) {
            disp += roomSymbols.get(toPut.getType().toUpperCase());
            itemFound = false;
         } else {
            disp += roomSymbols.get("FLOOR");
         }
      }
      return disp;
   }

   /**
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents.
   * @return (String) String representation of how the room looks
   */
   public String displayRoom() {
      String disp = "";

      for (int i = 0; i < roomHeight; i++) {
         for (int j = 0; j < roomWidth; j++) {
            String door = addDoorToDisplay(i, j);
            if (door != "") {
               disp += door;
            } else {
               disp += addItemToDisplay(i, j);
            }

         }
         disp += "\n";
      }
   return disp;
   }

}
