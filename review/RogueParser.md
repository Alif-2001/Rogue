| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public RogueParser()|Default constructor|none|none|none|3
|public RogueParser(String filename)|filename and sets up the object|none|parse()|none|3
|public Map<String, String> nextRoom()|Return the next room|roomIterator|none|Room|8
|public Map<String, String> nextItem()|Returns the next item|itemIterator|none|Item|8
|public Map<String, String> nextDoor()|Returns the next door|doorIterator|none|Door|8
|public Map<String, Character> getSymbols()|symbols to be used to print the game|symbols|none|none|3
|public Character getSymbol(String symbolName)|Get the character for a symbol|symbols|none|none|9
|public int getNumOfItems()|Get the number of items|numOfItems|none|none|3
|public int getNumOfRooms()|Get the number of rooms|numOfRooms|none|none|3
|private void parse(String filename)|Read the file containing the file locations|roomIterator,itemIterator,doorIterator|extractRoomInfo,extractItemInfo,extractSymbolInfo|none|27
|private void extractSymbolInfo(JSONObject symbolsJSON)|Get the symbol information|symbols|none|none|11
|private void extractRoomInfo(JSONObject roomsJSON)|Get the room information|rooms|none|none|11
|private void addRoomBasics()|add basics about a room|none|none|none|16
|private Map<String, String> singleRoom(JSONObject roomJSON)|Get a room's information|addRoomBasics()|none|19
|private Map<String, String>  itemPosition(JSONObject lootJSON, String roomID)|information about an item in a room|none|none|11
|private void extractItemInfo(JSONObject roomsJSON)|Item information from the Item key|numOfItems|none|none|9
|private Map<String, String>  singleItem(JSONObject itemsJSON)|single item from its JSON object|none|none|none|19