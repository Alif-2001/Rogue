#   Rogue

Rogue is a dungeon crawling game in which the player explores rooms, loots different items, and fends off monsters.

##  Compilation

Use gradle to compile Rogue.

```bash
gradle build
```

##  Run

```bash
java -jar build/libs/A1.jar
```

##  Version 1

Prints all the rooms in the dungeon with loots and doors.

Example of json:
```json
{
    "room": [{
            "id": 1,
            "start": true,
            "height": 10,
            "width": 20,
            "doors": [{
                "dir": "S",
                "id": 2
            }],
            "loot": [{
                    "id": 1,
                    "x": 3,
                    "y": 3
                },
                {
                    "id": 1,
                    "x": 8,
                    "y": 8
                }
            ]
        }
    ]
}
```

Output:
```bash
<---- [Room 1] ---->
- Starting Room
--------------------
|@.................|
|..................|
|..*...............|
|..................|
|..................|
|..................|
|..................|
|.......*..........|
--+-----------------
```
