import utils.InputUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ford.arnett on 12/15/23
 */
public class DayThree {
    static ArrayList<String> input;
    private final List<Character> notSymbols = Arrays.asList('.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private final List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private final Character gearSymbol = '*';
    int rowLength = 0, columnLength = 0;
    ArrayList<Symbol> allGearSymbols = new ArrayList<>();

    public static void main(String[] args) {
        File file = InputUtils.buildFilePath(InputUtils.filePath, InputUtils.fileName, "3", false);
        input = InputUtils.getInputFromFile(file);
        DayThree dayThree = new DayThree();
        dayThree.rowLength = input.size();
        //Non squares will destroy this.
        dayThree.columnLength = input.get(0).length();

        dayThree.partOneAndTwo();
    }

    private void partOneAndTwo() {
        int enginePartTotal = 0;

        for(int i = 0; i < input.size(); i++) {
            List<Character> previousLine, currentLine, nextLine;

            currentLine = parseLine(input.get(i));
            if (i == 0) {
                previousLine = null;
            } else {
                previousLine = parseLine(input.get(i - 1));
            } if (i == input.size() - 1) {
                nextLine = null;
            } else {
                nextLine = parseLine(input.get(i + 1));
            }

            EnginePartBuffer enginePartBuffer = new EnginePartBuffer(previousLine, currentLine, nextLine, i);
            List<EngineNumber> engineNumbers = parseLineForNumbers(enginePartBuffer);
            for(EngineNumber engineNumber : engineNumbers) {
                if (engineNumber.currentNumber != -1) {
                    enginePartBuffer.currentNumber = engineNumber.currentNumber;
                    int numberLength = engineNumber.currentNumberLength;
                    if (checkForAdjacentSymbols(enginePartBuffer, enginePartBuffer.currentRow, engineNumber.currentNumberColumn, numberLength)) {
                        System.out.println("Found an engine part number : " + engineNumber.currentNumber);
                        enginePartTotal += engineNumber.currentNumber;
                    }
                }

            }

        }

        System.out.println("Total: " + enginePartTotal);
        System.out.println("Total Gear Value : " + doPartTwo());


    }

    private long doPartTwo() {
        //Don't get confused here, it's taking each element of the array and comparing to every other element of the array, looking for exactly two matches.
/*        List<Symbol> allMatchedGears = allGearSymbols.stream()
                .filter(firstSetOfSymbols -> allGearSymbols.stream()
                            .filter(secondSetOfSymbols -> firstSetOfSymbols.row == secondSetOfSymbols.row &&
                                    firstSetOfSymbols.column == secondSetOfSymbols.column).count() == 2)
                .collect(Collectors.toList());*/
        //              .mapToInt(x -> x.relatedEngineNumber)
        //              .reduce(1, (x, y) -> x * y);

        //Got tired in here. But had to get the job done. It's a mess

        long value = 0;
        for (int i = 0; i < allGearSymbols.size(); i++) {
            Symbol symbol = allGearSymbols.get(i);
            for (int j = i; j < allGearSymbols.size(); j++) {
                Symbol symbol2 = allGearSymbols.get(j);
                if (symbol.row == symbol2.row && symbol.column == symbol2.column && !symbol.equals(symbol2)) {
                    value += (long) symbol.relatedEngineNumber * symbol2.relatedEngineNumber;
                    System.out.println("Found a gear at row " + symbol.row + " and column " + symbol.column + " with a value of " + symbol.relatedEngineNumber);
                    System.out.println("Found it's pair at row " + symbol2.row + " and column " + symbol2.column + " with a value of " + symbol2.relatedEngineNumber);

                }
            }

        }

/*        long value = 0;
        for(int i = 0; i < allMatchedGears.size(); i=i+2) {
            Symbol symbol = allMatchedGears.get(i);
            Symbol symbol2 = allMatchedGears.get(i+1);
            value += (long) allMatchedGears.get(i).relatedEngineNumber * allMatchedGears.get(i+1).relatedEngineNumber;
            }*/

        return value;
    }

    private List<Character> parseLine(String line) {
        return line.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    private ArrayList<EngineNumber> parseLineForNumbers(EnginePartBuffer enginePartBuffer) {
        ArrayList<EngineNumber> engineNumbers = new ArrayList<>();
        boolean isNewNumber = true;

        int numberLength;
        EngineNumber engineNumber = null;
        for (int i = 0; i < enginePartBuffer.currentLine.size(); i++) {
            Character currentChar = enginePartBuffer.currentLine.get(i);
            if (numbers.contains(currentChar) && isNewNumber) {
                engineNumber = new EngineNumber();
                engineNumbers.add(engineNumber);
                numberLength = findNumLength(enginePartBuffer, i);
                engineNumber.currentNumber = Integer.parseInt(enginePartBuffer.currentLine.subList(i, i + numberLength).stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining()));
                engineNumber.currentNumberLength = numberLength;
                engineNumber.currentNumberColumn = i;

                isNewNumber = false;
            } else if (!numbers.contains(currentChar)) {
                isNewNumber = true;
            }

        }

        return engineNumbers;
    }

    private int findNumLength(EnginePartBuffer enginePartBuffer, int i) {
        int lookingForNumEnd = i;
        while (lookingForNumEnd < enginePartBuffer.currentLine.size() && numbers.contains(enginePartBuffer.currentLine.get(lookingForNumEnd))) {
            lookingForNumEnd++;
        }
        return lookingForNumEnd - i;
    }

    private boolean checkForAdjacentSymbols(EnginePartBuffer enginePartBuffer, int rowOfFoundNumber, int columnOfFoundNumber, int numberLength) {
        boolean leftSafe, rightSafe, upSafe, downSafe;

        leftSafe = checkIfValidCoordinates(rowOfFoundNumber, columnOfFoundNumber - 1);
        rightSafe = checkIfValidCoordinates(rowOfFoundNumber, columnOfFoundNumber + numberLength);
        upSafe = checkIfValidCoordinates(rowOfFoundNumber - 1, columnOfFoundNumber);
        downSafe = checkIfValidCoordinates(rowOfFoundNumber + 1, columnOfFoundNumber);

        int columnOffset = leftSafe ? -1 : 0;
        int columnOffsetRight = rightSafe ? 0 : -1;

        int columnToStartChecking = columnOfFoundNumber + columnOffset;
        int columnToStopChecking = columnOfFoundNumber + numberLength + columnOffsetRight;
        for (int i = columnToStartChecking; i <= columnToStopChecking; i++) {
            // check up
            if (upSafe) {
                if (checkBufferSpotForSymbol(enginePartBuffer.previousLine, i, enginePartBuffer.currentNumber, enginePartBuffer.currentRow - 1)) return true;
            }
            if(downSafe) {
                if (checkBufferSpotForSymbol(enginePartBuffer.nextLine, i, enginePartBuffer.currentNumber, enginePartBuffer.currentRow + 1)) return true;
            }
            if(leftSafe) {
                if (checkBufferSpotForSymbol(enginePartBuffer.currentLine, i, enginePartBuffer.currentNumber, enginePartBuffer.currentRow)) return true;
            }
            if(rightSafe) {
                if (checkBufferSpotForSymbol(enginePartBuffer.currentLine, i, enginePartBuffer.currentNumber, enginePartBuffer.currentRow)) return true;
            }
        }


        return false;
    }

    private boolean checkBufferSpotForSymbol(List<Character> enginePartBuffer, int i, int currentNumber, int rowOfSymbol) {
        Character potentialSymbol = enginePartBuffer.get(i);

        boolean symbolFound = !notSymbols.contains(potentialSymbol);
        if(symbolFound && enginePartBuffer.get(i) == gearSymbol) {
            Symbol foundGearSymbol = new Symbol();
            foundGearSymbol.column = i;
            foundGearSymbol.row = rowOfSymbol;
            foundGearSymbol.relatedEngineNumber = currentNumber;
            foundGearSymbol.symbol = gearSymbol;
            allGearSymbols.add(foundGearSymbol);
        }
        return symbolFound;
    }

    private boolean checkIfValidCoordinates(int row, int column) {
        return row >= 0 && row < rowLength && column >= 0 && column < columnLength;
    }


    public static class EnginePartBuffer {
        List<Character> previousLine = new ArrayList<>();
        List<Character> currentLine = new ArrayList<>();
        List<Character> nextLine = new ArrayList<>();
        private final int currentRow;
        int currentNumber = -1;



        public EnginePartBuffer(List<Character> previousLine, List<Character> currentLine, List<Character> nextLine, int currentRow) {
            this.previousLine = previousLine;
            this.currentLine = currentLine;
            this.nextLine = nextLine;
            this.currentRow = currentRow;
        }
    }

    public static class EngineNumber {
        int currentNumber = -1;
        int currentNumberLength = -1;
        int currentNumberColumn = -1;
    }

    public static class Symbol {
        public int relatedEngineNumber;
        Character symbol;
        int row;
        int column;
    }

}
