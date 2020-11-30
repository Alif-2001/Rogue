package rogue;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class Deserialize {
    
    public Deserialize(){

    }
    
    public Rogue deserializeGame(String fileName){
        Rogue game = null;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName)); ){
            game = (Rogue)in.readObject();
        }catch(IOException ex){
            System.out.println("IOException: " + ex);
        }catch(ClassNotFoundException e){
            System.out.println("ClassNotFoundExcpetion" + e);
        }
        return game;
    }
}
