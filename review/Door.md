| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|public Door()|Door constructor|none|none|none|3
|public void setLocation(int newPosition, String newDirection)|Set the door's location|position, direction|none|none|4
|public int getPosition(String dir)|Get the position on the wall|direction, position|none|none|6
|public String getDirection()|get the door's direction|direction|none|none|3
|public void connectRoom(Room r)|connect a second room to the door|connectedRooms|none|Room|3
|public ArrayList<Room> getConnectedRooms()|get the two rooms the door links|connectedRooms|none|Room|3
|public Room getOtherRoom(Room currentRoom)|get the other room based on which room you are in|connectedRooms|none|Room|8