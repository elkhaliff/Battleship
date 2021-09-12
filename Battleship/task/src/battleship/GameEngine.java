package battleship;

/**
 * Основной класс приложения
 *  @author Andrey Zotov aka OldFox
 *  rows - Количество строк
 *  cols - Количество колонок
 */
public class GameEngine {
    private final int rows; // Количество строк
    private final int cols; // Количество столбцов
    private int cntX = 0;
    private int cntO = 0;


    private final String [][] fieldMap;

    public GameEngine(int cells) {
        this.rows = cells;
        this.cols = cells;
        /**
         * Инициализация массива рабочей области
         */
        fieldMap = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                fieldMap[i][j] = " ";
            }
        }
    }

    /**
     * Ход крестиков
     */
    public void setX(int row, int col) {
        fieldMap[row][col] = "X";
    }

    /**
     * Ход ноликов
     */
    public void setO(int row, int col) {
        fieldMap[row][col] = "O";
    }

    /**
     * Формирование строки из игрового поля данного класса
     * (в частности - получаем возможность вывода на печать)
     */
    @Override
    public String toString() {
        String outStr = "";
        String border = "";

        for (int i = 1; i < cols*3+1; i++) {
            border += "-";
        }
        border += "\n";
        outStr += border;
        for (int i = 0; i < rows; i++) {
            outStr +=  "| ";
            for (int j = 0; j < cols; j++) {
                outStr +=  fieldMap[i][j] + " ";
            }
            outStr += "|\n";
        }
        outStr += border;
        return outStr;
    }


    /**
     * Статистика крестиков и ноликов
     */
    public void statXO(){
        cntX = 0;
        cntO = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                switch (fieldMap[i][j]) {
                    case "X": cntX++; break;
                    case "O": cntO++; break;
                }
            }
        }
    }

    /**
     * Проверка поля на ничью и выигрыш одного из игроков
     */
    public String checkGame() {
        boolean isWinX = false; // X wins
        boolean isWin0 = false; // O wins
        String winX = "";
        String winO = "";
        for (int i = 0; i < cols; i++) {
            winX += "X";
            winO += "O";
        }

        for (int r=0; r < rows; r++) {
            isWinX = getRow(r).equals(winX) || isWinX;
            isWin0 = getRow(r).equals(winO) || isWin0;
        }
        for (int c=0; c < rows; c++) {
            isWinX = getCol(c).equals(winX) || isWinX;
            isWin0 = getCol(c).equals(winO) || isWin0;
        }

        isWinX = getRightDiagonal().equals(winX) || isWinX;
        isWin0 = getRightDiagonal().equals(winO) || isWin0;

        isWinX = getLeftDiagonal().equals(winX) || isWinX;
        isWin0 = getLeftDiagonal().equals(winO) || isWin0;

        if (isWinX) return "X wins";
        if (isWin0) return "O wins";
        if (!isEmptyCell()) return "Draw";
        return "";
    }

    /**
     * Получение столбца данных
     */
    private String getCol(int col) {
        var out = "";
        for (int r=0; r < rows; r++) {
            out += fieldMap[r][col];
        }
        return out;
    }

    /**
     * Получение строки данных
     */
    private String getRow(int row) {
        var out = "";
        for (int c=0; c < cols; c++) {
            out += fieldMap[row][c];
        }
        return out;
    }

    /**
     * Получение правой диагонали
     */
    private String getRightDiagonal() {
        var out = "";
        for (int c=0; c < cols; c++) {
            out += fieldMap[c][c];
        }
        return out;
    }

    /**
     * Получение левой диагонали
     */
    private String getLeftDiagonal() {
        var out = "";
        var c = cols;
        for (int r=0; r < rows; r++) {
            out += fieldMap[r][--c];
        }
        return out;
    }

    /**
     * Проверка на незаполненность
     */
    public boolean isEmpty(int row, int col) {
        return (fieldMap[row][col].equals(" "));
    }

    /**
     * Количество свободных ячеек (возможность хода)
     */
    private boolean isEmptyCell() {
        return (rows * cols - (cntX + cntO)) > 0;
    }

    /**
     * Проверка и установка нового значения
     */
    public int setCoordinates(String step, int row, int col) {
        if ((row < 1 && row > 3) || (col < 1 && col > 3)) {
            return 1;    // Coordinates should be from 1 to 3!
        }
        if (!isEmpty(row-1, col-1)) {
            return 2;    // This cell is occupied! Choose another one!
        }
        if (step == "X") {
            setX(row-1, col-1);
        } else {
            setO(row-1, col-1);
        }
        return 0;
    }
}
