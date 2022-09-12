package src;

public enum Properties {
    FOG('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');


    final char mark;

    Properties(char mark) {
        this.mark = mark;
    }
}
