package rogue;

public class Food extends Item implements Edible {
    public Food(){
        
    }
    @Override
    public String eat(){
        super.getPlayer().removeItemFromInventory(this);
        return super.getDescription();
    }
}
