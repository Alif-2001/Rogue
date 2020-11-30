package rogue;
//import java.util.Scanner;
//import java.util.ArrayList;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.awt.Point;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;


public class A1Solution {



    /**
     * Solution A1, prints all the rooms in the game.
     * @param args comand line input.
     */
    public static void main(String[] args) {
        // Hardcoded configuration file location/name
        String configurationFileLocation = "fileLocations.json";

// instantiate a new Rogue object and call methods to do the required things
        System.out.println("We have liftoff!");
        RogueParser parser = new RogueParser(configurationFileLocation);
        Rogue rogue = new Rogue(parser);

        String disp = rogue.displayAll();
        System.out.println(disp);

        Serialize save = new Serialize();
        save.SerializeGame(rogue);

        Deserialize load = new Deserialize();
        Rogue game2 = load.DeserializeGame("save.game");

        System.out.println("SAVED GAME:");
        System.out.println(game2.displayAll());
    }
}
