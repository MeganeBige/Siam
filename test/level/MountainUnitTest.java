package level;

import org.junit.Before;
import siam.level.Mountain;
import siam.player.Camp;

public class MountainUnitTest {

    private Mountain mountain;

    @Before
    public void setUp() {
        mountain = new Mountain(10, 10, Camp.WHITE);
    }
}
