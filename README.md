#   Rogue

Rogue is a dungeon crawling game in which the player explores rooms, loots different items, and fends off monsters.

##  Compilation

Use gradle to compile Rogue.

```bash
gradle build
```

##  Run

```bash
java -jar build/libs/A2.jar
```

##  Version 2

Displays the rooms on the terminal. The player can move using "w, a, s ,d".
Explore the dungeon and pick up items!

Example of json:
```json
{
    "room": [{
            "id": 1,
            "start": true,
            "height": 10,
            "width": 20,
            "doors": [{
                "dir": "W",
                "con_room": 2,
                "wall_pos": 3
            },
            {
                "dir": "E",
                "con_room": 2,
                "wall_pos": 5
            }],
            "loot": [{
                    "id": 1,
                    "x": 3,
                    "y": 3
                },
                {
                    "id": 2,
                    "x": 8,
                    "y": 8
                }
            ]
        }
    ],
    "items": [{
            "id": 1,
            "name": "Health Potion",
            "type": "potion",
            "description":"you feel better"
        },
        {
            "id": 2,
            "name": "Scroll of Fireball",
            "type": "scroll",
            "description":"A 20 foot ball of flame hurtles away from you"
        },
        {
            "id": 3,
            "name": "Mango",
            "type": "food",
            "description": "my that was a yummy mango!"
        }
    ]
}
```

Output:
```bash

Thats a lovely move: w                          

--------------------
|@.................|
|..................|
+..!...............|
|..................|
|..................+
|..................|
|..................|
|.......?..........|
--------------------
```
