package rogue.rogueExceptions;
import rogue.*;
import java.awt.Point;
import java.util.Random;

public class ImpossiblePositionException extends Exception{
    private Item item = new Item();
    public ImpossiblePositionException(String s){
        super(s);
    }

    public void setItem(Item remItem){
        item = remItem;
    }

    private int generateRandomInt(int min, int max){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void setNewPosition(int roomHeight, int roomWidth){
        Point position = new Point();
        position.setLocation(generateRandomInt(1, roomWidth-2), generateRandomInt(1, roomHeight-2));
        item.setXyLocation(position);
    }

    public Item getItem(){
        return item;
    }
}
