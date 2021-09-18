package battleship;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Основной класс приложения
 *  @author Andrey Zotov aka OldFox
 */
public class GameEngine {
    final String passTurn = "Press Enter and pass the move to another player";

    private GameField gameField1;
    private GameField gameField2;

    private int currGamer = 1;

    /**
     * Конструктор, в качестве параметра длинна стороны
     */
    public GameEngine(int cells) {
        /**
         * Инициализация классов игровых зон
         */
        gameField1 = new GameField(cells);
        gameField2 = new GameField(cells);
    }

    /**
     * Расстановка кораблей обоих игроков
     */
    public void makeFleet() {
        println("Player 1, place your ships on the game field");
        gameField1.makeFleet();
        String dummy = getString(passTurn);
        println("Player 2, place your ships on the game field");
        gameField2.makeFleet();
    }

    /**
     * Процесс игры
     */
    public void startGame() {
        while (!gameField1.isLoser() || !gameField1.isLoser())
            makeTurn();
        println("You sank the last ship. You won. Congratulations!");
    }

    private void makeTurn() {
        String dummy = getString(passTurn);
        if (currGamer == 1) {
            println("Player 1, it's your turn:");
            gameField1.makeTurn(gameField2);
        } else {
            println("Player 2, it's your turn:");
            gameField2.makeTurn(gameField1);
        }
        currGamer = (currGamer == 1) ? 2 : 1;
    }

    public static void print(String string) { System.out.print(string); }

    public static void println(String string) { System.out.println(string); }

    public static String getString(String string) {
        Scanner scanner = new Scanner(System.in);
        println(string);
        return scanner.nextLine();
    }
}