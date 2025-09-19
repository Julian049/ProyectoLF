package co.edu.uptc.model;

public class State {
    private String nombre;
    private boolean initialState;
    private boolean finalState;

    public State(String nombre) {
        this.nombre = nombre;
        this.initialState = false;
        this.finalState = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isInitialState() {
        return initialState;
    }

    public void setInitialState(boolean initialState) {
        this.initialState = initialState;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public void setFinalState(boolean finalState) {
        this.finalState = finalState;
    }
}
