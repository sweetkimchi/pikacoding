package ooga.model.player;

/**
 * 
 */
public abstract class Player implements Element {

    private int programCounter;
    /**
     * Default constructor
     */
    public Player() {
        programCounter = 1;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public int getProgramCounter(){
        return programCounter;
    }
}