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


public class Rogue{
    private Player roguePlayer;
    private ArrayList<Room> rogueRooms = new ArrayList<>();
    private ArrayList<Item> rogueItems = new ArrayList<>();
    private Map<String, String> rogueSymbols = new HashMap<String, String>();

    public void setPlayer(Player thePlayer){
        roguePlayer = thePlayer;
    }


    public void setSymbols(String filename){
        JSONParser jsonParser = new JSONParser();
        try{
            Object obj = jsonParser.parse(new FileReader(filename)); 
            JSONObject JSON = (JSONObject) obj;

    
            JSONArray ja = (JSONArray)JSON.get("symbols");
            
            for(Object object: ja){
                String name = ((JSONObject)object).get("name").toString();
                String symbol = ((JSONObject)object).get("symbol").toString();
                rogueSymbols.put(name, symbol);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addRoom(Room room){
        rogueRooms.add(room);
    }

    public ArrayList<Room> getRooms(){
        return rogueRooms;

    }

    private void addItems(Item item){
        rogueItems.add(item);
    }

    public ArrayList<Item> getItems(){
        return rogueItems;

    }
    public Player getPlayer(){
        return roguePlayer;

    }

    public void createRooms(String filename){
        JSONParser jsonParser = new JSONParser();
        try{
            Object obj = jsonParser.parse(new FileReader(filename)); 
            JSONObject JSON = (JSONObject) obj;

    
            JSONArray ja = (JSONArray)JSON.get("room");
            
            for(Object object: ja){
                
                Room room = new Room();
                room.setId(Integer.parseInt(((JSONObject)object).get("id").toString()));
                if(Boolean.parseBoolean(((JSONObject)object).get("start").toString()) == true){
                    room.makeStart();
                }
                room.setHeight(Integer.parseInt(((JSONObject)object).get("height").toString()));
                room.setWidth(Integer.parseInt(((JSONObject)object).get("width").toString()));

                JSONObject JSON2 = (JSONObject) object;
                JSONArray doors = (JSONArray)JSON2.get("doors");

                for(Object object2: doors){
                    String direction = ((JSONObject)object2).get("dir").toString();
                    int location = Integer.parseInt(((JSONObject)object2).get("id").toString());
                    room.setDoor(direction, location);
                }
                
                JSONObject JSON3 = (JSONObject) object;
                JSONArray loot = (JSONArray)JSON3.get("loot");

                ArrayList<Item> roomItems = new ArrayList<Item>();
                for(Object object3: loot){
                    Item newItem = new Item();
                    newItem.setId(Integer.parseInt(((JSONObject)object3).get("id").toString()));
                    Point newLocation = new Point();
                    newLocation.setLocation(Integer.parseInt(((JSONObject)object3).get("x").toString()), Integer.parseInt(((JSONObject)object3).get("y").toString()));
                    newItem.setXyLocation(newLocation);
                    roomItems.add(newItem);
                }
                room.setRoomItems(roomItems);
                this.addRoom(room);
            }

            ja = (JSONArray)JSON.get("items");
            for(Object object: ja){
                Item item = new Item();
                item.setId(Integer.parseInt(((JSONObject)object).get("id").toString()));
                item.setName(((JSONObject)object).get("name").toString());
                item.setType(((JSONObject)object).get("type").toString());

                this.addItems(item);
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public String displayAll(){
        //creates a string that displays all the rooms in the dungeon
        String disp = "";
        for(Room room: rogueRooms){
            disp += "<---- [Room "+room.getId()+"] ---->\n";
            if(room.isStart() == true){
                disp += "- Starting Room\n";
            }
            disp += room.displayRoom();
        }
        return disp;
    }
}