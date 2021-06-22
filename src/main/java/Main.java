import com.fasterxml.jackson.core.JacksonException;
import com.zombiecastlerush.util.Game;
import com.zombiecastlerush.util.Prompter;

class Main {
    public static void main(String[] args) {
        Prompter.clearScreen();
        Game ZombieCastleRush = Game.getInstance();
        //TODO: temporary solution; we can handle exception here
        try {
            ZombieCastleRush.start();
            //TODO: ask prompter to stop the game
            // rushHour.stop();
            System.out.println("GUI mode initializing...");
        } catch (JacksonException je) {
            System.out.println(je.getMessage());
        }
    }
}
