package co.edu.uptc.model;

import co.edu.uptc.utils.TypeState;

import java.util.ArrayList;
import java.util.List;

public class DFA {

    private List<State> states;
    private List<Transition> transitions;

    public DFA() {
        this.states = new ArrayList<State>();
        this.transitions = new ArrayList<Transition>();
    }

    public void addState() {
        State state = new State("q" + states.size());
        state.setType(TypeState.INTERMEDIUM);
        this.states.add(state);
    }

    public void addTransition(String from, String to, String symbol) {
        State stateFrom = searchState(from);
        State stateTo = searchState(to);
        Transition transition  = new Transition(stateFrom, stateTo, symbol);
        this.transitions.add(transition);
    }

    public State searchState(String name) {
        for (State state : states) {
            if (state.getNombre().equals(name)) {
                return state;
            }
        }
        return null;
    }

    public Transition searchTransition(String name) {
        for (Transition transition : transitions) {
            if (transition.getSymbol().equals(name)) {
                return transition;
            }
        }
        return null;
    }

    public void deleteState(String state) {
        State stateToDelete = searchState(state);
        this.states.remove(stateToDelete);
        this.transitions.removeIf(transition -> transition.getOriginState().equals(stateToDelete) || transition.getDestinationState().equals(stateToDelete));
    }

    public void deleteTransition(String transition) {
        Transition transitionToDelete = searchTransition(transition);
        this.transitions.remove(transitionToDelete);
    }

    public void updateState(State state) {
        List<State> temp = this.states;
        this.states.remove(state);
        this.states.add(state);
    }

    public void updateTransition(Transition transition) {
        this.transitions.remove(transition);
        this.transitions.add(transition);
    }

    public State getInitialState() {
        for (State state : this.states) {
            if (state.getType().equals(TypeState.INITIAL)) {
                return state;
            }
        }
        return null;
    }

    public State getNextState(State current, String symbol) {
        for (Transition transition : this.transitions) {
            if (transition.getOriginState().equals(current) &&
                    transition.getSymbol().equals(symbol)) {
                return transition.getDestinationState();
            }
        }
        return null;
    }

    public boolean isFinalState(State state) {
        return state.getType().equals(TypeState.FINAL);
    }

    public String validate(String input) {
        State currentState = getInitialState();

        if (currentState == null) {
            return "No se ha definido un estado inicial";
        }

        String[] values = input.split("");
        for (String symbol : values) {
            State nextState = getNextState(currentState, symbol);
            if (nextState == null) {
                return "El simbolo: " + symbol + " no pertenece al alfabeto del automata";
            }
            currentState = nextState;
        }

        if (isFinalState(currentState)) {
            return "Cadena aceptada";
        } else {
            return "Cadena rechazada";
        }
    }


    public List<State> getStates() {
        return states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

}
