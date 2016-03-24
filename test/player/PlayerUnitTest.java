package player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import siam.player.Camp;
import siam.player.Player;

public class PlayerUnitTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player(Camp.WHITE, "Player");
    }

    @Test
    public void testGetCamp() {
        Assert.assertSame(Camp.WHITE, player.getCamp());
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("Player", player.getName());
    }

    @Test
    public void testPut() {
        Assert.assertEquals(0, player.getPieceOnBoard());
        player.put();
        Assert.assertEquals(1, player.getPieceOnBoard());
    }

    @Test
    public void testBringOut() {
        player.put();
        Assert.assertEquals(1, player.getPieceOnBoard());
        player.bringOut();
        Assert.assertEquals(0, player.getPieceOnBoard());
    }

    @Test
    public void testCanPut() {
        Assert.assertTrue(player.canPut());
        player.put();
        player.put();
        player.put();
        player.put();
        player.put();
        Assert.assertFalse(player.canPut());
    }

    @Test
    public void testCanBringOut() {
        Assert.assertTrue(player.canBringOut());
        player.bringOut();
        Assert.assertFalse(player.canBringOut());
    }

    @Test
    public void testGetPieceOnBoard() {
        Assert.assertEquals(0, player.getPieceOnBoard());
        player.put();
        Assert.assertEquals(1, player.getPieceOnBoard());
    }
}
