package co.edu.uptc.model;

public class State {

    private String name;
    private boolean isInitial;
    private boolean isFinal;

    public State(String name) {
        this.name = name;
        this.isInitial = false;
        this.isFinal = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean finalState) {
        isFinal = finalState;
    }

    public String getTypeDescription() {
        if (isInitial && isFinal) {
            return "Initial-Final";
        } else if (isInitial) {
            return "Initial";
        } else if (isFinal) {
            return "Final";
        } else {
            return "Intermediate";
        }
    }

    @Override
    public String toString() {
        return "State " + name + '\'';
    }
}
