package battleship;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

public class GameField {
    private final int rows; // Количество строк
    private final int cols; // Количество столбцов
    private final String cloudCell = "~"; // неисследованное облако
    private final String ship = "O"; // тело корабля
    private final String hit = "X"; // попадание
    private final String miss = "M"; // промах
    private final String printAll = "al"; // печатать всё
    private final String printShots = "sh"; // печатать только промахи или попадания

    private int shipId;
    private String currentShipId = "";


    private final String [][] fieldMap;
    private String [][] shipIdMap;
    private final String [][] occupationMap;

    private final HashMap<String, Integer> shipsFleet;
    private final String[] ships;

    /**
     * Конструктор, в качестве параметра длинна стороны
     */
    public GameField(int cells) {
        this.rows = cells;
        this.cols = cells;
        /**
         * Инициализация массива рабочей области
         */
        fieldMap = new String[rows][cols];
        shipIdMap = new String[rows][cols];
        shipId = 0;
        occupationMap = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                fieldMap[i][j] = cloudCell;
                occupationMap[i][j] = cloudCell;
                shipIdMap[i][j] = cloudCell;
            }
        }
        shipsFleet = new HashMap();
        ships = new String[5];
        ships[0] = "Aircraft Carrier"; shipsFleet.put(ships[0], 5);
        ships[1] = "Battleship"; shipsFleet.put(ships[1], 4);
        ships[2] = "Submarine"; shipsFleet.put(ships[2], 3);
        ships[3] = "Cruiser"; shipsFleet.put(ships[3], 3);
        ships[4] = "Destroyer"; shipsFleet.put(ships[4], 2);
    }

    /**
     * Формирование строки из игрового поля данного класса
     * (в частности - получаем возможность вывода на печать)
     */
    public String toString(String typePrint) {
        StringBuilder outStr = new StringBuilder();
        StringBuilder border = new StringBuilder(" ");
        for (int i = 1; i < cols+1; i++) {
            border.append(" "); border.append(i);
        }
        border.append("\n");
        outStr.append(border);
        for (int i = 0; i < rows; i++) {
            outStr.append(Character.toString(i+65));
            for (int j = 0; j < cols; j++) {
                outStr.append(" ");
                String ch = fieldMap[i][j];
                if (typePrint.equals(printShots))
                    ch = (ch.equals(ship)) ? cloudCell : ch;
                outStr.append(ch);
            }
            outStr.append("\n");
        }

        return outStr.toString();
    }

    /**
     * Если не осталось кораблей на поле - проигрыш
     */
    public boolean isLoser() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (ship.equals(fieldMap[i][j])) return false;
            }
        }
        return true;
    }

    /**
     * Проверка на незаполненность
     */
    private boolean isOccupation(Point point) {
        return (occupationMap[point.x][point.y].equals(cloudCell));
    }

    /**
     * Проверка есть ли корабль в этой точке
     */
    public boolean isShip(Point point) {
        currentShipId = "";
        String sh = fieldMap[point.x][point.y];
        if (sh.equals(ship))
            currentShipId = shipIdMap[point.x][point.y];
        return (sh.equals(ship) || sh.equals(hit));
    }

    /**
     * Проверка есть ли еще живые палубы этого корабля
     */
    private boolean isShipAlive() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (currentShipId.equals(shipIdMap[i][j])) return true;
            }
        }
        return false;
    }

    /**
     * Расстановка кораблей
     */
    public void makeFleet() {
        // shipsFleet.forEach((k, v) -> makeShip(k, v));
        println(this.toString(printAll));
//        String s = ships[3];
        for (String s : ships)
            makeShip(s, shipsFleet.get(s));
    }

    /**
     * Создание корабля
     * @param shipType - тип судна
     * @param desk - количество палуб
     */
    private void makeShip(String shipType, int desk) {
        boolean isOK = false;
        println("Enter the coordinates of the " + shipType + "(" + desk + "cells):");
        while (!isOK) {
            String mapPoints = getString();
            String[] points = mapPoints.split(" ");
            Point pointA = getMapPoints(points[0]);
            Point pointB = getMapPoints(points[1]);
            isOK = (pointA.x == pointB.x || pointA.y == pointB.y) && !pointA.equals(pointB);
            if (!isOK) {
                println("Error! Wrong ship location! Try again:");
                continue;
            }
            int sDesk = (pointA.x == pointB.x) ? Math.abs(pointA.y - pointB.y) : Math.abs(pointA.x - pointB.x);
            isOK = sDesk+1 == desk;
            if (!isOK) {
                println("Error! Wrong length of the Submarine! Try again:");
                continue;
            }
            isOK = setShipOnMap(pointA, pointB, desk);
            if (!isOK) {
                println("Error! You placed it too close to another one. Try again:");
                continue;
            }
            println(this.toString(printAll));
        }
    }

    /**
     * Установка корабля на карту
     * @param pointA - координаты начала
     * @param pointB - координаты хвоста
     * @param desk - количество палуб
     * @return - возвращает удачность попытки
     */
    private boolean setShipOnMap(Point pointA, Point pointB, int desk) {
        boolean isHorizontal = pointA.x == pointB.x;
        int begin;
        if (isHorizontal) {
            begin = Math.min(pointA.y, pointB.y);
            for (int i = begin; i < begin+desk; i++) if (!isOccupation(new Point(pointA.x, i))) return false;
            shipId++;
            for (int i = begin; i < begin+desk; i++) makeOccupation(new Point(pointA.x, i), ""+shipId);
        } else {
            begin = Math.min(pointA.x, pointB.x);
            for (int i = begin; i < begin+desk; i++) if (!isOccupation(new Point(i, pointA.y))) return false;
            shipId++;
            for (int i = begin; i < begin+desk; i++) makeOccupation(new Point(i, pointA.y), ""+shipId);
        }
        return true;
    }

    private int getMin(int a) { return (a==0) ? a : a - 1;}
    private int getMax(int a) { return (a==9) ? a : a + 1; }

    /**
     * Устанавливает занимаемую область вокруг клетки корабля
     * @param point
     */
    private void makeOccupation(Point point, String shipId) {
        fieldMap[point.x][point.y] = ship;
        shipIdMap[point.x][point.y] = shipId;
        for (int x = getMin(point.x); x <= getMax(point.x); x++) {
            for (int y = getMin(point.y); y <= getMax(point.y); y++) {
                occupationMap[x][y] = ship;
            }
        }
    }

    /**
     * Вычисляет координаты из строки (например A1 -> x=0;y=0)
     * @param mapPoints - строка координат
     * @return - класс с координатами
     */
    private Point getMapPoints(String mapPoints) {
        char[] coordinate = mapPoints.toCharArray();
        int x = Character.getNumericValue(coordinate[0]);
        int y = (coordinate.length > 2) ? Character.getNumericValue(coordinate[1]+coordinate[2]) : Character.getNumericValue(coordinate[1]);
        return new Point(x-10, y-1);
    }

    /**
     * Делает ход, распечатывает полученный результат, закрывая оставшуюся область.
     */
    public void makeTurn(GameField otherGameField) {
        println(otherGameField.toString(printShots));
        println("---------------------");
        println(this.toString(printAll));
        println("Player, it's your turn:");
        Point point = new Point();
        boolean isOK = false;
        while (!isOK) {
            point = getMapPoints(getString());
            isOK = point.x >= 0 && point.x < 10 && point.y >= 0 && point.y < 10;
            if (!isOK) {
                println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
        }
        currentShipId = "";
        boolean pointWithShip = otherGameField.isShip(point);
        otherGameField.markPoint(point, pointWithShip ? hit : miss);
        if (pointWithShip)
            println(otherGameField.isShipAlive() ? "You hit a ship!" : "You sank a ship!");
        else
            println("You missed!");
    }

    public void markPoint(Point point, String s) {
        fieldMap[point.x][point.y] = s;
        shipIdMap[point.x][point.y] = s;
    }

    public static void print(String string) { System.out.print(string); }

    public static void println(String string) { System.out.println(string); }

    public static String getString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}