package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// import sun.invoke.empty.Empty;

import java.awt.Point;


/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room  {

   private int roomWidth;
   private int roomHeight;
   private int roomId;
   private ArrayList<Item> roomItems = new ArrayList<Item>();
   private Player roomPlayer = new Player();
   private Map<String, Integer> roomDoors = new HashMap<String, Integer>();
   private Map<String, String> roomSymbols = new HashMap<String, String>();
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


   public Player getPlayer() {
      return roomPlayer;
   }


   public void setPlayer(Player newPlayer) {
      roomPlayer = newPlayer;
      playerInRoom = true;
   }

   public void makeStart(){
      startRoom = true;
      Point location = new Point();
      location.setLocation(1, 1);
      roomPlayer.setXyLocation(location);
      playerInRoom = true;
   }
   
   public boolean isStart(){
      if(startRoom == true){
         return true;
      }else{
         return false;
      }
   }

   public int getDoor(String direction){
      return roomDoors.get(direction);
   }

   /*
   direction is one of NSEW
   location is a number between 0 and the length of the wall
   */

   public void setDoor(String direction, int location){
      roomDoors.put(direction, location);
   }


   public boolean isPlayerInRoom() {
      if (playerInRoom){
         return true;
      }else{
         return false;
      }
   }

   public void setSymbols(Map<String, String> symbols){
      roomSymbols = symbols;
   }


   /**
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents
   * @return (String) String representation of how the room looks
   */
   public String displayRoom() {
      String disp = "";
      Point playerLocation, itemLocation;
      Boolean itemFound = false; 

      for(int i = 0; i<roomHeight; i++){
         for(int j=0 ; j<roomWidth; j++){
            if(i == 0 || i == roomHeight-1){

               if(roomDoors.containsKey("N") || roomDoors.containsKey("S")){
                  if (roomDoors.containsKey("N") && roomDoors.get("N") == j && i == 0){
                     disp += roomSymbols.get("DOOR");
                  }else if(roomDoors.containsKey("S") && roomDoors.get("S") == j && i == roomHeight-1){
                     disp += roomSymbols.get("DOOR");
                  }else{
                     disp += roomSymbols.get("NS_WALL");
                  }
               }else{
                  disp += roomSymbols.get("NS_WALL");
               }
            }else if(j == 0 || j == roomWidth-1){
               if(roomDoors.containsKey("W") || roomDoors.containsKey("E")){
                  if (roomDoors.containsKey("W")  && roomDoors.get("W") == i && j == 0){
                     disp += roomSymbols.get("DOOR");
                  }else if(roomDoors.containsKey("E")  && roomDoors.get("E") == i && j == roomWidth-1){
                     disp += roomSymbols.get("DOOR");
                  }else{
                     disp += roomSymbols.get("EW_WALL");
                  }
               }else{
                  disp += roomSymbols.get("EW_WALL");
               }
            }else{
               playerLocation= roomPlayer.getXyLocation();
               if (playerLocation !=null && i == playerLocation.getY() && j == playerLocation.getX()){
                  disp += roomSymbols.get("PLAYER");
               }else{
                  
                  for (Item item : roomItems ){
                     itemLocation = item.getXyLocation();
                     if(i == itemLocation.getY() && j == itemLocation.getX()){
                        itemFound = true;
                     }
                  }
                  if(itemFound == true){
                     disp += roomSymbols.get("ITEM");
                     itemFound = false;
                  }else{
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