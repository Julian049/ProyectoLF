package co.edu.uptc.model;

public class Transition {
    private State originState;
    private State destinationState;
    private char symbol;

    public Transition(State originState, State destinationState, char symbol) {
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

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Transition " +
                "originState=" + originState +
                ", destinationState=" + destinationState +
                ", symbol='" + symbol + '\'';
    }
}
