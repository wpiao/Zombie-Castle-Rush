import com.zombiecastlerush.util.Game;

class Main {
    public static void main(String[] args){
        System.out.println("Zombie Castle Rush Test");
        Game ZombieCastleRush = Game.getInstance();
        ZombieCastleRush.start();
        //TODO: ask prompter to stop the game
        // rushHour.stop();
        System.out.println("Timer is up. The end ...");
    }
}
