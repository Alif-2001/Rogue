package rogue;
import rogue.rogueExceptions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
   private ArrayList<Door> roomDoors = new ArrayList<Door>();
   private Map<String, Character> roomSymbols = new HashMap<String, Character>();
   private boolean playerInRoom = false;
   private boolean startRoom = false;
   private Rogue thisGame;

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

   public void setRogue(Rogue rogue){
      thisGame = rogue;
   }


   public void addItem(Item newItem) throws ImpossiblePositionException, NoSuchItemException{
      double x = newItem.getXyLocation().getX();
      double y = newItem.getXyLocation().getY();

      boolean itemOverlap = false;

      

      for(Item item: roomItems){
         if(x == item.getXyLocation().getX() && y == item.getXyLocation().getY()){
            itemOverlap = true;
         }
         if (!thisGame.getItems().contains(item)){
            NoSuchItemException e = new NoSuchItemException("This Item does not exist in the room!");
            e.setMissingItem(item);
            throw(e);
         }
      }

      if(x <= 0 || y <= 0 || x >= roomWidth-1 || y >= roomHeight-1 || itemOverlap == true){
         ImpossiblePositionException e2 = new ImpossiblePositionException("Can't put "+ newItem.getName() + "(ID: "+ String.valueOf(newItem.getId())+ ") " + "here, changing position.");
         e2.setItem(newItem);
         throw (e2);
      }else{
         roomItems.add(newItem);
      }
   }

   public void addSingleItem(Item newItem){
      roomItems.add(newItem);
   }

   public void removeItem(Item remItem){
      roomItems.remove(remItem);
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
      roomPlayer.setCurrentRoom(this);
      playerInRoom = true;
   }

   public boolean isStart() {
      if (startRoom) {
         return true;
      }
      return false;
   }

   public int getDoorLocation(String direction) {
      for(Door door: roomDoors){
         if(door.getPosition(direction)!=-1){
            return door.getPosition(direction);
         }
      }
      return -1;
   }

   public ArrayList<Door> getDoors(){
      return roomDoors;
   }

   /*
   direction is one of NSEW
   location is a number between 0 and the length of the wall
   */

   public void addDoor(Door newDoor) {
      roomDoors.add(newDoor);
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

   public boolean verifyRoom() throws NotEnoughDoorsException{

      for(Item item: roomItems){
         double x = item.getXyLocation().getX();
         double y = item.getXyLocation().getY();

         if(x <= 0 || y <= 0 || x >= roomWidth-1 || y >= roomHeight-1){
            return false;
         }
      }

      if(roomDoors.size() == 0){
         throw new NotEnoughDoorsException("Not enough Doors in room: " + String.valueOf(roomId));
      }

      String direction[] = {"N","S","E","W"};
      for (String letter: direction){
         if(letter == "N" || letter == "S"){
            if(getDoorLocation(letter) <= 0 || getDoorLocation(letter) > roomWidth-1){
               return false;
            }
         }
         if(letter == "E" || letter == "W"){
            if(getDoorLocation(letter) <= 1 || getDoorLocation(letter) > roomHeight-2){
               return false;
            }
         }
      }


      return true;
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
