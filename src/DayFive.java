import utils.InputUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DayFive {
    static ArrayList<String> input;
    static boolean testOrRealData = false;




    public static Map<Integer, Integer> decodeCypher(int[][] input) {
        Map<Integer, Integer> result = new HashMap<>();
        for (int[] line : input) {
            int destinationStart = line[0];
            int sourceStart = line[1];
            int rangeLength = line[2];
            for (int i = 0; i < rangeLength; i++) {
                result.put(sourceStart + i, destinationStart + i);
            }
        }
        return result;
    }

    public static int getSoilNumber(Map<Integer, Integer> map, int seedNumber) {
        return map.getOrDefault(seedNumber, seedNumber);
    }

    public static void main(String[] args) {

        File file = InputUtils.buildFilePath(InputUtils.filePath, InputUtils.fileName, "4", false);
        input = InputUtils.getInputFromFile(file);


        int[][] input = {{50, 98, 2}, {52, 50, 48}};
        Map<Integer, Integer> result = decodeCypher(input);
        System.out.println(getSoilNumber(result, 79)); // prints 55
        System.out.println(getSoilNumber(result, 14)); // prints 10
        System.out.println(getSoilNumber(result, 55)); // prints 50
        System.out.println(getSoilNumber(result, 13)); // prints 51
    }

}
