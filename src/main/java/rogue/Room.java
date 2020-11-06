package rogue;
import rogue.rogueExceptions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.awt.Point;

// import sun.invoke.empty.Empty;

import java.awt.Point;


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
   private Map<String, Integer> roomDoors = new HashMap<String, Integer>();
   private Map<String, Character> roomSymbols = new HashMap<String, Character>();
   private boolean playerInRoom = false;
   private boolean startRoom = false;

   // Default constructor
   public Room() {

   }

      // Required getter and setters below


   public int getWidth() {
      return roomWidth;
   }


   public void setWidth(int newWidth) {
      roomWidth = newWidth;
   }


   public int getHeight() {
      return roomHeight;
   }


   public void setHeight(int newHeight) {
      roomHeight = newHeight;
   }

   public int getId() {
      return roomId;
   }


   public void setId(int newId) {
      roomId = newId;
   }


   public ArrayList<Item> getRoomItems() {
      return roomItems;
   }


   public void setRoomItems(ArrayList<Item> newRoomItems) {
      roomItems = newRoomItems;
   }

   private int generateRandomInt(int min, int max){
      Random r = new Random();
      return r.nextInt((max - min) + 1) + min;
   }

   public void addItem(Item newItem) throws ImpossiblePositionException{
      double x = newItem.getXyLocation().getX();
      double y = newItem.getXyLocation().getY();

      boolean itemOverlap = false;

      for(Item item: roomItems){
         if(x == item.getXyLocation().getX() && y == item.getXyLocation().getY()){
            itemOverlap = true;
         }
      }

      if(x <= 0 || y <= 0 || x >= roomWidth-1 || y >= roomHeight-1 || itemOverlap == true){
         Point position = new Point();
         position.setLocation(generateRandomInt(1, roomWidth-2), generateRandomInt(1, roomHeight-2));
         newItem.setXyLocation(position);
         try{
            this.addItem(newItem);
         }catch (ImpossiblePositionException e){
            throw e;
         }
         throw new ImpossiblePositionException("Can't put "+ newItem.getName() + "(ID: "+ String.valueOf(newItem.getId())+ ") " + "here, changing position.");
      }else{
         roomItems.add(newItem);
      }
   }


   public Player getPlayer() {
      return roomPlayer;
   }


   public void setPlayer(Player newPlayer) {
      roomPlayer = newPlayer;
      playerInRoom = true;
   }

   public void makeStart() {
      startRoom = true;
      Point location = new Point();
      location.setLocation(1, 1);
      roomPlayer.setXyLocation(location);
      playerInRoom = true;
   }

   public boolean isStart() {
      if (startRoom) {
         return true;
      }
      return false;
   }

   public int getDoor(String direction) {
      return roomDoors.get(direction);
   }

   /*
   direction is one of NSEW
   location is a number between 0 and the length of the wall
   */

   public void setDoor(String direction, int location) {
      roomDoors.put(direction, location);
   }


   public boolean isPlayerInRoom() {
      if (playerInRoom) {
         return true;
      }
      return false;
   }

   public void setSymbols(Map<String, Character> symbols) {
      roomSymbols = symbols;
   }


   /**
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents
   * @return (String) String representation of how the room looks
   */
   public String displayRoom() {
      String disp = "";
      Point playerLocation;
      Point itemLocation;
      Boolean itemFound = false;

      for (int i = 0; i < roomHeight; i++) {
         for (int j = 0; j < roomWidth; j++) {
            if (i == 0 || i == roomHeight - 1) {

               if (roomDoors.containsKey("N") || roomDoors.containsKey("S")) {
                  if (roomDoors.containsKey("N") && roomDoors.get("N") == j && i == 0) {
                     disp += roomSymbols.get("DOOR");
                  } else if (roomDoors.containsKey("S") && roomDoors.get("S") == j && i == roomHeight - 1) {
                     disp += roomSymbols.get("DOOR");
                  } else {
                     disp += roomSymbols.get("NS_WALL");
                  }
               } else {
                  disp += roomSymbols.get("NS_WALL");
               }
            } else if (j == 0 || j == roomWidth - 1) {
               if (roomDoors.containsKey("W") || roomDoors.containsKey("E")) {
                  if (roomDoors.containsKey("W")  && roomDoors.get("W") == i && j == 0) {
                     disp += roomSymbols.get("DOOR");
                  } else if (roomDoors.containsKey("E")  && roomDoors.get("E") == i && j == roomWidth - 1) {
                     disp += roomSymbols.get("DOOR");
                  } else {
                     disp += roomSymbols.get("EW_WALL");
                  }
               } else {
                  disp += roomSymbols.get("EW_WALL");
               }
            } else {
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
            }
         }
         disp += "\n";
      }
   return disp;
   }
}
