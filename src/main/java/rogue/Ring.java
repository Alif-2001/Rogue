package rogue;

public class Ring extends Magic implements Wearable {
    @Override
    public String wear(){
        super.getPlayer().removeItemFromInventory(this);
        super.getPlayer().wearItem(this);
        return super.getDescription();
    }

}
