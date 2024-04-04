import utils.InputUtils;

import java.io.File;
import java.util.*;

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
                "31 18 13 56 77 | 74 77 10 23 35 67 36 11"
        };

        List<Set<String>> cards = new ArrayList<>();
        List<Set<String>> winningNumbersList = new ArrayList<>();
        List<String> rawCards = testOrRealData ? Arrays.asList(testCards) : input;
        System.out.println("Note: for debugging, cards are currently 0 indexed. If you are looking at the test data the numbers from the website it will be off by one.");

        for (String card : rawCards) {
            String[] parts = card.split("\\|");
            Set<String> winningNumbers = new HashSet<>(Arrays.asList(parts[0].trim().split(" ")));
            Set<String> myNumbers = new HashSet<>(Arrays.asList(parts[1].trim().split(" ")));
            cards.add(myNumbers);
            winningNumbersList.add(winningNumbers);
        }

        long totalCards = cards.size();
        List<Long> cardCounts = new ArrayList<>(Collections.nCopies(cards.size() + 1, 1L));

        for (int cardNumber = 0; cardNumber < cards.size(); cardNumber++) {
            Set<String> myNumbers = cards.get(cardNumber);
            Set<String> winningNumbers = winningNumbersList.get(cardNumber);

            int matches = 0;
            for (String number : myNumbers) {
                if (!number.isEmpty()&& winningNumbers.contains(number)) {
                    matches++;
                }
            }

            // Win copies of subsequent cards equal to the number of matches
            for (int j = 0; j < matches && cardNumber + j < cards.size() - 1; j++) {
                //Jump to the next card and add the number of matches to the count
                int copyIndex = cardNumber + j + 1;
                cardCounts.set(copyIndex, cardCounts.get(cardNumber) + cardCounts.get(copyIndex));
            }
            if(matches > 0) {
                System.out.println("Card " + (cardNumber) + " has matched " + matches + " winning numbers");
            }
        }

        // Count the total number of scratchcards and print summary
        for (int i = 0; i < cardCounts.size(); i++) {
            long count = cardCounts.get(i);
            totalCards += count - 1; // Subtract 1 because we've already counted the original card
            System.out.println("Total count of Card " + (i) + ": " + count);
        }

        System.out.println("Total cards: " + totalCards);
    }

}