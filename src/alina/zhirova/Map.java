package alina.zhirova;

public class Map {

    public static final char CELL_X = 'x';
    public static final char CELL_0 = 'O';
    public static final char EMTY_CELL = '*';

    public static int SIZE = 3;
    public static int WIN_SIZE = 3;

    public static char humanSymbol;
    public static char aiSymbol;

    public static char[][] curMap;
    public static int[][] curRating;


    public static void initMap() {
        curMap = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                curMap[i][j] = EMTY_CELL;
            }
        }
    }


    public static void initRating() {
        curRating = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                curRating[i][j] = 0;
            }
        }
    }


    public static void clearMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                curMap[i][j] = EMTY_CELL;
            }
        }
    }


    public static void clearRating() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                curRating[i][j] = 0;
            }
        }
    }


}
