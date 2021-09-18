package battleship;

public class Main {
    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine(10);
        gameEngine.makeFleet();
        gameEngine.startGame();
    }
}