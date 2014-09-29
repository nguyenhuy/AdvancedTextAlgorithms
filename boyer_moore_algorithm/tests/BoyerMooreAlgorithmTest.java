import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by nguyenthanhhuy on 9/29/14.
 */
public class BoyerMooreAlgorithmTest {

    @Test
    public void getBadCharacterShift_rightMostOccurrenceLessThanMismatchPosition() {
        String pattern = "pedaled";
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        // Right most occurrence of `p` is 1, so shift 6 - 1.
        assertEquals(5, algorithm.getBadCharacterShift(6, 'p'));
    }

    @Test
    public void getBadCharacterShift_rightMostOccurrenceEqualsMismatchPosition() {
        String pattern = "pedaled";
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        // Right most occurrence of `p` is 1, so shift 1.
        assertEquals(1, algorithm.getBadCharacterShift(1, 'p'));
    }

    @Test
    public void getBadCharacterShift_rightMostOccurrenceGreaterThanMismatchPosition() {
        String pattern = "pedaled";
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        // Right most occurrence of `e` is 6, so shift 1.
        assertEquals(1, algorithm.getBadCharacterShift(2, 'e'));
    }

    @Test
    public void getBadCharacterShift_noOccurrences() {
        String pattern = "pedaled";
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        int mismatchPosition = 6;
        assertEquals(mismatchPosition, algorithm.getBadCharacterShift(mismatchPosition, 'h'));
    }

    @Test
    public void getBadCharacterShift_mismatchAtLastPositionInPattern() {
        String pattern = "pedaled";
        int m = pattern.length();
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        // Right most occurrence of `d` in pattern[1..6] is 3, so shift m - 3 = 7 - 3.
        assertEquals(4, algorithm.getBadCharacterShift(m, 'd'));
    }

}
