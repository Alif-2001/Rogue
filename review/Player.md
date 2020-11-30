| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public Player()|player constructor|none|none|none|3
|public Player(String name)|initiate player with a name|playerName|none|none|3
|public String getName()|get player's name|playerName|none|none|3
|public void setName(String newName)|set the name of the player|playerName|none|none|3
|public Point getXyLocation()|get player's position|playerXyLocation|none|none|3
|public void setXyLocation(Point newXyLocation)|set the player's loaction|playerXyLocation|none|none|3
|public Room getCurrentRoom()|know where the player currently is|playerRoom|none|Room|3
|public void setCurrentRoom(Room newRoom)|set player's current room|playerRoom|none|Room|3
|public void pickItem(Item newItem)|pick an item|inventory|none|Inventory|4
|public ArrayList<Item> getInventory()|get all the items the player has picked|inventory|none|Inventory,Item|3
|public ArrayList<Item> getClothes()|get the clothes the player is wearing|clothes|none|Items|3
|public void removeItemFromInventory(Item itemToRemove)|removes an item from the player's inventory|inventory|none|Inventory|3
|public void wearItem(Item itemToWear)|add items to the list of clothes|clothes|none|none|3
|public void tossItem(Item itemToDrop)|drop an item|inventory, playerRoom|none|Inventory,Player|5
