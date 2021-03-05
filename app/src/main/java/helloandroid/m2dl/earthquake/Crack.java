package helloandroid.m2dl.earthquake;

enum StateCrack {
    APPARITION, DANGER, ENDING
}
public class Crack extends Point {
    StateCrack state ;
    int roundLife;
    int img;

    public Crack (){
        state = StateCrack.APPARITION;
        roundLife = 1;
    }

    public int getImg() {
        return img;
    }

    public void addRoundLife(){
        roundLife += 1;
        if(roundLife < 4){
            state = StateCrack.APPARITION;
            img = R.drawable.crack;
        }else if(roundLife < 9){
            state = StateCrack.DANGER;
            img = R.drawable.crack_danger;
        }else if(roundLife >= 9){
            state = StateCrack.ENDING;
            img = R.drawable.crack;
        }
    }

    boolean isEnding(){
        return state == StateCrack.ENDING;
    }
}
