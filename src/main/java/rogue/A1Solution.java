package rogue;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class A1Solution {




    public static void main(String[] args) {
        // Hardcoded configuration file location/name
        String configurationFileLocation = "fileLocations.json";  //please don't change this for this version of the assignment

// instantiate a new Rogue object and call methods to do the required things
        System.out.println("We have liftoff!");
        RogueParser parser = new RogueParser(configurationFileLocation);
        Rogue rogue = new Rogue(parser);
        
        String disp = rogue.displayAll();
        System.out.println(disp);
    }
}
