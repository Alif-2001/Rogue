package rogue;

public class SmallFood extends Food implements Tossable {
    /**
     * Default zero parameter constructor.
     */
    public SmallFood() {

    }

    /**
     * This method tosses the Item onto the map.
     * @return the message describing the action
     */
    public String toss() {
        super.getPlayer().tossItem(this);
        String description = super.getDescription();
        return description.split(":")[1];
    }
}
