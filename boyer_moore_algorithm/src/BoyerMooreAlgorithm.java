import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nguyenthanhhuy on 9/29/14.
 */
public class BoyerMooreAlgorithm {

    public static class Result {
        public ArrayList<Integer> matchPositions;
        public int numOfComparisions;

        public Result(ArrayList<Integer> matchPositions, int numOfComparisions) {
            this.matchPositions = matchPositions;
            this.numOfComparisions = numOfComparisions;
        }
    }

    private String pattern;
    /**
     * Pattern length
     */
    private int m;
    /**
     * A map contains the right most occurrence of each character in the pattern.
     */
    private Map<Character, Integer> rightMostOccurrences;
    /**
     * A map contains value (<code>j</code>) for each position key (<code>i</code>),
     * such that <code>j</code> is the right end position of the right most copy
     * of pattern[i..m], which is not a suffix of pattern, and is not preceded by pattern[i-1].
     */
    private Map<Integer, Integer> rightMostSuffixes;
    /**
     * A map contains value (<code>j</code>) for each position key (<code>i</code>),
     * such that <code>j</code> is the length of the largest suffix of pattern[i..m]
     * that is also a prefix of pattern.
     */
    private Map<Integer, Integer> largestPrefixes;

    public BoyerMooreAlgorithm(String pattern) {
        this.pattern = pattern;
        processPattern();
    }

    public Map<Integer, Integer> getRightMostSuffixes() {
        Map<Integer, Integer> clone = new HashMap<Integer, Integer>(rightMostSuffixes.size());
        clone.putAll(rightMostSuffixes);
        return clone;
    }

    public Map<Integer, Integer> getLargestPrefixes() {
        Map<Integer, Integer> clone = new HashMap<Integer, Integer>(largestPrefixes.size());
        clone.putAll(largestPrefixes);
        return clone;
    }

