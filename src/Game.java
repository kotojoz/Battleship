package src;

import java.util.Scanner;

import static src.Properties.*;

public class Game {

    protected Field firstPlayerFiled;
    protected Field secondPlayerPlayerField;

    private static final Scanner scanner = new Scanner(System.in);

    //place ships on the board
    protected Field placeShips() {
        Field field = new Field();
        field.printBoard();
        //go throw ships list and set coordinates
        for (int i = 0; i < field.ships.size(); i++) {
            //check user input
            String[] coordinates = InputHandler.enterShipCoordinates(field, field.ships.get(i));
            field.placeShip(coordinates, field.ships.get(i));
            field.printBoard();
        }
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
        return field;
    }

    //make a shot
    public void makeAShot(Field field) {
        String id = InputHandler.enterShotCoordinate();
        Cell cell = field.getCellById(id);
        //if hit the ship
        if (cell.getMark() == SHIP.mark || cell.getMark() == HIT.mark) {
            cell.setMark(HIT.mark); // set hit mark
            cell.setVisible(true); //make cell be visible
            //if no more ships on the board, print that user has won
            if (field.noMoreShips()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                //if the ship is destroyed, print that in console
            } else if (findShip(cell, field).isSunk()) {
                System.out.println("\nYou sank a ship! Specify a new target:");
            } else {
                System.out.println("\nYou hit a ship!\n");
            }
        } else {
            cell.setMark(MISS.mark);
            cell.setVisible(true);
            System.out.println("\nYou missed!\n");
        }
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }

    //find the ship, that is on this cell
    private Ship findShip(Cell cell, Field field) {
        return field.ships.stream().filter(ship -> ship.coordinates.contains(cell))
                .findFirst().orElse(null);
    }

    public void startGame() {
        System.out.println("\nPlayer 1, place your ships on the game field\n");
        firstPlayerFiled = placeShips();
        System.out.println("\nPlayer 2, place your ships to the game field\n");
        secondPlayerPlayerField = placeShips();

        while (!firstPlayerFiled.noMoreShips()) {
            System.out.println("\nPlayer 1, it's your turn:\n");
            secondPlayerPlayerField.printBoardWithFog();
            System.out.println("---------------------");
            firstPlayerFiled.printBoard();
            makeAShot(secondPlayerPlayerField);

            if (secondPlayerPlayerField.noMoreShips()) {
                break;
            }

            System.out.println("\nPlayer 2, it's your turn:\n");
            firstPlayerFiled.printBoardWithFog();
            System.out.println("---------------------");
            secondPlayerPlayerField.printBoard();
            makeAShot(firstPlayerFiled);
        }
    }
}
