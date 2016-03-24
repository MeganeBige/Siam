package level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import siam.Constants;
import siam.level.*;
import siam.player.Camp;

public class BoardUnitTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board(5, false, false);
    }

    @Test
    public void testInitBoard() {
        for (int x = 0; x < Constants.BOARD_SIZE; x++) {
            for (int y = 0; y < Constants.BOARD_SIZE; y++) {
                Assert.assertTrue(board.getTile(x, y) instanceof Tile);
                Assert.assertFalse(board.asABanishedTile(x, y));
                if ((x == 1 || x == 2 || x == 3) && y == 2) {
                    Assert.assertFalse(board.isFree(x, y));
                    Assert.assertSame(Camp.NEUTRAL, board.getPiece(x, y).getCamp());
                } else {
                    Assert.assertTrue(board.isFree(x, y));
                }
            }
        }
        board = new Board(5, true, true);
        Assert.assertTrue(board.asABanishedTile(2, 0));
        Assert.assertTrue(board.asABanishedTile(2, 4));
        Assert.assertSame(Camp.WHITE, board.getPiece(1, 2).getCamp());
        Assert.assertSame(Camp.NEUTRAL, board.getPiece(2, 2).getCamp());
        Assert.assertSame(Camp.BLACK, board.getPiece(3, 2).getCamp());
    }

    @Test
    public void testPieceMoving() {
        Piece piece = Mockito.mock(Piece.class);
        board.pieceMoving(piece);
        Assert.assertTrue(board.isPieceIsMoving());
        Assert.assertSame(piece, board.getPieceMoving());
    }

    @Test
    public void testPieceStopMoving() {
        Piece piece = Mockito.mock(Piece.class);
        board.pieceMoving(piece);
        board.pieceStopMoving();
        Assert.assertFalse(board.isPieceIsMoving());
        Assert.assertSame(null, board.getPieceMoving());
    }

    @Test
    public void testGetPiece() {
        Assert.assertSame(null, board.getPiece(0, 0));
    }

    @Test
    public void testPutPiece() {
        Piece piece = Mockito.mock(Piece.class);
        Mockito.when(piece.getCoord()).thenReturn(new int[]{0,0});
        board.putPiece(piece);
        Assert.assertSame(piece, board.getPiece(0, 0));
        piece = Mockito.mock(Piece.class);
        board.putPiece(0, 0, piece);
        Assert.assertSame(piece, board.getPiece(0, 0));
    }

    @Test
    public void testRemovePiece() {
        Piece piece = Mockito.mock(Piece.class);
        board.putPiece(0, 0, piece);
        board.removePiece(new int[]{0,0});
        Assert.assertSame(null, board.getPiece(0, 0));
    }

    @Test
    public void testChangeTile() {
        Tile tile = board.getTile(0, 0);
        board.changeTile(0, 0, true);
        Assert.assertNotSame(tile, board.getTile(0, 0));
    }

    @Test
    public void testPieceSelected() {
        Animal animal = Mockito.mock(Animal.class);
        Mockito.when(animal.getIsSelected()).thenReturn(false, false, true, true);
        board.putPiece(0, 0, animal);
        Assert.assertFalse(board.pieceSelected());
        Assert.assertFalse(board.pieceSelected(0, 0));
        board.select(0, 0);
        Assert.assertTrue(board.pieceSelected());
        Assert.assertTrue(board.pieceSelected(0, 0));
    }

    @Test
    public void testGetPieceSelected() {
        Animal animal = Mockito.mock(Animal.class);
        Mockito.when(animal.getIsSelected()).thenReturn(true);
        Assert.assertSame(null, board.getPieceSelected());
        board.putPiece(0, 0, animal);
        Assert.assertSame(animal, board.getPieceSelected());
    }

    @Test
    public void testDeselect() {
        Animal animal = Mockito.mock(Animal.class);
        Mockito.when(animal.getIsSelected()).thenReturn(true, true, false);
        board.putPiece(0, 0, animal);
        board.select(0, 0);
        board.deselect();
        Assert.assertFalse(board.pieceSelected(0, 0));
    }

    @Test
    public void testSelect() {
        Animal animal = Mockito.mock(Animal.class);
        Mockito.when(animal.getIsSelected()).thenReturn(true);
        board.putPiece(0, 0, animal);
        Assert.assertFalse(board.select(1, 1));
        Assert.assertTrue(board.select(0, 0));
    }

    @Test
    public void testIsFree() {
        Assert.assertTrue(board.isFree(0, 0));
        Assert.assertFalse(board.isFree(2, 2));
    }

    @Test
    public void testIsOnEdge() {
        Assert.assertTrue(board.isOnEdge(0, 0));
        Assert.assertFalse(board.isOnEdge(2, 2));
    }

    @Test
    public void testAsABanishedTile() {
        Assert.assertFalse(board.asABanishedTile(0, 0));
        board.changeTile(0, 0, true);
        Assert.assertTrue(board.asABanishedTile(0, 0));
    }

    @Test
    public void testIsInBound() {
        Assert.assertTrue(board.isInBound(0, 0));
        Assert.assertFalse(board.isInBound(10, 10));
    }

    @Test
    public void testMovePiece() {
        Animal animal = Mockito.mock(Animal.class);
        Mockito.when(animal.getCoord()).thenReturn(new int[]{0,0});
        board.putPiece(0, 0, animal);
        board.movePiece(1, 1, 0, 0);
        Assert.assertSame(null, board.getPiece(0, 0));
        Assert.assertSame(animal, board.getPiece(1, 1));
        board.movePiece(1, 1, Orientation.TOP);
        Assert.assertSame(null, board.getPiece(1, 1));
        Assert.assertSame(animal, board.getPiece(1, 0));
    }

    @Test
    public void testGetPieceMoving() {
        Assert.assertSame(null, board.getPieceMoving());
    }

    @Test
    public void testIsPieceIsMoving() {
        Assert.assertFalse(board.isPieceIsMoving());
    }

    @Test
    public void testGetTile() {
        Assert.assertTrue(board.getTile(0, 0) instanceof Tile);
    }
}