    public Result match(String text) {
        ArrayList<Integer> matchPositions = new ArrayList<Integer>();
        int numOfComparisions = 0;
        int k = m;
        int n = text.length();
        while (k <= n) {
            int i = m;
            int h = k;
            for (; i > 0 && pattern.charAt(i - 1) == text.charAt(h - 1); --i, --h) {
                ++numOfComparisions;
            }
            if (i == 0) {
                matchPositions.add(k);
                k += m - largestPrefixes.get(2);
            } else {
                ++numOfComparisions;
                k += Math.max(getBadCharacterShift(i, text.charAt(h - 1)), getGoodSuffixShift(i));
            }
        }
        return new Result(matchPositions, numOfComparisions);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Pre processing steps
    ////////////////////////////////////////////////////////////////////////////
    private void processPattern() {
        m = pattern.length();
        computeRightMostOccurrences();
        Map<Integer, Integer> commonSuffixes = computeCommonSuffixes(pattern);
        computeRightMostSuffixes(commonSuffixes);
        computeLargestPrefixes(commonSuffixes);
    }

    private void computeRightMostOccurrences() {
        rightMostOccurrences = new HashMap<Character, Integer>();
        char currentChar;
        for (int i = 1; i < m; ++i) {
            currentChar = pattern.charAt(i - 1);
            rightMostOccurrences.put(currentChar, i);
        }
    }

    private void computeRightMostSuffixes(Map<Integer, Integer> commonSuffixes) {
        // rightMostSuffixes.get(i) is largest j such that commonSuffixes.get(j) = m - i + 1.
        // Thus i = m - commonSuffixes.get(j) + 1
        rightMostSuffixes = new HashMap<Integer, Integer>();
        for (int i = 2; i <= m + 1; ++i) {
            rightMostSuffixes.put(i, 0);
        }
        for (int j = 1; j <= m - 1; ++j) {
            int i = m - commonSuffixes.get(j) + 1;
            rightMostSuffixes.put(i, j);
        }
    }

    private void computeLargestPrefixes(Map<Integer, Integer> commonSuffixes) {
        // largestPrefixes.get(i) is the largest j <= |pattern[i..m]| such that commonSuffixes.get(j) = j
        // j <= m - i + 1
        // Thus i <= m - j + 1
        largestPrefixes = new HashMap<Integer, Integer>();
        for (int i = 2; i <= m + 1; ++i) {
            largestPrefixes.put(i, 0);
        }
        for (int j = 1; j <= m - 1; ++j) {
            if (commonSuffixes.get(j) == j) {
                for (int i = 2; i <= m - j + 1; ++i) {
                    largestPrefixes.put(i, j);
                }
            }
        }
    }

    /**
     * Returns a map contains value <code>l</code> for each position key <code>j</code>,
     * such that <code>l</code> is the length of the longest common suffix of pattern[1..j]
     * and pattern itself.
     * The map can be computed from result returned by {@link BoyerMooreAlgorithm#computeCommonPrefixes(String)}.
     */
    public static Map<Integer, Integer> computeCommonSuffixes(String pattern) {
        int m = pattern.length();
        Map<Integer, Integer> commonSuffixes = new HashMap<Integer, Integer>();
        String reversedPattern = new StringBuilder(pattern).reverse().toString();
        Map<Integer, Integer> commonPrefixes = computeCommonPrefixes(reversedPattern);
        for (int i : commonPrefixes.keySet()) {
            // i = m - j + 1
            int j = m - i + 1;
            int l = commonPrefixes.get(i);
            commonSuffixes.put(j, l);
        }
        return commonSuffixes;
    }

    /**
     * Returns a map contains value (<code>l</code>) for each position key (<code>j</code>),
     * such that <code>l</code> is the length of the longest common prefix of pattern[j..m]
     * and pattern itself.
     * @param pattern the pattern to be scanned.
     */
    public static Map<Integer, Integer> computeCommonPrefixes(String pattern) {
        int m = pattern.length();
        Map<Integer, Integer> prefixes = new HashMap<Integer, Integer>();
        prefixes.put(1, 0);
        int s = 1;
        for (int j = 2; j <= m; ++j) {
            int k = j - s + 1;
            int r = s + prefixes.get(s) - 1;
            if (r <= j) {
                prefixes.put(j, naiveScan(pattern, j, 1));
                if (prefixes.get(j) > 0) {
                    s = j;
                }
            } else if (prefixes.get(k) + k < prefixes.get(s)) {
                prefixes.put(j, prefixes.get(k));
            } else {
                int x = naiveScan(pattern, r + 1, r - j + 2);
                prefixes.put(j, r - j + 1 + x);
                s = j;
            }
        }
        prefixes.put(1, m);
        return prefixes;
    }

    /**
     * Returns length of the longest match between pattern's sub-strings
     * begin at two given positions.
     * @param pattern the pattern to be scanned.
     * @param p first position of first sub-string
     * @param q first position of second sub-string
     */
    public static int naiveScan(String pattern, int p, int q) {
        int m = pattern.length();
        int result = 0;
        for (; p <= m && q <= m; ++p, ++q, ++result) {
            if (pattern.charAt(p - 1) != pattern.charAt(q -1)) {
                break;
            }
        }
        return result;
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
        return Math.max(1, i - getRightMostOccurrence(b));
    }

    /**
     * Returns the right most occurrence of a character <code>c</code> in the pattern,
     * or <code>0</code> if the pattern does not contain the character.
     * @param c the character in question.
     */
    private int getRightMostOccurrence(char c) {
        Integer rightMostOcurrence = rightMostOccurrences.get(c);
        return rightMostOcurrence != null ? rightMostOcurrence : 0;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Good suffix shift rule
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Returns the longest shift possible when during an attempt at position <code>j</code> in text,
     * there is an occurrence of pattern[i..m] but there is a mismatch between
     * <code>pattern[i - 1]</code> and <code>text[i + j -1]</code>.
     * @param i
     */
    public int getGoodSuffixShift(int i) {
        if (i == m) {
            return 1;
        }
        if (rightMostSuffixes.get(i) > 0) {
            return m - rightMostSuffixes.get(i);
        }
        return m - largestPrefixes.get(i);
    }

}
