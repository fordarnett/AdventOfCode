package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ford.arnett on 12/15/23
 */
public class InputUtils {
    public static String filePath = "/Users/ford.arnett/intellij/AdventOfCode/src/resources/";
    public static String fileName = "adventday";

    public static File buildFilePath(String filePath, String fileName, String day, boolean testData) {
        String testDataString = testData ? "testdata" : "";
        return new File(filePath + fileName + day + testDataString + ".txt");

    }

    public static ArrayList<String> getInputFromFile(File fullFilePath) {
        ArrayList<String> allInputs = new ArrayList<>();

        Scanner scanner;
        try {
            scanner = new Scanner(fullFilePath);
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
}
