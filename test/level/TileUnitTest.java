package level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import siam.graphics.Sprite;
import siam.level.Piece;
import siam.level.Tile;

public class TileUnitTest {

    private Tile tile;

    @Before
    public void setUp() {
        tile = new Tile(10, 10, false);
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(tile.isEmpty());
    }

    @Test
    public void testIsOnEdge() {
        Assert.assertFalse(tile.isOnEdge());
    }

    @Test
    public void testIsBanished() {
        Assert.assertFalse(tile.isBanished());
    }

    @Test
    public void testInsertPiece() {
        Piece piece = Mockito.mock(Piece.class);
        tile.insertPiece(piece);
        Assert.assertFalse(tile.isEmpty());
    }

    @Test
    public void testRemovePiece() {
        Piece piece = Mockito.mock(Piece.class);
        tile.insertPiece(piece);
        tile.removePiece();
        Assert.assertTrue(tile.isEmpty());
    }

    @Test
    public void testGetSprite() {
        Assert.assertSame(Sprite.tile, tile.getSprite());
    }
}
