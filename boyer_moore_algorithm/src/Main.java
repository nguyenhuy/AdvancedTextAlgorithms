import java.util.Scanner;

/**
 * Created by nguyenthanhhuy on 10/3/14.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the text (end by 3 consucutive empty lines): ");
        StringBuilder builder = new StringBuilder();
        int newLineCount = 0;
        while (newLineCount < 3) {
            String temp = scanner.nextLine();
            if (temp.isEmpty()) {
                newLineCount++;
            } else {
                builder.append(temp);
                newLineCount = 0;
            }
        }
        String text = builder.toString();

        System.out.print("Enter the pattern: ");
        String pattern = scanner.nextLine();

        BoyerMooreAlgorithm algorithm = new BoyerMooreAlgorithm(pattern);
        BoyerMooreAlgorithm.Result result = algorithm.match(text);

        int numOfOccurrences = result.matchPositions.size();
        System.out.println("Number of occurrences: " + numOfOccurrences);

        if (numOfOccurrences > 0) {
            builder = new StringBuilder("Right end position of pattern occurrences in text: ");
            boolean first = true;
            for (int position : result.matchPositions) {
                if (!first) {
                    builder.append(", ");
                } else {
                    first = false;
                }
                builder.append(position);
            }
            System.out.println(builder.toString());
        }

        System.out.println("Total number of comparisons: " + result.numOfComparisions);
    }

}
