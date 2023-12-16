import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ford.arnett on 12/14/23
 */
public class DayTwo {
    public static void main(String[] args) throws FileNotFoundException {
        DayTwo dayTwo = new DayTwo();
        dayTwo.partOne();
        dayTwo.partTwoCalculateMaxCubes();
    }


    private void partOne() throws FileNotFoundException {
        ArrayList<String> input = getInputFromFile();
        Game game;

        int gameTotal = 0;
        for (int i = 0; i < input.size(); i++) {
            game = parseOneGame(input.get(i));
            ArrayList<Integer> maxCubes = new ArrayList<>();
            gameTotal += checkValidityOf(game, 12, 13, 14, maxCubes);
        }

        System.out.println("Total: " + gameTotal);
    }


    public void partTwoCalculateMaxCubes() throws FileNotFoundException {
        DayTwo dayTwo = new DayTwo();
        ArrayList<String> input = dayTwo.getInputFromFile();
        Game game;
        int powerTotal = 0;

        for (int i = 0; i < input.size(); i++) {
            game = dayTwo.parseOneGame(input.get(i));
            ArrayList<Integer> maxCubes = new ArrayList<>();
            dayTwo.checkValidityOf(game, 12, 13, 14, maxCubes);
            powerTotal += maxCubes.get(0) * maxCubes.get(1) * maxCubes.get(2);
        }

        System.out.println("Total: " + powerTotal);
    }


    private int checkValidityOf(Game game, int constraintRed, int constraintGreen, int constraintBlue, ArrayList<Integer> maxCubes) {
        boolean failRed = false, failGreen = false, failBlue = false;
        int redMax = 0, greenMax = 0, blueMax = 0;

        for (int i = 0; i < game.red.size(); i++) {
            if (game.red.get(i) > constraintRed) {
                failRed = true;
            }
            if (redMax < game.red.get(i)) {
                redMax = game.red.get(i);
            }
        }

        for (int i = 0; i < game.green.size(); i++) {
            if (game.green.get(i) > constraintGreen) {
                failGreen = true;
            }
            if (greenMax < game.green.get(i)) {
                greenMax = game.green.get(i);
            }
        }

        for (int i = 0; i < game.blue.size(); i++) {
            if (game.blue.get(i) > constraintBlue) {
                failBlue = true;
            }
            if (blueMax < game.blue.get(i)) {
                blueMax = game.blue.get(i);
            }
        }

        maxCubes.add(redMax);
        maxCubes.add(greenMax);
        maxCubes.add(blueMax);

        if (!(failRed || failGreen || failBlue)) {
            return game.gameNumber;
        } else {
            return 0;
        }


    }


    private ArrayList<String> getExampleInput() {
        ArrayList<String> input = new ArrayList<>();
        input.add("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
        input.add("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue");
        input.add("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red");
        input.add("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red");
        input.add("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");

        return input;

    }


    public Game parseOneGame(String singleGame) {
        Game game = new Game();
        game.gameNumber = Integer.parseInt(singleGame.split(":")[0].replaceAll("\\D", ""));

        singleGame.split(":")[1].split(";")[0].split(",");
        String pullsOnly = singleGame.split(":")[1];

        for (String pull : pullsOnly.split(";")) {
            for (String singleColor : pull.split(",")) {
                game.assignPull(singleColor);
            }
        }

        // split the string into game sections
        /*List<String> pairs = Arrays.stream(colorsOnly)
                // split each game section into pairs of number and color
                .flatMap(game -> Arrays.stream(game.split(";")[1].trim().split(",")))
                // remove any leading or trailing whitespace
                .map(String::trim)
                .collect(Collectors.toList());

        for (String pair : pairs) {
            System.out.println(pair);
        } */

        return game;

    }



    public class Game {
        public int gameNumber;
        private ArrayList<Integer> blue = new ArrayList<>();
        private ArrayList<Integer> green = new ArrayList<>();
        private ArrayList<Integer> red = new ArrayList<>();


        public void assignPull(String singlePull) {
            if (singlePull.contains("blue")) {
                blue.add(Integer.parseInt(singlePull.replaceAll("\\D", "")));
            } else if (singlePull.contains("green")) {
                green.add(Integer.parseInt(singlePull.replaceAll("\\D", "")));
            } else if (singlePull.contains("red")) {
                red.add(Integer.parseInt(singlePull.replaceAll("\\D", "")));
            }
        }
    }


    private ArrayList<String> getInputFromFile() throws FileNotFoundException {
        ArrayList<String> allInputs = new ArrayList<>();

        String realData = "/Users/ford.arnett/intellij/AdventOfCode/src/resources/adventday2.txt";
        String testData = "/Users/ford.arnett/Library/Application Support/JetBrains/IdeaIC2023.2/scratches/adventday2testdata.txt";
        Scanner scanner = new Scanner(new File(realData));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            allInputs.add(line);
        }

        return allInputs;

    }
}