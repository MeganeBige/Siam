package level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import siam.graphics.Screen;
import siam.graphics.Sprite;
import siam.level.Piece;
import siam.player.Camp;

public class PieceUnitTest {

    private Piece piece;

    @Before
    public void setUp() {
        piece = new Piece(10, 10, Camp.WHITE) {
            @Override
            public void render(Screen screen) {

            }
        };
    }

    @Test
    public void testGetCoord() {
        Assert.assertArrayEquals(new int[]{10,10}, piece.getCoord());
    }

    @Test
    public void testGetSprite() {
        Assert.assertSame(null, piece.getSprite());
    }

    @Test
    public void testSetSprite() {
        Sprite sprite = Mockito.mock(Sprite.class);
        piece.setSprite(sprite);
        Assert.assertSame(sprite, piece.getSprite());
    }

    @Test
    public void testGetCamp() {
        Assert.assertSame(Camp.WHITE, piece.getCamp());
    }

    @Test
    public void testSetPosition() {
        piece.setPosition(0,0);
        Assert.assertArrayEquals(new int[]{0,0}, piece.getCoord());
    }
}
