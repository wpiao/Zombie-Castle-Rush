import com.fasterxml.jackson.core.JacksonException;
import com.zombiecastlerush.util.Game;

class Main {
    public static void main(String[] args){
        System.out.println("Zombie Castle Rush Test");
        Game ZombieCastleRush = Game.getInstance();
        //TODO: temporary solution; we can handle exception here
        try {
            ZombieCastleRush.start();
            //TODO: ask prompter to stop the game
            // rushHour.stop();
            System.out.println("Timer is up. The end ...");
        } catch (JacksonException je){
            System.out.println(je.getMessage());
        }
    }
}
