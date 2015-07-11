import com.voipfuture.game.Game2048;
import com.voipfuture.game.Tile;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author Anatoly Chernysh
 */
public class Game2048Test {

    private Tile[] createTestTiles() {
        Tile[]testTiles = new Tile[4 * 4];
        testTiles[0]  = new Tile(2); testTiles[1]  = new Tile(0); testTiles[2]  = new Tile(2); testTiles[3]  = new Tile(0);
        testTiles[4]  = new Tile(2); testTiles[5]  = new Tile(0); testTiles[6]  = new Tile(0); testTiles[7]  = new Tile(0);
        testTiles[8]  = new Tile(8); testTiles[9]  = new Tile(2); testTiles[10] = new Tile(0); testTiles[11] = new Tile(0);
        testTiles[12] = new Tile(8); testTiles[13] = new Tile(2); testTiles[14] = new Tile(0); testTiles[15] = new Tile(0);
        return testTiles;
    }

    private int calculateAmountOfEmptyTiles(Tile[] tiles) {
        int amountOfEmptyTiles = 0;
        for (int i = 0;i < tiles.length;i++) {
            if (tiles[i].isEmpty()) {
                amountOfEmptyTiles++;
            }
        }
        return amountOfEmptyTiles;
    }

    @Test
    public void testMoveLeft() {
        Game2048 game2048 = new Game2048(4);
        game2048.setMyTiles(createTestTiles());
        game2048.moveLeft();

        assertEquals(game2048.getTileAt(0, 0).getValue(), 4);
        assertEquals(game2048.getTileAt(0, 1).getValue(), 2);
        assertEquals(game2048.getTileAt(0, 2).getValue(), 8);
        assertEquals(game2048.getTileAt(0, 3).getValue(), 8);
        assertEquals(game2048.getTileAt(1, 2).getValue(), 2);
        assertEquals(game2048.getTileAt(1, 3).getValue(), 2);

        assertEquals(calculateAmountOfEmptyTiles(game2048.getMyTiles()), 9);

        assertEquals(game2048.getMyScore(), 4);
    }

    @Test
    public void testMoveRight() {
        Game2048 game2048 = new Game2048(4);
        game2048.setMyTiles(createTestTiles());
        game2048.moveRight();

        assertEquals(game2048.getTileAt(3, 0).getValue(), 4);
        assertEquals(game2048.getTileAt(3, 1).getValue(), 2);
        assertEquals(game2048.getTileAt(3, 2).getValue(), 2);
        assertEquals(game2048.getTileAt(2, 2).getValue(), 8);
        assertEquals(game2048.getTileAt(3, 3).getValue(), 2);
        assertEquals(game2048.getTileAt(2, 3).getValue(), 8);

        assertEquals(calculateAmountOfEmptyTiles(game2048.getMyTiles()), 9);

        assertEquals(game2048.getMyScore(), 4);
    }

    @Test
    public void testMoveUp() {
        Game2048 game2048 = new Game2048(4);
        game2048.setMyTiles(createTestTiles());
        game2048.moveUp();

        assertEquals(game2048.getTileAt(0, 0).getValue(), 4);
        assertEquals(game2048.getTileAt(1, 0).getValue(), 4);
        assertEquals(game2048.getTileAt(2, 0).getValue(), 2);
        assertEquals(game2048.getTileAt(0, 1).getValue(), 16);

        assertEquals(calculateAmountOfEmptyTiles(game2048.getMyTiles()), 11);

        assertEquals(game2048.getMyScore(), 24);
    }

    @Test
    public void testMoveDown() {
        Game2048 game2048 = new Game2048(4);
        game2048.setMyTiles(createTestTiles());
        game2048.moveDown();

        assertEquals(game2048.getTileAt(0, 2).getValue(), 4);
        assertEquals(game2048.getTileAt(0, 3).getValue(), 16);
        assertEquals(game2048.getTileAt(1, 3).getValue(), 4);
        assertEquals(game2048.getTileAt(2, 3).getValue(), 2);

        assertEquals(calculateAmountOfEmptyTiles(game2048.getMyTiles()), 11);

        assertEquals(game2048.getMyScore(), 24);
    }

    @Test
    public void testCanMove() {
        Game2048 game2048 = new Game2048(4);
        game2048.setMyTiles(createTestTiles());

        assertEquals(game2048.canMove(), true);
    }

    @Test
    public void testCanMoveIfTilesCanBeMerged() {
        Game2048 game2048 = new Game2048(4);

        Tile[]testTiles = new Tile[4 * 4];
        testTiles[0]  = new Tile(2); testTiles[1]  = new Tile(2); testTiles[2]  = new Tile(2); testTiles[3]  = new Tile(2);
        testTiles[4]  = new Tile(2); testTiles[5]  = new Tile(2); testTiles[6]  = new Tile(2); testTiles[7]  = new Tile(2);
        testTiles[8]  = new Tile(8); testTiles[9]  = new Tile(2); testTiles[10] = new Tile(2); testTiles[11] = new Tile(2);
        testTiles[12] = new Tile(8); testTiles[13] = new Tile(2); testTiles[14] = new Tile(2); testTiles[15] = new Tile(2);
        game2048.setMyTiles(testTiles);

        assertEquals(game2048.canMove(), true);
    }

    @Test
    public void testCanMoveIfTilesCantBeMerged() {
        Game2048 game2048 = new Game2048(4);

        Tile[]testTiles = new Tile[4 * 4];
        testTiles[0]  = new Tile(2); testTiles[1]  = new Tile(32); testTiles[2]  = new Tile(2); testTiles[3]  = new Tile(32);
        testTiles[4]  = new Tile(4); testTiles[5]  = new Tile(64); testTiles[6]  = new Tile(4); testTiles[7]  = new Tile(64);
        testTiles[8]  = new Tile(8); testTiles[9]  = new Tile(128); testTiles[10] = new Tile(8); testTiles[11] = new Tile(128);
        testTiles[12] = new Tile(16); testTiles[13] = new Tile(256); testTiles[14] = new Tile(16); testTiles[15] = new Tile(256);
        game2048.setMyTiles(testTiles);

        assertEquals(game2048.canMove(), false);
    }
}