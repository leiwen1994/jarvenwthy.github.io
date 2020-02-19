import static org.junit.Assert.*;

import org.junit.Test;

public class PublicTests {

    @Test
    public void testUnique2() {
        assertEquals(1, Functions.countUnique(1, 1));
        assertEquals(2, Functions.countUnique(1, 2));
    }

    @Test
    public void testUnique3() {
        assertEquals(1, Functions.countUnique(1, 1, 1));
    }

    @Test
    public void testMax() {
        assertEquals(3, Functions.countUnique(1, 2, 3));
    }

    @Test
    public void testCountFactors() {
        assertEquals(3, Functions.countFactors(6));
        assertEquals(5, Functions.countFactors(12));
    }

    @Test
    public void testCountAdjacentIncreasing() {
        assertEquals(3, Functions.countAdjacentIncreasing(new int[] { 1, 1, 2, 4, 2, 3 }));
    }

}
