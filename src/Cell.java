package src;

public class Cell {
    private final String id;
    private char mark;

    private boolean isVisible;

    public Cell(String id, char mark) {
        this.id = id;
        this.mark = mark;
        this.isVisible = false;
    }

    public String getId() {
        return id;
    }

    public char getMark() {
        return mark;
    }

    public void setMark(char mark) {
        this.mark = mark;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
