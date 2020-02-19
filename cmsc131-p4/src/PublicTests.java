import static org.junit.Assert.*;

import org.junit.Test;

public class PublicTests {

    @Test
    public void testLargestPowerOfTwoLessThan() {
        assertEquals(1, Project4.largestPowerOfTwoLessThan(2));
        assertEquals(2, Project4.largestPowerOfTwoLessThan(3));
        assertEquals(4, Project4.largestPowerOfTwoLessThan(7));
    }
    @Test
    public void testCollatzStoppingTime() {
        assertEquals(0, Project4.collatzStoppingTime(1));
        assertEquals(1, Project4.collatzStoppingTime(2));
        assertEquals(2, Project4.collatzStoppingTime(4));
        assertEquals(7, Project4.collatzStoppingTime(3));
    }

    @Test
    public void testIsPerfect() {
        assertEquals("6 is perfect", true, Project4.isPerfect(6));
        assertEquals("4 is not perfect", false, Project4.isPerfect(4));
        assertEquals("28 is perfect", true, Project4.isPerfect(28));  
    }
    
    @Test
    public void testIsPrime() {
        assertEquals("3 is prime", true, Project4.isPrime(3));
        assertEquals("4 is not prime", false, Project4.isPrime(4));
        assertEquals("11*13 is not prime", false, Project4.isPrime(11*13));
        assertEquals("17 is prime", true, Project4.isPrime(17)); 
    }
    
    @Test
    public void testPrimesSmallerThan() {
        assertEquals("No primes less than 2", 0,  Project4.primesSmallerThan(2));
        assertEquals("4 primes less than 10", 4,  Project4.primesSmallerThan(10));
    }
    
    @Test
    public void testLunhCheckDigit() {
        assertEquals(3, Project4.lunhCheckDigit(7992739871L));
        assertEquals(1, Project4.lunhCheckDigit(4111_1111_1111_111L));
    }
    
    @Test public void testGradeDistribution() {
        assertArrayEquals(new int[] {2, 1, 2, 2},
                Project4.gradeDistribution( new int[] {95, 85, 69, 52, 90, 75, 62}, 
                        new int[] { 90, 82, 68 } ));
        
    }
}
