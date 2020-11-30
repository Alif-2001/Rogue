package rogue;

public class Clothing extends Item implements Wearable {
    /**
     * default zero parameter constructor.
     */
    public Clothing() {
    }
    @Override
    public String wear() {
        super.getPlayer().removeItemFromInventory(this);
        super.getPlayer().wearItem(this);
        return super.getDescription();
    }

}
