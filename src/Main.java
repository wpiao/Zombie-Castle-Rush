import com.zombiecastlerush.util.Game;

class Main {
    public static void main(String[] args){
        System.out.println("Zombie Castle Rush Test");
        Game rushHour = Game.getInstance();
        rushHour.start();
        //TODO: ask prompter to stop the game
        // rushHour.stop();
        System.out.println("Timer is up. The end ...");
    }
}
