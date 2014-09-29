import java.util.HashMap;

/**
 * Created by nguyenthanhhuy on 9/29/14.
 */
public class BoyerMooreAlgorithm {
    private String pattern;
    // Pattern length
    private int m;
    private HashMap<Character, Integer> rightMostOccurrences;

    public BoyerMooreAlgorithm(String pattern) {
        this.pattern = pattern;
        processPattern();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Pre processing steps
    ////////////////////////////////////////////////////////////////////////////
    private void processPattern() {
        m = pattern.length();
        computeRightMostOccurrences();
    }

    private void computeRightMostOccurrences() {
        rightMostOccurrences = new HashMap<Character, Integer>();
        char currentChar;
        for (int i = 1; i < m; ++i) {
            currentChar = pattern.charAt(i - 1);
            rightMostOccurrences.put(currentChar, i);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Bad character shift rule
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Returns the longest shift possible when a mismatch occurs
     * between <code>pattern[i]</code> and <code>text[i+j] = b</code>
     * during an attempt at position <code>j</code> in text.
     * @param i mismatch position in pattern
     * @param b mismatch character in text
     * @return <code>i - k</code> (with <code>k</code> is the right most occurence
     * of <code>b</code> in <code>pattern[1..m-1]</code>) if k < i,
     * or <code>1</code> if <code>k >= i</code>,
     * or <code>i</code> if k = 0.
     */
    public int getBadCharacterShift(int i, char b) {
        return Math.max(1, i - getRightMostOcurrence(b));
    }

    /**
     * Returns the right most occurrence of a character <code>c</code> in the pattern,
     * or <code>0</code> if the pattern does not contain the character.
     * @param c the character in question.
     * @return the right most position of <code>c</code> in the pattern,
     * or <code>0</code> if <code>c</code> does not occur in the pattern.
     */
    private int getRightMostOcurrence(char c) {
        Integer rightMostOcurrence = rightMostOccurrences.get(c);
        return rightMostOcurrence != null ? rightMostOcurrence : 0;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Good suffix shift rule
    ////////////////////////////////////////////////////////////////////////////

}
