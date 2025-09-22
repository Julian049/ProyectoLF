package co.edu.uptc.model;

public class Transition {
    private State originState;
    private State destinationState;
    private String symbol;

    public Transition(State originState, State destinationState, String symbol) {
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
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
