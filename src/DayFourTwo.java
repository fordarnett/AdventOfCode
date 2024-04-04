import utils.InputUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayFourTwo {
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

        int totalCards = cards.size();
        List<Integer> cardCopies = new ArrayList<>(cards.size());
        for (int i = 0; i < cards.size(); i++) {
            cardCopies.add(0);
        }

        // Process all original and copied scratchcards until no more scratchcards are won
        boolean wonMoreCards = true;
        while (wonMoreCards) {
            wonMoreCards = false;
            for (int i = 0; i < cards.size(); i++) {
                int copies = cardCopies.get(i);
                if (copies > 0) {
                    // This is a copy of a previously won card, so skip it
                    continue;
                }
                String card = cards.get(i);
                card = card.replaceAll("\\s(\\d) ", "$1 ");
                String[] parts = card.split("\\|");
                List<String> winningNumbers = Arrays.asList(parts[0].trim().split(" "));
                List<String> myNumbers = Arrays.asList(parts[1].trim().split(" "));

                int matches = 0;

                for (String number : myNumbers) {
                    if (winningNumbers.contains(number)) {
                        matches++;
                    }
                }

                // Win copies of subsequent cards equal to the number of matches
                for (int j = 0; j < matches && i + j + 1 < cards.size(); j++) {
                    int copyIndex = i + j + 1;
                    cardCopies.set(copyIndex, cardCopies.get(copyIndex) + 1);
                    if (cardCopies.get(copyIndex) > 0) {
                        wonMoreCards = true;
                    }
                }
            }
        }

        // Count the total number of scratchcards
        for (int copies : cardCopies) {
            totalCards += copies;
        }

        System.out.println("Total cards: " + totalCards);
    }
}