package helloandroid.m2dl.coronattack.game_over;

public class Score {

    private String name;

    private int value;

    public Score() {

    }

    public Score(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
