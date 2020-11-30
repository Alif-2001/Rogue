package rogue;

public class Food extends Item implements Edible {
    @Override
    public String eat(){
        super.getPlayer().removeItemFromInventory(this);
        return super.getDescription();
    }
}
