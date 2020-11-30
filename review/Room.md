| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|somemethod|this is what it is responsible for| a,b,c|someothermethod|Cat, Feline|14
|Room|Default zero parameter constructor|none|none|none|3
|getWidth|get the width of the room|roomWidth|none|none|3
|setWidth|set the width of the room|roomWidth|none|none|3
|getHeight|get the room's height|roomHeight|none|none|3
|setHeight|set the Height of the room|roomHeight|none|none|3
|getId|get the room's id|roomId|none|none|3
|setId|set the room's id|roomId|none|none|3
|getRoomItems|get all the items in the room|roomItems|none|none|3
|setRoomItems|set all the items in the room|roomItems|none|none|3
|setRogue|set the game in which the room is|thisGame|none|none|3
|addItem|add an item to the room|roomItems, roomWidth, roomHeight|checkItemOverlap|Item|18
|addSingleItem|add a single item to the room|roomItems|none|Item|3
|removeItem|remove a single item from the room|roomItems|none|Item|3
|getPlayer|get the player in the room|roomPlayer|none|Player|3
|setPlayer|set the player who is in the room|roomPlayer,playerInRoom|none|Player|4
|makeStart|set this room as start|startRoom, roomPlayer, playerInRoom|nonw|none|8
|isStart|know if the room is the starting room or not|startRoom|none|none|6
|getDoorLocation|door's loaction according to the direction NSEW|roomDoors|none|Door|9
|getDoor|get the door at a given direction NSEW|roomDoors|none|Door|9
|getDoors|get all the doors in the room|roomDoors|none|none|3
|addDoor|add a door to the room|roomDoors|none|none|3
|isPlayerInRoom|know if the player is in the room|playerInRoom|none|none|6
|setSymbols|set all the symbols to display the room|roomSymbols|none|none|3
|verifyRoom|verify if the room has everything in right place| roomItems,roomDoors| isDoorInBound| Item,Door|13
|displayRoom|produce an ascii rendering of the room and all of its contents|roomItems, roomDoors, roomHeight,roomWidth|addDoorToDisplay,addItemToDisplay|Item, Door|17
