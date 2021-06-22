# Zombie Castle Rush

A console-based game written in Java. A player starts from the castle-hall and needs to find a way to the exit. A 
player needs to fight and win the final boss to get to the exit. There are two play mode in this game - console mode,
roguelike mode.   

**Developers:** Wenhao, Yichun, Julian, Rasoj  

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
Instructions to Build Maven on IntelliJ

1. Open the project in a new window
2. Open the Project side-tab on left
3. Right-click on Zombie-Castle-Rush, click Add Framework Support
4. Click Maven, then OK
5. Open Maven side-tab on right
6. Expand Zombie-Castle-Rush, Lifecycle, then double-click Compile

Running Unit Tests

1. With above steps completed, click Maven side-tab
2. Expand Zombie-Castle-Rush, Lifecycle, then double-click Test

Building the JAR

1. With build steps completed, click Maven side-tab
2. Expand Zombie-Castle-Rush, Lifecycle, then double-click install
3. Check your output folder (should be /target)