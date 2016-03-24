package level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import siam.level.Animal;
import siam.level.Orientation;
import siam.player.Camp;

public class AnimalUnitTest {

    private Animal animal;

    @Before
    public void setUp() {
        animal = new Animal(10, 10, Camp.WHITE, Orientation.TOP);
    }

    @Test
    public void testGetOrientation() {
        Assert.assertSame(Orientation.TOP, animal.getOrientation());
    }

    @Test
    public void testSetOrientation() {
        animal.setOrientation(Orientation.DOWN);
        Assert.assertSame(Orientation.DOWN, animal.getOrientation());
    }

    @Test
    public void testGetIsSelected() {
        Assert.assertFalse(animal.getIsSelected());
    }

    @Test
    public void testSelected() {
        animal.selected();
        Assert.assertTrue(animal.getIsSelected());
    }

    @Test
    public void testDeselected() {
        animal.selected();
        animal.deselected();
        Assert.assertFalse(animal.getIsSelected());
    }
}
