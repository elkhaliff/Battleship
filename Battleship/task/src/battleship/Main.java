package battleship;

import java.util.Scanner;

public class Main {
    public static void println(String string) {
        System.out.println(string);
    }
    public static void print(String string) {
        System.out.print(string);
    }

    public static String getString(String input) {
        Scanner scanner = new Scanner(System.in);
        print(input);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameEngine GameEngine = new GameEngine(10);
        print(GameEngine.toString());
/*
        int err = -1;
        String step = "X";
        String checkGameStr = "";

        while (checkGameStr == "") { // Цикл получения координат - ожидание хода, проверка результатов
            print("Enter the coordinates (" + step + "): ");
            try {
                err = GameEngine.setCoordinates(step, scanner.nextInt(), scanner.nextInt()); // Запрашиваем ход игрока, устанавливаем ход на доску
                switch (err) {
                    case 0: {
                        step = (step == "O") ? "X" : "O";
                        GameEngine.statXO(); // Сбор статистики по заполненным клеткам
                        checkGameStr = GameEngine.checkGame();
                        println(GameEngine.toString());
                        break;
                    }
                    case 1: println("Coordinates should be from 1 to 3!"); break;
                    case 2: println("This cell is occupied! Choose another one!"); break;
                }
            } catch (Exception e) {
                println("You should enter numbers!");
            }
        }
        println(GameEngine.checkGame());

 */
    }
}
