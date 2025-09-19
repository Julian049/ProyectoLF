package co.edu.uptc.model;

public class Transition {
    private State originState;
    private State destinationState;
    private int symbol;

    public Transition(State originState, State destinationState, int symbol) {
        this.originState = originState;
        this.destinationState = destinationState;
        this.symbol = symbol;
    }

    public State getOriginState() {
        return originState;
    }

    public void setOriginState(State originState) {
        this.originState = originState;
    }

    public State getDestinationState() {
        return destinationState;
    }

    public void setDestinationState(State destinationState) {
        this.destinationState = destinationState;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
}
