package com.voipfuture.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Anatoly Chernysh
 */
public class Game2048 {

    public final static int TARGET_SCORE = 2048;

    public final static int DEFAULT_BOARD_SIZE = 4;

    private Tile[] myTiles;

    private int mySizeOfBoard;

    private boolean myWin = false;

    private int myScore = 0;

    public Game2048(int sizeOfBoard) {
        this.mySizeOfBoard = sizeOfBoard;
        resetGame();
    }

    public void resetGame() {
        myScore = 0;
        myWin = false;
        myTiles = new Tile[mySizeOfBoard * mySizeOfBoard];
        for (int i = 0; i < myTiles.length; i++) {
            myTiles[i] = new Tile();
        }

        addTile();
    }

    private List<Tile> availableTiles() {
        final List<Tile> listOfAvailableTiles = new ArrayList<Tile>(myTiles.length);
        for (final Tile tile : myTiles) {
            if (tile.isEmpty()) {
                listOfAvailableTiles.add(tile);
            }
        }
        return listOfAvailableTiles;
    }

    private boolean isFull() {
        return availableTiles().size() == 0;
    }

    private void addTile() {
        List<Tile> listOfAvailableTiles = availableTiles();
        if (!listOfAvailableTiles.isEmpty()) {
            int index = (int) (Math.random() * listOfAvailableTiles.size()) % listOfAvailableTiles.size();

            Tile emptyTile = listOfAvailableTiles.get(index);
            emptyTile.setValue(Math.random() < 0.9 ? 2 : 4);
        }
    }

    public Tile getTileAt(int x, int y) {
        return myTiles[x + y * mySizeOfBoard];
    }

    private Tile[] getTilesInLine(int index) {
        Tile []result = new Tile[mySizeOfBoard];
        for (int i = 0;i < mySizeOfBoard;i++) {
            result[i] = getTileAt(i, index);
        }
        return result;
    }

    private void populateLineWithEmptyTiles(List<Tile> lineOfTiles) {
        while (lineOfTiles.size() != mySizeOfBoard) {
            lineOfTiles.add(new Tile());
        }
    }

    private Tile[] moveTilesInLine(Tile []oldLine) {
        LinkedList<Tile> tempLine = new LinkedList<Tile>();

        for (int i = 0;i < oldLine.length;i++) {
            if (!oldLine[i].isEmpty()) {
                tempLine.addLast(oldLine[i]);
            }
        }

        if (tempLine.size() == 0) {
            return oldLine;
        }

        populateLineWithEmptyTiles(tempLine);

        Tile[] newLineOfTiles = new Tile[oldLine.length];
        for (int i = 0;i < oldLine.length;i++) {
            newLineOfTiles[i] = tempLine.removeFirst();
        }

        return newLineOfTiles;
    }

    private boolean compareLines(Tile []line1, Tile []line2) {
        if (line1.length != line2.length) {
            return false;
        }

        for (int i = 0;i < line1.length;i++) {
            if (line1[i] != line2[i]) {
                return false;
            }
        }

        return true;
    }

    private void setTilesInLine(int index, Tile[] tiles) {
        System.arraycopy(tiles, 0, myTiles, index * mySizeOfBoard, mySizeOfBoard);
    }

    private Tile[] mergeTilesInLine(Tile []line) {
        List<Tile> mergedLine = new ArrayList<Tile>();

        for (int i = 0;i < line.length;i++) {
            int num = line[i].getValue();
            if (i < (line.length - 1) &&
                    !line[i].isEmpty() &&
                    line[i].getValue() == line[i + 1].getValue()) {
                num *= 2;
                this.myScore += num;
                if (this.myScore == TARGET_SCORE) {
                    this.myWin = true;
                }
                i++;
            }
            mergedLine.add(new Tile(num));
        }

        populateLineWithEmptyTiles(mergedLine);
        return mergedLine.toArray(new Tile[0]);
    }

