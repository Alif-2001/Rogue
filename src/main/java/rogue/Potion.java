package rogue;

public class Potion extends Magic implements Edible, Tossable{

    public Potion(){
        
    }

    @Override
    public String eat(){
        super.getPlayer().removeItemFromInventory(this);
        String description = super.getDescription();
        return description.split(":")[0];
    }

    @Override
    public String toss(){
        super.getPlayer().tossItem(this);
        String description = super.getDescription();
        return description.split(":")[1];
    }
}
