import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class CodeAdventDay1 {
    static ArrayList <String> cypherLines;

    public static void main(String[] args) throws FileNotFoundException {
        CodeAdventDay1 codeAdventDay1 = new CodeAdventDay1();

        cypherLines = codeAdventDay1.getInput(true);
        int result = codeAdventDay1.calculateCalibrationValue();
        System.out.println("********The calibration value is " + result + "********");

    }

    private int calculateCalibrationValue() {
        int calibrationValue = 0;
        
        for (String cypherLine : cypherLines) {
            int cypherValueForOneLine = 0;
            //I'm not good with regex. I stole this from chatGPT. It's supposed to split off the numbers from the text. 
            String[] arr = cypherLine.trim().replaceAll("^\\D+", "").split("\\D+");
            if (arr.length != 0) {
                String lastNumber = arr[arr.length - 1];
                String firstDigit = String.valueOf(arr[0].charAt(0));
                String lastDigit = String.valueOf(lastNumber.charAt(lastNumber.length() - 1));
                cypherValueForOneLine = Integer.parseInt(firstDigit + lastDigit);
            } else {
                System.out.println("Error: The amount of numbers on the line (" + arr.length + ") is funky and something likely went wrong with the parsing on this line");
            }

            //System.out.println("Line: " + cypherLine + " has a calibration value of " + cypherValueForOneLine + " and a total calibration value of " + calibrationValue);
            System.out.println(cypherValueForOneLine);
            calibrationValue += cypherValueForOneLine;
        }

        return calibrationValue;
    }


    private ArrayList<String> getInput(boolean replace) throws FileNotFoundException {
        ArrayList<String> cyperLines = new ArrayList<>();

        String realData = "/Users/ford.arnett/intellij/AdventOfCode/src/resources/adventday1.txt";
        String testData = "/Users/ford.arnett/intellij/AdventOfCode/src/resources/adventday1testdata.txt";
        Scanner scanner = new Scanner(new File(realData));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (replace) {
                line = replaceStringWithNumber(line);
            }
            cyperLines.add(line);
        }

        return cyperLines;

    }

    /**
     * Part two of the challenge. Replace the lettered numbers with digital numbers
     *
     * @param line
     * @return
     */
    private static String replaceStringWithNumber(String line) {
        //eightwo twoeight oneight
        //eight2o t2oeight o1eight
        line = line.replace("one", "o1e");
        line = line.replace("two", "t2o");
        line = line.replace("three", "t3e");
        line = line.replace("four", "f4r");
        line = line.replace("five", "f5e");
        line = line.replace("six", "s6x");
        line = line.replace("seven", "s7n");
        line = line.replace("eight", "e8t");
        line = line.replace("nine", "n9e");
        line = line.replace("zero", "z0o");

        
        return line;
    }
}