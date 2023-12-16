import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by ford.arnett on 12/15/23
 */
public class DayTwoOptimized {
    boolean realOrTest = true;
    ArrayList<String> input = getInput();

    public static void main(String[] args) {
        DayTwoOptimized dayTwo = new DayTwoOptimized();
        Game.setConstraints(12,13,14);

        dayTwo.partOneStream();
        dayTwo.partTwoCalculateMaxCubes();
    }

    private void partOneStream() {
        DayTwoOptimized.Game game;

        int gameTotal = 0;
        for (String games : input) {
            game = parseOneGame(games);
            gameTotal += getGameNumberIfGameValid(game);
        }

        System.out.println("Part one total: " + gameTotal);
    }

    public void partTwoCalculateMaxCubes()  {
        DayTwoOptimized dayTwo = new DayTwoOptimized();
        Game game;
        int powerTotal = 0;


        for(int i = 0; i < input.size(); i++) {
            game = dayTwo.parseOneGame(input.get(i));
            powerTotal += dayTwo.calculateCubePower(game);
        }

        System.out.println("Power total: " + powerTotal);
    }

    private int getGameNumberIfGameValid(Game game) {
        boolean gameValid = game.allCubeCounts.stream()
                .allMatch(list -> list.stream()
                        .allMatch(oneCubeCount -> oneCubeCount.numberOfCubes <= oneCubeCount.constraint));

        return gameValid ? game.gameNumber : 0;
    }

    private int calculateCubePower(Game game) {
            return game.allCubeCounts.stream()
                    .mapToInt(list -> list.stream()
                            .mapToInt(oneCubeCount -> oneCubeCount.numberOfCubes).max().orElse(0)
                    ).reduce(1, (x, y) -> x * y);
    }

    public Game parseOneGame(String singleGame) {
        Game game = new Game();
        game.gameNumber = Integer.parseInt(singleGame.split(":")[0].replaceAll("\\D", ""));


        // split the string into game sections
        Arrays.stream(singleGame.split(":")[1].split(";"))
                // split each game section into pairs of number and color
                .flatMap(singleGameColorsAndNumbers -> Arrays.stream(singleGameColorsAndNumbers.split(",")))
                .map(String::trim)
                .forEach(game::loadCubeCountIntoGame);


        return game;
    }

    private ArrayList<String> getInput() {
        return realOrTest ? getInputFromFile() : getExampleInput();
    }


    private ArrayList<String> getInputFromFile() {
        ArrayList<String> allInputs = new ArrayList<>();

        String realData = "/Users/ford.arnett/intellij/AdventOfCode/src/resources/adventday2.txt";
        Scanner scanner;
        try {
            scanner = new Scanner(new File(realData));
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Unable to read input. " + e.getMessage());
            return allInputs;
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            allInputs.add(line);
        }

        return allInputs;

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

    /**
     * A game consists of one or more pulls, each of which can have one or more cube counts
     * Created by ford.arnett on 12/15/23
     */
    public static class Game {
        private static int redConstraint;
        private static int greenConstraint;
        private static int blueConstraint;

        public int gameNumber;
        private ArrayList<OneCubeCount> red = new ArrayList<>(), green = new ArrayList<>(), blue = new ArrayList<>();
        private ArrayList<ArrayList<OneCubeCount>> allCubeCounts = new ArrayList<>();

        public Game() {
            allCubeCounts.add(red);
            allCubeCounts.add(green);
            allCubeCounts.add(blue);
        }

        public void loadCubeCountIntoGame(String cubeCount) {
            if(cubeCount.contains("blue")) {
                blue.add(new OneCubeCount("blue", Integer.parseInt(cubeCount.replaceAll("\\D", "")), blueConstraint));
            } else if(cubeCount.contains("green")) {
                green.add(new OneCubeCount("green", Integer.parseInt(cubeCount.replaceAll("\\D", "")), greenConstraint));
            } else if(cubeCount.contains("red")) {
                red.add(new OneCubeCount("red", Integer.parseInt(cubeCount.replaceAll("\\D", "")), redConstraint));
            }
        }

        public static void setConstraints(int redConstraint, int greenConstraint, int blueConstraint) {
            Game.redConstraint = redConstraint;
            Game.greenConstraint = greenConstraint;
            Game.blueConstraint = blueConstraint;
        }


    }

    /**
     * One cube count consists of one color and the number of cubes of that color. One pull may have multiple colors and the number of cubes of each color
     * Created by ford.arnett on 12/15/23
     */
    static class OneCubeCount {
        String color;
        int numberOfCubes;
        int constraint;

        public OneCubeCount(String color, int numberOfCubes, int constraint) {
            this.color = color;
            this.numberOfCubes = numberOfCubes;
            this.constraint = constraint;
        }
    }

}
