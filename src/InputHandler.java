package src;

import java.util.*;
import java.util.stream.Stream;

import static src.Properties.*;

public class InputHandler {

    private static final Scanner scanner = new Scanner(System.in);

    //List with possible letters
    private static final List<Character> correctLetters =
            Stream.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J').toList();


    //Let user input coordinates, if coordinates are correct return input, if not, let user try again
    public static String[] enterShipCoordinates(Field field, Ship ship) {
        while (true) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells): \n",
                    ship.getName(), ship.getSize());

            String input = scanner.nextLine().toUpperCase();
            String[] coordinates = input.split("\\s+");
            try {
                if (coordinates.length != 2) {
                    throw new ShipPlacementException("\nError! Bad parameters");
                }
                if (isCorrectLettersAndDigits(coordinates) && isCorrectCoordinates(coordinates, ship)) {

                    checkThatCellsAreEmpty(coordinates, field);

                    return coordinates;
                }
            } catch (ShipPlacementException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Let user enter shot coordinates, if coordinates correct return coordinates, else let user try again
    public static String enterShotCoordinate() {
        System.out.println("\nTake a shot!");
        while (true) {
            String coordinate = scanner.nextLine().toUpperCase();
            try {
                if (isCorrectLettersAndDigits(coordinate)) {
                    return coordinate;
                }
            } catch (ShipPlacementException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //check that coordinates length is equals to the ship length
    private static boolean isCorrectCoordinates(String[] coordinates, Ship ship) {
        if (isSameLetter(coordinates[0], coordinates[1], ship)) {
            return true;
        } else if (isSameDigits(coordinates[0], coordinates[1], ship)) {
            return true;
        } else {
            throw new ShipPlacementException("\nError! Wrong ship location! Try again:");
        }
    }

    //check that coordinates length is same as ship length
    private static boolean isSameLetter(String coordinate1, String coordinate2, Ship ship) {
        if (coordinate1.charAt(0) == coordinate2.charAt(0)) {
            int start = Integer.parseInt(coordinate1.substring(1));
            int last = Integer.parseInt(coordinate2.substring(1));
            if (Math.abs(start - last) == ship.getSize() - 1) {
                return true;
            } else {
                throw new ShipPlacementException("\nError! Wrong length of the " + ship.getName() + "! Try again:");
            }
        } else {
            return false;
        }
    }

    //check that coordinates length is same as ship length
    private static boolean isSameDigits(String coordinate1, String coordinate2, Ship ship) {
        int start = Integer.parseInt(coordinate1.substring(1));
        int last = Integer.parseInt(coordinate2.substring(1));
        if (start == last) {
            if (Math.abs(coordinate1.charAt(0) - coordinate2.charAt(0)) == ship.getSize() - 1) {
                return true;
            } else {
                throw new ShipPlacementException("\nError! Wrong length of the " + ship.getName() + "! Try again:");
            }
        }
        return false;
    }


    //Check that coordinates are in the board`s borders(for the ship placement)
    private static boolean isCorrectLettersAndDigits(String[] coordinates) {
        int number1 = Integer.parseInt(coordinates[0].substring(1));
        int number2 = Integer.parseInt(coordinates[1].substring(1));

        if (correctLetters.contains(coordinates[0].charAt(0)) &&
                correctLetters.contains(coordinates[1].charAt(0)) &&
                number1 > 0 && number1 <= 10 &&
                number2 > 0 && number2 <= 10) {
            return true;
        } else {
            throw new ShipPlacementException("\nError! You entered the wrong coordinates! Try again:");
        }
    }


    //Check that coordinates are in the board`s borders(for the shot placement)
    private static boolean isCorrectLettersAndDigits(String coordinate) {
        int number1 = Integer.parseInt(coordinate.substring(1));
        if (correctLetters.contains(coordinate.charAt(0)) &&
                number1 > 0 && number1 <= 10) {
            return true;
        } else {
            throw new ShipPlacementException("\nError! You entered the wrong coordinates! Try again:");
        }
    }

    //check that cell and cells around are empty
    private static void checkThatCellsAreEmpty(String[] coordinates, Field field) {
        // if coordinates have same letter(as example: A1 A3)
        if (coordinates[0].charAt(0) == coordinates[1].charAt(0)) {
            int number1 = Integer.parseInt(coordinates[0].substring(1));
            int number2 = Integer.parseInt(coordinates[1].substring(1));
            int start = Math.min(number1, number2);
            int last = Math.max(number1, number2);
            for (int i = start; i <= last; i++) {
                String id = String.valueOf(coordinates[0].charAt(0)) + i;
                isEmptyCellsAround(field.getCellIndexByID(id), field.board);
            }
            //if coordinate have same digit(as example: A1 B1)
        } else {
            int start = Math.min(coordinates[0].charAt(0), coordinates[1].charAt(0));
            int last = Math.max(coordinates[0].charAt(0), coordinates[1].charAt(0));
            for (int i = start; i <= last; i++) {
                String id = Character.toString(i) + coordinates[0].substring(1);
                isEmptyCellsAround(field.getCellIndexByID(id), field.board);
            }
        }
    }

    //check cells around, if at least one have ship mark throw exception
    private static void isEmptyCellsAround(int index, List<Cell> board) {
        isEmptyCell(index, board);
        isEmptyCell(index - 11, board);
        isEmptyCell(index - 10, board);
        isEmptyCell(index - 9, board);
        isEmptyCell(index - 1, board);
        isEmptyCell(index + 1, board);
        isEmptyCell(index + 9, board);
        isEmptyCell(index + 10, board);
        isEmptyCell(index + 11, board);
    }


    private static void isEmptyCell(int index, List<Cell> board) {
        try {
            if (board.get(index).getMark() != FOG.mark) {
                throw new ShipPlacementException("\nError! You placed it too close to another one. Try again:");
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}