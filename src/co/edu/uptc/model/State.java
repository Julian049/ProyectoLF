package co.edu.uptc.model;

import co.edu.uptc.utils.TypeState;

public class State {

    private String name;
    private TypeState type;

    public State(String name) {
        this.name = name;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String name) {
        this.name = name;
    }

    public TypeState getType() {
        return type;
    }

    public void setType(TypeState type) {
        this.type = type;
    }

}
