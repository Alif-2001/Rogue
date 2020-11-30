package rogue;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Serialize {

    /**
     * default zero parameter constructor.
     */
    public Serialize() {

    }

    /**
     * This method serializes a game and saves it under the name "save.game".
     * @param gameToSave the Rogue game we want to save
     */
    public void serializeGame(Rogue gameToSave) {
        String fileName = "save.game";

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream outputDest = new ObjectOutputStream(outputStream);
            outputDest.writeObject(gameToSave);
            outputDest.close();
            outputStream.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
