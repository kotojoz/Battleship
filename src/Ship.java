package src;

import static src.Properties.SHIP;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private final String name;

    private final int size;

    protected List<Cell> coordinates;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
        this.coordinates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    protected boolean isSunk() {
        return coordinates.stream().noneMatch(v -> v.getMark() == SHIP.mark);
    }
}
