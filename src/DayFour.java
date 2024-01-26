import utils.InputUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayFour {
    static ArrayList<String> input;
    static boolean testOrRealData = false;

    public static void main(String[] args) {
        File file = InputUtils.buildFilePath(InputUtils.filePath, InputUtils.fileName, "4", false);
        input = InputUtils.getInputFromFile(file);

        String[] testCards = {
                "41 48 83 86 17 | 83 86 6 31 17 9 48 53",
                "13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "1 21 53 59 44 | 69 82 63 72 16 21 14 1",
                "41 92 73 84 69 | 59 84 76 51 58 5 54 83",
                "87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "31 18 13 56 72 | 74 77 10 23 35 67 36 11"
        };

        List<String> cards;

        cards = testOrRealData ? Arrays.asList(testCards) : input;

        int totalPoints = 0;

        for (String card : cards) {
            card = card.replaceAll("\\s(\\d) ", "$1 ");
            String[] parts = card.split("\\|");
            List<String> winningNumbers = Arrays.asList(parts[0].trim().split(" "));
            List<String> myNumbers = Arrays.asList(parts[1].trim().split(" "));

            int points = 0;
            int matches = 0;

            for (String number : myNumbers) {
                if (winningNumbers.contains(number)) {
                    matches++;
                    System.out.println("Found number: " + number);
                }
            }

            if (matches > 0) {
                points = (int) Math.pow(2, matches - 1);
            }

            System.out.println("Card: " + card);
            System.out.println("Points: " + points);
            System.out.println();

            totalPoints += points;
        }

        System.out.println("Total points: " + totalPoints);
    }


}
