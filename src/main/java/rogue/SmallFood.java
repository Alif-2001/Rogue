package rogue;

public class SmallFood extends Food implements Tossable {
    public SmallFood(){
        
    }
    public String toss(){
        super.getPlayer().tossItem(this);
        String description = super.getDescription();
        return description.split(":")[1];
    }
    
}
