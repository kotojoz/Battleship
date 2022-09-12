package src;

import java.util.ArrayList;
import java.util.List;

import static src.Properties.*;

public class Field {

    private final int MAX_IN_ROW = 10;

    protected List<Cell> board;

    public Field() {
        createBoard();
        loadShips();
    }

    protected List<Ship> ships;

    //create list of cells
    private void createBoard() {
        board = new ArrayList<>();
        char MAX_LETTER = 'J';

        for (int i = 'A'; i <= MAX_LETTER; i++) {
            for (int j = 1; j <= MAX_IN_ROW; j++) {
                String id = Character.toString(i) + j;
                board.add(new Cell(id, FOG.mark));
            }
        }
    }

    private void loadShips() {
        ships = new ArrayList<>();
        ships.add(new Ship("Aircraft Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Destroyer", 2));

    }

    public void printBoard() {

        //print numbers on the top of the board
        System.out.print(" ");
        for (int i = 1; i <= MAX_IN_ROW; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        int counter = 0;
        char letter = 'A';

        System.out.print(letter++ + " "); //print letter on the left side of the board
        for (Cell cell : board) {

            //print cell`s mark
            if (counter < MAX_IN_ROW) {
                System.out.print(cell.getMark() + " ");
                counter++;

            } else {
                // if there are 10 cell`s marks in a row, start new line
                System.out.println();
                System.out.print(letter++ + " ");
                System.out.print(cell.getMark() + " ");
                counter = 1;
            }
        }
        System.out.println();
    }

    public void printBoardWithFog() {

        //print numbers on the top of the board
        System.out.print("");
        for (int i = 1; i <= MAX_IN_ROW; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        int counter = 0;
        char letter = 'A';

        System.out.print(letter++ + " "); //print letter on the left side of the board
        for (Cell cell : board) {

            //print cell`s mark
            if (counter < MAX_IN_ROW) {
                if (cell.isVisible()) {
                    System.out.print(cell.getMark() + " ");
                } else {
                    System.out.print(FOG.mark + " ");
                }
                counter++;

            } else {
                // if there are 10 cell`s marks in a row, start new line
                System.out.println();
                System.out.print(letter++ + " ");
                if (cell.isVisible()) {
                    System.out.print(cell.getMark() + " ");
                } else {
                    System.out.print(FOG.mark + " ");
                }
                counter = 1;
            }
        }
        System.out.println();
    }

    public void placeShip(String[] coordinates, Ship ship) {
        //if letters are the same, place ship horizontal
        if (coordinates[0].charAt(0) == coordinates[1].charAt(0)) {
            int number1 = Integer.parseInt(coordinates[0].substring(1));
            int number2 = Integer.parseInt(coordinates[1].substring(1));
            int start = Math.min(number1, number2);
            int last = Math.max(number1, number2);
            for (int i = start; i <= last; i++) {
                String id = String.valueOf(coordinates[0].charAt(0)) + i;
                setMark(id, ship);
            }
        } else {
            //if letters are different, place ship vertical
            int start = Math.min(coordinates[0].charAt(0), coordinates[1].charAt(0));
            int last = Math.max(coordinates[0].charAt(0), coordinates[1].charAt(0));
            for (int i = start; i <= last; i++) {
                String id = Character.toString(i) + coordinates[0].substring(1);
                setMark(id, ship);
            }
        }
    }

    //find cell on the board and mark it as ship
    private void setMark(String id, Ship ship) {
        Cell cell = getCellById(id);
        cell.setMark(SHIP.mark);
        ship.coordinates.add(cell);//set cell in the ship`s coordinate list
    }

    //find cell using id
    protected Cell getCellById(String id) {
        return board.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null);
    }

    //find cell`s index using cell`s id
    protected int getCellIndexByID(String id) {
        Cell cell = board.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null);
        return board.indexOf(cell);
    }

    //return true if no more ships on the board
    protected boolean noMoreShips() {
        return ships.stream().allMatch(Ship::isSunk);
    }
}
