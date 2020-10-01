package rogue;

import java.util.ArrayList;
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
    private Map<String, String> rogueSymbols;

    public void setPlayer(Player thePlayer){
        roguePlayer = thePlayer;
    }


    public void setSymbols(String filename){
        JSONParser jsonParser = new JSONParser();
        try{
            Object obj = jsonParser.parse(new FileReader(filename)); 
            JSONObject JSON = (JSONObject) obj;

    
            JSONArray ja = (JSONArray)JSON.get("symbols");
            
            /*
            for(Object object: ja){
                rogueSymbols.put((String)((JSONObject)object).get("name"), (String)((JSONObject)object).get("symbol"));
            }
            */
            //System.out.println(rogueSymbols);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Room> getRooms(){
        return null;

    }

    public ArrayList<Item> getItems(){
        return null;

    }
    public Player getPlayer(){
        return null;

    }

    public void createRooms(String filename){

    }
    public String displayAll(){
        //creates a string that displays all the rooms in the dungeon
        return null;
    }
}