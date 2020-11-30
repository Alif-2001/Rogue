package rogue;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Serialize {

    public Serialize(){

    }
    public void serializeGame(Rogue gameToSave){
        String fileName = "save.game";

        try{
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream outputDest = new ObjectOutputStream(outputStream);
            outputDest.writeObject(gameToSave);
            outputDest.close();
            outputStream.close();
        }catch(IOException ex){
            System.out.println(ex);
        }
        
    }
}
