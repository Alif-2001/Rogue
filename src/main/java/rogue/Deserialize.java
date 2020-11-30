package rogue;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Deserialize {

    /**
     * default zero parameter constructor.
     */
    public Deserialize() {
    }

    /**
     * This method is used to deserialize a saved game from the given location.
     * @param fileName the file loaction (usually a save.game)
     * @return the Rogue game
     */
    public Rogue deserializeGame(String fileName) {
        Rogue game = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName)); ) {
            game = (Rogue) in.readObject();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundExcpetion" + e);
        }
        return game;
    }
}
