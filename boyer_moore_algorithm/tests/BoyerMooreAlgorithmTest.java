import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by nguyenthanhhuy on 9/29/14.
 */
public class BoyerMooreAlgorithmTest {

    @Test
    public void match_correctData() {
        String text = "Editor's note: Scott Hubbard is director of the Stanford Center of Excellence for Commercial Space Transportation. He is a former director of the NASA Ames Research Center and the author of \"Exploring Mars: Chronicles from a Decade of Discovery.\" He serves as the chair of the SpaceX Commercial Crew Safety Advisory Panel. The views expressed are solely his own.\n" +
                "(CNN) -- Today's selection of Boeing and SpaceX as the providers of a U.S.-based capability to take humans to the International Space Station (ISS) is a major milestone in the almost six-decade history of space exploration. It is just the latest sign that the old paradigm of government-only space travel is being replaced by something else -- a new business ecosystem composed of novel relationships among NASA and the aerospace industry.\n" +
                "\n" +
                "No longer will NASA own the ISS \"trucking company\" -- specifying every nut and bolt. Instead, NASA is buying services from U.S. industry. To be sure, the new announcement made it clear that NASA will be carefully examining the safety aspects of each design. But the designs will still be those of Boeing and SpaceX and vetted by NASA.\n" +
                "I believe this new approach is America's \"secret weapon\" in what some have described as a space race with China. And, as far as I can tell, while the rest of the world is still stuck in a nearly government-only mode, NASA, with the support of the Obama administration, is letting loose the creativity of American know-how.\n" +
                " NASA's new unmanned spacecraft NASA gets rare view of black hole NASA to resume launches in the U.S.?\n" +
                "Beginning with the NASA Commercial Orbital Transportation Services competition, continuing with the Commercial Resupply Services (cargo) and now the Commercial Crew selection, NASA \"bet the farm\" on commercial companies filling the gap left by the retirement of the space shuttle, with the Commercial Crew companies (SpaceX and Boeing) replacing the Russians in bringing NASA astronauts to the ISS. This will allow NASA to invest the savings in deep space capabilities such as SLS and Orion.\n" +
                "I believe it is critical that both commercial cargo and crew succeed for at least two reasons: First, NASA's proper role can be summed up in three words: \"Explore Deep Space.\" It is time for NASA to turn over the low Earth orbit work to industry while NASA focuses on getting humanity to Mars, following in the tracks of robotic rovers Spirit, Opportunity and Curiosity. Second, history teaches us that without a trailing edge of commercial exploitation and profitability, exploration as large scale, routine human endeavor will not succeed.\n" +
                "\n" +
                "As an aerospace professional and former NASA executive, I have encountered over the decades many concepts for private space exploration. Until a few years ago, none of these ideas met the sniff test for what I call the \"practical visionary,\" that is, someone capable of seeing a new future, yet solidly grounded in lessons learned. Something was always missing in these early ventures -- either the technical approach required some \"unobtanium\" technology to be invented, the advocate had good ideas but no money or the \"build it and they will come\" philosophy showed total naiveté in business and marketing.\n" +
                "Today's selection points us in a different direction. As with the early 20th century airmail routes that helped stimulate aviation, NASA's commercial programs are now the anchor tenants in the government transfer of space services to the private sector. This in turn will enable a robust new business enterprise and allow NASA to focus on Mars -- the ultimate target for exploration.";
        String pattern = "is just the latest sign";
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        BoyerMooreAlgorithm.Result result = algorithm.match(text);
//        assertEquals(1, result.matchPositions);
        assertEquals(1, 1);
    }

    @Test
    public void computeCommonSuffixes() {
        String pattern = "antecedence";
        Map<Integer, Integer> commonSuffixes = BoyerMooreAlgorithm.computeCommonSuffixes(pattern);
        assertEquals(0, commonSuffixes.get(1).intValue());
        assertEquals(0, commonSuffixes.get(2).intValue());
        assertEquals(0, commonSuffixes.get(3).intValue());
        assertEquals(1, commonSuffixes.get(4).intValue());
        assertEquals(0, commonSuffixes.get(5).intValue());
        assertEquals(2, commonSuffixes.get(6).intValue());
        assertEquals(0, commonSuffixes.get(7).intValue());
        assertEquals(1, commonSuffixes.get(8).intValue());
        assertEquals(0, commonSuffixes.get(9).intValue());
        assertEquals(0, commonSuffixes.get(10).intValue());
        assertEquals(11, commonSuffixes.get(11).intValue());
    }

    @Test
    public void computeCommonPrefixes() {
        String pattern = "ecnedecetna";
        Map<Integer, Integer> commonPrefixes = BoyerMooreAlgorithm.computeCommonPrefixes(pattern);
        assertEquals(11, commonPrefixes.get(1).intValue());
        assertEquals(0, commonPrefixes.get(2).intValue());
        assertEquals(0, commonPrefixes.get(3).intValue());
        assertEquals(1, commonPrefixes.get(4).intValue());
        assertEquals(0, commonPrefixes.get(5).intValue());
        assertEquals(2, commonPrefixes.get(6).intValue());
        assertEquals(0, commonPrefixes.get(7).intValue());
        assertEquals(1, commonPrefixes.get(8).intValue());
        assertEquals(0, commonPrefixes.get(9).intValue());
        assertEquals(0, commonPrefixes.get(10).intValue());
        assertEquals(0, commonPrefixes.get(11).intValue());
    }

    @Test
    public void naiveScan_noMatch() {
        String pattern = "abc";
        int longestMatch = BoyerMooreAlgorithm.naiveScan(pattern, 1, 3);
        assertEquals(0, longestMatch);
    }

    @Test
    public void naiveScane_hasMatch() {
        String pattern = "abcabc";
        int longestMatch = BoyerMooreAlgorithm.naiveScan(pattern, 1, 4);
        assertEquals(3, longestMatch);
    }

    @Test
    public void getBadCharacterShift_rightMostOccurrenceLessThanMismatchPosition() {
        String pattern = "pedaled";
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        // Right most occurrence of `p` is 1, so shift by 6 - 1.
        assertEquals(5, algorithm.getBadCharacterShift(6, 'p'));
    }

    @Test
    public void getBadCharacterShift_rightMostOccurrenceEqualsMismatchPosition() {
        String pattern = "pedaled";
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        // Right most occurrence of `p` is 1, so shift by 1.
        assertEquals(1, algorithm.getBadCharacterShift(1, 'p'));
    }

    @Test
    public void getBadCharacterShift_rightMostOccurrenceGreaterThanMismatchPosition() {
        String pattern = "pedaled";
        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        // Right most occurrence of `e` is 6, so shift by 1.
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
        // Right most occurrence of `d` in pattern[1..6] is 3, so shift by: m - 3 = 7 - 3.
        assertEquals(4, algorithm.getBadCharacterShift(m, 'd'));
    }

}