    private Tile[] rotateTiles(int angle) {
        Tile[] rotatedTiles = new Tile[mySizeOfBoard * mySizeOfBoard];

        double radians = Math.toRadians(angle);
        int cos = (int)Math.cos(radians);
        int sin = (int)Math.sin(radians);

        int offsetX = mySizeOfBoard - 1;
        int offsetY = mySizeOfBoard - 1;
        if (angle == 90) {
            offsetY = 0;
        } else if (angle == 270) {
            offsetX = 0;
        }

        for (int x = 0;x < mySizeOfBoard;x++) {
            for (int y = 0;y < mySizeOfBoard;y++) {
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                rotatedTiles[(newX) + (newY) * mySizeOfBoard] = getTileAt(x, y);
            }
        }

        return rotatedTiles;
    }

    public void moveLeft() {
        boolean tileHasToBeAdded = false;

        for (int i = 0;i < mySizeOfBoard;i++) {
            Tile []tilesInLine = getTilesInLine(i);
            Tile []mergedTilesInLine = mergeTilesInLine(moveTilesInLine(tilesInLine));
            setTilesInLine(i, mergedTilesInLine);
            if (!tileHasToBeAdded && !compareLines(tilesInLine, mergedTilesInLine)) {
                tileHasToBeAdded = true;
            }
        }

        if (tileHasToBeAdded) {
            addTile();
        }
    }

    public void moveRight() {
        myTiles = rotateTiles(180);
        moveLeft();
        myTiles = rotateTiles(180);
    }

    public void moveUp() {
        myTiles = rotateTiles(270);
        moveLeft();
        myTiles = rotateTiles(90);
    }

    public void moveDown() {
        myTiles = rotateTiles(90);
        moveLeft();
        myTiles = rotateTiles(270);
    }

    public boolean canMove() {
        if (!isFull()) {
            return true;
        }

        for (int i = 0;i < mySizeOfBoard;i++) {
            for (int j = 0;j < mySizeOfBoard;j++) {
                Tile tile = getTileAt(i, j);
                if (((i < mySizeOfBoard - 1) && tile.getValue() == getTileAt(i + 1, j).getValue()) ||
                        (j < mySizeOfBoard - 1) && tile.getValue() == getTileAt(i, j + 1).getValue()) {
                    return true;
                }
            }
        }

        return false;
    }

    public void print() {
        for (int i = 0;i < mySizeOfBoard;i++) {
            for (int j = 0;j < mySizeOfBoard;j++) {
                System.out.print(myTiles[i * mySizeOfBoard + j].getValue() + " ");
            }
            System.out.println();
        }
        System.out.println("Score: " + myScore);
        System.out.println();
    }

    public Tile[] getMyTiles() {
        return myTiles;
    }

    public void setMyTiles(Tile[] myTiles) {
        this.myTiles = myTiles;
    }

    public int getMySizeOfBoard() {
        return mySizeOfBoard;
    }

    public boolean isMyWin() {
        return myWin;
    }

    public int getMyScore() {
        return myScore;
    }

    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter size of game's board (minimum size is 4): ");
        String strSizeOfBoard = in.nextLine();

        int sizeOfBoard = DEFAULT_BOARD_SIZE;
        try {
            sizeOfBoard = Integer.valueOf(strSizeOfBoard);
        }
        catch (Exception e) {
        }

        Game2048 myGame2048 = new Game2048(sizeOfBoard);
        myGame2048.print();

        while (true) {

            if (!myGame2048.canMove()) {
                System.out.println("You've just lost The Game.");
                break;
            }
            if (myGame2048.isMyWin()) {
                System.out.println("You've just won The Game.");
                break;
            }

            System.out.print("Enter move using the keyboard keys 'a' (left) , 'd' (right) , 'w' (up) and 's' (down): ");
            final String move = in.next();
            if (move.equals("a")) {
                myGame2048.moveLeft();
            }
            else if (move.equals("d")) {
                myGame2048.moveRight();
            }
            else if (move.equals("w")) {
                myGame2048.moveUp();
            }
            else if (move.equals("s")) {
                myGame2048.moveDown();
            }

            myGame2048.print();
        }
    }
}