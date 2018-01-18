package alina.zhirova;


public class GameLogic {

    public static int[][] cornersCoordinates = { {0, 0}, {0, Map.SIZE - 1 }, {Map.SIZE - 1, 0}, {Map.SIZE - 1, Map.SIZE - 1} };


    public static boolean isMapFull() {
        for (int i = 0; i < Map.SIZE; i++) {
            for (int j = 0; j < Map.SIZE; j++) {
                if (Map.curMap[i][j] == Map.EMTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean isCellValid(int x, int y) {
        if (x >= 0 && x < Map.SIZE && y >= 0 && y < Map.SIZE && Map.curMap[y][x] == Map.EMTY_CELL) {
            return true;
        }
        return false;
    }


    public static int[] gettingWinIndexes(char winSymbol, int cellWidth, int cellHeight) {
        int[] coordinates = {0, 0, 0, 0};
        int mainDiagonalCount = 0;
        int sideDiagonalCount = 0;
        for (int i = 0; i < Map.SIZE; i++) {
            int lineCount = 0;
            int columnCount = 0;
            for (int j = 0; j < Map.SIZE; j++) {
                if (Map.curMap[i][j] == winSymbol) {
                    lineCount++;
                }
                else {
                    lineCount = 0;
                }
                if (Map.curMap[j][i] == winSymbol) {
                    columnCount++;
                }
                else {
                    columnCount = 0;
                }
                if (i == j && Map.curMap[i][j] == winSymbol) {
                    mainDiagonalCount++;
                }
                if (j == Map.SIZE - 1 - i && Map.curMap[i][j] == winSymbol) {
                    sideDiagonalCount++;
                }
                ////////////////////////////
                if (lineCount == Map.WIN_SIZE) {
                    coordinates[0] = 0;
                    coordinates[1] = (int)((i + 0.5)* cellHeight);
                    coordinates[2] = Map.SIZE * cellWidth;
                    coordinates[3] = (int)((i + 0.5)* cellHeight);
                }
                else if (columnCount == Map.WIN_SIZE) {
                    coordinates[0] = (int)((i + 0.5)* cellWidth);
                    coordinates[1] = 0;
                    coordinates[2] = (int)((i + 0.5)* cellWidth);
                    coordinates[3] = Map.SIZE * cellHeight;
                }
                else if (mainDiagonalCount == Map.WIN_SIZE) {
                    coordinates[0] = 0;
                    coordinates[1] = 0;
                    coordinates[2] = Map.SIZE * cellWidth;
                    coordinates[3] = Map.SIZE * cellHeight;
                }
                else if (sideDiagonalCount == Map.WIN_SIZE) {
                    coordinates[0] = Map.SIZE * cellWidth;
                    coordinates[1] = 0;
                    coordinates[2] = 0;
                    coordinates[3] = Map.SIZE * cellHeight;
                }
            }
        }
        return coordinates;
    }


    public static boolean checkWin(char winSymbol) {
        int mainDiagonalCount = 0;
        int sideDiagonalCount = 0;
        for (int i = 0; i < Map.SIZE; i++) {
            int lineCount = 0;
            int columnCount = 0;
            for (int j = 0; j < Map.SIZE; j++) {
                if (Map.curMap[i][j] == winSymbol) {
                    lineCount++;
                }
                else {
                    lineCount = 0;
                }
                if (Map.curMap[j][i] == winSymbol) {
                    columnCount++;
                }
                else {
                    columnCount = 0;
                }
                if (i == j && Map.curMap[i][j] == winSymbol) {
                    mainDiagonalCount++;
                }
                if (j == Map.SIZE - 1 - i && Map.curMap[i][j] == winSymbol) {
                    sideDiagonalCount++;
                }
                if (lineCount == Map.WIN_SIZE || columnCount == Map. WIN_SIZE ||
                        mainDiagonalCount == Map.WIN_SIZE || sideDiagonalCount == Map.WIN_SIZE) {
                    return  true;
                }
            }
        }
        return  false;
    }


    public static void aiTurn() {
        ratingUpdate();
        int maxRating = Map.curRating[0][0];
        int[] maxIndexes = {0, 0};

        for (int i = 0; i < Map.SIZE; i++) {
            for (int j = 0; j < Map.SIZE; j++) {
                if (Map.curRating[i][j] > maxRating) {
                    maxRating = Map.curRating[i][j];
                    maxIndexes[0] = i;
                    maxIndexes[1] = j;
                }
            }
        }
        if (Map.curMap[ maxIndexes[0] ][ maxIndexes[1] ] == Map.EMTY_CELL) {
            Map.curMap[ maxIndexes[0] ][ maxIndexes[1] ] = Map.aiSymbol;
        }
    }


    public static void ratingUpdate() {
        Map.clearRating();
        if (!instantAIWinHandling()) {
            if (!humanWinHandling()) {
                int center = Map.SIZE / 2;
                if (Map.curMap[center][center] == Map.EMTY_CELL) {
                    Map.curRating[center][center] = 5;
                }
                else if (!cornersHandlingWithBetterWinningChance()) {
                    if (!notCornersHandlingWithBetterWinningChance()) {
                        if (!cornersHandlingWithWorseWinningChance()) {
                            notCornersHandlingWithWorseWinningChance();
                        }
                    }
                }
            }
        }
    }


    public static boolean isArrayEmpty(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != Map.EMTY_CELL) {
                return false;
            }
        }
        return true;
    }


    public static boolean isCorner(int line, int column) {
        for (int i = 0; i < cornersCoordinates.length; i++) {
            int[] curCorner = cornersCoordinates[i];
            if (curCorner[0] == line && curCorner[1] == column) {
                return  true;
            }
        }
        return false;
    }


    public static boolean notCornersHandlingWithWorseWinningChance() {
        int[] array = new int[3];
        for (int i = 0; i < Map.SIZE; i++) {
            for (int k = 0; k < Map.SIZE; k++) {
                if (!isCorner(i, k)) {
                    if (Map.curMap[i][k] == Map.EMTY_CELL) {
                        for (int j = 0; j < Map.SIZE; j++) {
                            array[j] = Map.curMap[i][j];
                        }
                        if (isArrayEmpty(array)) {
                            Map.curRating[i][k] = 1;
                            return true;
                        }
                        else {
                            for (int j = 0; j < Map.SIZE; j++) {
                                array[j] = Map.curMap[j][k];
                            }
                            if (isArrayEmpty(array)) {
                                Map.curRating[i][k] = 1;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public static boolean cornersHandlingWithWorseWinningChance() {
        int[] array = new int[3];
        for (int i = 0; i < cornersCoordinates.length; i++) {
            int[] curCorner = cornersCoordinates[i];
            if (Map.curMap[curCorner[0]][curCorner[1]] == Map.EMTY_CELL) {
                for (int j = 0; j < Map.SIZE; j++) {
                    array[j] = Map.curMap[curCorner[0]][j];
                }
                if (isArrayEmpty(array)) {
                    Map.curRating[curCorner[0]][curCorner[1]] = 2;
                    return true;
                }
                else {
                    for (int j = 0; j < Map.SIZE; j++) {
                        array[j] = Map.curMap[j][curCorner[1]];
                    }
                    if (isArrayEmpty(array)) {
                        Map.curRating[curCorner[0]][curCorner[1]] = 2;
                        return true;
                    }
                    else {
                        for (int j = 0; j < Map.SIZE; j++) {
                            if (curCorner[0] == curCorner[1]) {
                                array[j] = Map.curMap[j][j];
                            }
                            else {
                                array[j] = Map.curMap[j][Map.SIZE - 1 - j];
                            }
                        }
                        if (isArrayEmpty(array)) {
                            Map.curRating[curCorner[0]][curCorner[1]] = 2;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public static boolean notCornersHandlingWithBetterWinningChance() {
        int[] array = new int[3];
        for (int i = 0; i < Map.SIZE; i++) {
            for (int k = 0; k < Map.SIZE; k++) {
                if (!isCorner(i, k)) {
                    if (Map.curMap[i][k] == Map.EMTY_CELL) {
                        for (int j = 0; j < Map.SIZE; j++) {
                            array[j] = Map.curMap[i][j];
                        }
                        if (checkPossibleWin(array)) {
                            Map.curRating[i][k] = 3;
                            return true;
                        }
                        else {
                            for (int j = 0; j < Map.SIZE; j++) {
                                array[j] = Map.curMap[j][k];
                            }
                            if (checkPossibleWin(array)) {
                                Map.curRating[i][k] = 3;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public static boolean cornersHandlingWithBetterWinningChance() {
        int[] array = new int[3];
        for (int i = 0; i < cornersCoordinates.length; i++) {
            int[] curCorner = cornersCoordinates[i];
            if (Map.curMap[curCorner[0]][curCorner[1]] == Map.EMTY_CELL) {
                for (int j = 0; j < Map.SIZE; j++) {
                    array[j] = Map.curMap[curCorner[0]][j];
                }
                if (checkPossibleWin(array)) {
                    Map.curRating[curCorner[0]][curCorner[1]] = 4;
                    return true;
                }
                else {
                    for (int j = 0; j < Map.SIZE; j++) {
                        array[j] = Map.curMap[j][curCorner[1]];
                    }
                    if (checkPossibleWin(array)) {
                        Map.curRating[curCorner[0]][curCorner[1]] = 4;
                        return true;
                    }
                    else {
                        for (int j = 0; j < Map.SIZE; j++) {
                            if (curCorner[0] == curCorner[1]) {
                                array[j] = Map.curMap[j][j];
                            }
                            else {
                                array[j] = Map.curMap[j][Map.SIZE - 1 - j];
                            }
                        }
                        if (checkPossibleWin(array)) {
                            Map.curRating[curCorner[0]][curCorner[1]] = 4;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public static boolean checkPossibleWin(int[] array) {
        boolean isFoundNotEmptyItem = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == Map.humanSymbol) {
                return false;
            }
            else if (array[i] == Map.aiSymbol) {
                isFoundNotEmptyItem = true;
            }
        }
        if (isFoundNotEmptyItem) {
            return true;
        }
        return false;
    }


    public static boolean humanWinHandling() {
        for (int i = 0; i < Map.SIZE; i++) {
            for (int j = 0; j < Map.SIZE; j++) {
                if (Map.curMap[i][j] == Map.EMTY_CELL) {  // line
                    Map.curMap[i][j] = Map.humanSymbol;
                    if (checkWin(Map.humanSymbol)) {
                        Map.curRating[i][j] = 6;
                        Map.curMap[i][j] = Map.EMTY_CELL;
                        return true;
                    }
                    Map.curMap[i][j] = Map.EMTY_CELL;
                }
                else if (Map.curMap[j][i] == Map.EMTY_CELL) { // column
                    Map.curMap[j][i] = Map.humanSymbol;
                    if (checkWin(Map.humanSymbol)) {
                        Map.curRating[j][i] = 6;
                        Map.curMap[j][i] = Map.EMTY_CELL;
                        return true;
                    }
                    Map.curMap[j][i] = Map.EMTY_CELL;
                }
                else if (i == j && Map.curMap[i][j] == Map.EMTY_CELL) { // main diagonal
                    Map.curMap[i][j] = Map.humanSymbol;
                    if (checkWin(Map.humanSymbol)) {
                        Map.curRating[i][j] = 6;
                        Map.curMap[i][j] = Map.EMTY_CELL;
                        return true;
                    }
                    Map.curMap[i][j] = Map.EMTY_CELL;
                }
                else if (j == Map.SIZE - 1 - i && Map.curMap[i][j] == Map.EMTY_CELL) { // side diagonal
                    Map.curMap[i][j] = Map.humanSymbol;
                    if (checkWin(Map.humanSymbol)) {
                        Map.curRating[i][j] = 6;
                        Map.curMap[i][j] = Map.EMTY_CELL;
                        return true;
                    }
                    Map.curMap[i][j] = Map.EMTY_CELL;
                }
            }
        }
        return false;
    }


    public static boolean instantAIWinHandling() {
        for (int i = 0; i < Map.SIZE; i++) {
            for (int j = 0; j < Map.SIZE; j++) {
                if (Map.curMap[i][j] == Map.EMTY_CELL) {  // line
                    Map.curMap[i][j] = Map.aiSymbol;
                    if (checkWin(Map.aiSymbol)) {
                        Map.curRating[i][j] = 7;
                        Map.curMap[i][j] = Map.EMTY_CELL;
                        return true;
                    }
                    Map.curMap[i][j] = Map.EMTY_CELL;
                }
                else if (Map.curMap[j][i] == Map.EMTY_CELL) { // column
                    Map.curMap[j][i] = Map.aiSymbol;
                    if (checkWin(Map.aiSymbol)) {
                        Map.curRating[j][i] = 7;
                        Map.curMap[j][i] = Map.EMTY_CELL;
                        return true;
                    }
                    Map.curMap[j][i] = Map.EMTY_CELL;
                }
                else if (i == j && Map.curMap[i][j] == Map.EMTY_CELL) { // main diagonal
                    Map.curMap[i][j] = Map.aiSymbol;
                    if (checkWin(Map.aiSymbol)) {
                        Map.curRating[i][j] = 7;
                        Map.curMap[i][j] = Map.EMTY_CELL;
                        return true;
                    }
                    Map.curMap[i][j] = Map.EMTY_CELL;
                }
                else if (j == Map.SIZE - 1 - i && Map.curMap[i][j] == Map.EMTY_CELL) { // side diagonal
                    Map.curMap[i][j] = Map.aiSymbol;
                    if (checkWin(Map.aiSymbol)) {
                        Map.curRating[i][j] = 7;
                        Map.curMap[i][j] = Map.EMTY_CELL;
                        return true;
                    }
                    Map.curMap[i][j] = Map.EMTY_CELL;
                }
            }
        }
        return false;
    }


    public static void printMap() {
        System.out.println("========= M A P =========");
        for (int i = 0; i <= Map.SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < Map.SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < Map.SIZE; j++) {
                System.out.print(Map.curMap[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static void printRating() {
        System.out.println("========= R A T I N G =========");
        for (int i = 0; i <= Map.SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < Map.SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < Map.SIZE; j++) {
                System.out.print(Map.curRating[i][j] + " ");
            }
            System.out.println();
        }
    }


}
