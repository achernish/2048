package com.voipfuture.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Anatoly Chernysh
 */
public class Game2048 {

    private Tile[] myTiles;

    private int mySizeOfBoard;

    private boolean myWin = false;

    private boolean myLose = false;

    private int myScore = 0;

    public Game2048(int sizeOfBoard) {
        this.mySizeOfBoard = sizeOfBoard;
        resetGame();
    }

    public void resetGame() {
        myScore = 0;
        myWin = false;
        myLose = false;
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

    private void addTile() {
        List<Tile> listOfAvailableTiles = availableTiles();
        if (!listOfAvailableTiles.isEmpty()) {
            int index = (int) (Math.random() * listOfAvailableTiles.size()) % listOfAvailableTiles.size();

            Tile emptyTile = listOfAvailableTiles.get(index);
            emptyTile.setValue(Math.random() < 0.9 ? 2 : 4);
        }
    }

    private Tile getTileAt(int i, int j) {
        return myTiles[i + j * mySizeOfBoard];
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

    private void left() {
        for (int i = 0;i < mySizeOfBoard;i++) {
            Tile []tilesInLine = getTilesInLine(i);
            //moveTilesInLine(tilesInLine);
        }
    }

    private void right() {

    }

    private void up() {

    }

    private void down() {

    }

    private void print() {
        for (int i = 0;i < mySizeOfBoard;i++) {
            for (int j = 0;j < mySizeOfBoard;j++) {
                System.out.print(myTiles[i * mySizeOfBoard + j].getValue() + " ");
            }
            System.out.println();
        }
    }

    public static void main(String []args) {
        Game2048 game2048 = new Game2048(4);
        game2048.print();
    }
}