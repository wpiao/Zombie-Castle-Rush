# Zombie Castle Rush

A console-based game written in Java. A player starts from the castle-hall and needs to find a way to the exit. A 
player needs to fight and win the final boss to get to the exit. There are two play mode in this game - console mode,
roguelike mode.   

**Developers:** Wenhao, Yichun, Julian, Rasoj  

## V2.0.0 features - 06/28/2021     

1. Console play mode    
   - Add more puzzles
   - Show player's location in the map
   - Add win and lose screen
   - Show inventory
   - Add more color scheme to the prompt
   
2. Roguelike play mode
   - Save/Load game
   - Add zombies
   - Add chasing zombie
   - Buy/Sell items in the shop
   - Use items
   - Pick up/Drop items
   - Fight zombies/lord
   - Show status in the side panels
   - Show player's location in the map
   - Field of view
   
3. Deploy to the web - https://wpiao.github.io/zombie-castle-rush-client
   - Only supports console mode
   - Does not support sound
   
## V1.0.0 features - 06/22/2021

1. Console play mode
    - Show ascii map
    - Add welcome screen
    - Add background music
    - Save/Load game
    
2. Roguelike play mode
    - Traverse rooms
    - Show location and its description
    
3. Bug fixed
    - Command "fight", "run" were case-sensitive
    - Cannot "sell" or "buy" items from shop after saving game
    - Empty inputs cause error when choose game mode
    
## How to start

1. Clone the repo
2. Add dependencies
   - jackson-annotations: 2.12.3
   - jackson-core: 2.12.3
   - jackson-databind: 2.12.3
   - junit: 4.12
   - hamcrest-core: 1.3
   - asciiPanel
3. Build the project and run it

## How to play    

- Jar file - https://github.com/wpiao/Zombie-Castle-Rush/releases/tag/V2.0.0
   1. Download the most recent release
   2. Download zip file and extract it
   3. Double click "run.bat" for Windows OS
   4. Run "run.sh" file in the terminal in the same directory of the unzipped files for Linux or macOS

- Web mode - https://wpiao.github.io/zombie-castle-rush-client
   1. Only supports console mode
   2. Does not support sound