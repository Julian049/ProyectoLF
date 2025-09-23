package co.edu.uptc.model;

import co.edu.uptc.model.exceptions.ObjectAlreadyExists;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.List;

public class DFA {

    private List<State> states;
    private List<Transition> transitions;
    private List<Character> symbols;

    public DFA() {
        this.states = new ArrayList<State>();
        this.transitions = new ArrayList<Transition>();
        this.symbols = new ArrayList<>();
    }

    public void addState(String name) throws ObjectAlreadyExists {
        State newState = new State(name);
        if (states.isEmpty()) {
            this.states.add(newState);
        } else {
            for (State state : states) {
                if (state.getName().equalsIgnoreCase(name)) {
                    ObjectAlreadyExists ex = new ObjectAlreadyExists("El estado ya existe");
                    throw ex;
                } else {
                    this.states.add(newState);
                    break;
                }
            }
        }
    }

    public void addTransition(String from, String to, char symbol) {
        State stateFrom = searchState(from);
        State stateTo = searchState(to);
        System.out.println(symbols.toString());
        if (symbols.contains(symbol)) {
            Transition transition = new Transition(stateFrom, stateTo, symbol);
            this.transitions.add(transition);
            System.out.println("Transicion añadida correctamente");
        } else {
            System.out.println("El simbolo no existe");
        }
    }

    private boolean searchSymbol(char Symbol) {
        boolean result = false;
        for (char symbol : symbols) {

        }
        return result;
    }

    public State searchState(String name) {
        for (State state : states) {
            if (state.getName().equalsIgnoreCase(name)) {
                return state;
            }
        }
        return null;
    }

    public Transition searchTransition(char symbol) {
        for (Transition transition : transitions) {
            if (transition.getSymbol() == symbol) {
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

    public void deleteTransition(char transition) {
        Transition transitionToDelete = searchTransition(transition);
        this.transitions.remove(transitionToDelete);
    }

    public void updateState(State state) {
        this.states.remove(state);
        this.states.add(state);
    }

    public void updateTransition(Transition transition) {
        this.transitions.remove(transition);
        this.transitions.add(transition);
    }

    public State getInitialState() {
        for (State state : this.states) {
            if (state.isInitial()) {
                return state;
            }
        }
        return null;
    }

    public State getNextState(State current, char symbol) {
        for (Transition transition : this.transitions) {
            if (transition.getOriginState().equals(current) &&
                    transition.getSymbol() == symbol) {
                return transition.getDestinationState();
            }
        }
        return null;
    }

    public boolean isFinalState(State state) {
        return state.isFinal();
    }

    public String validate(String input) {

        StringBuilder output = new StringBuilder("Evaluando cadena " + input + " \n");
        boolean initialState = false;
        State currentState = getInitialState();

        if (currentState == null) {
            output.append("No se ha definido un estado inicial");
        } else {
            initialState = true;
        }

        if (initialState && input.isEmpty()) {
            if (currentState.isFinal()) {
                output.append("Proceso finalizado. El estado final es " + currentState.getName() + "\n");
                output.append("Resultado: La cadena \"" + input + "\" es ACEPTADA");
            } else {
                output.append("Proceso finalizado. El estado final es " + currentState.getName() + "\n");
                output.append("Resultado: La cadena \"" + input + "\" es RECHAZADA");
            }
        }


        char[] values = input.toCharArray();
        for (char symbol : values) {
            State nextState = getNextState(currentState, symbol);
            if (nextState == null) {
                output.append("El simbolo: ").append(symbol).append(" no pertenece al alfabeto del automata \n");
                break;
            }
            output.append("Desde el estado " + currentState.getName() + " con el símbolo '" + symbol + "' se transita al estado " + nextState.getName() + "\n");
            currentState = nextState;
        }

        if (isFinalState(currentState)) {
            output.append("Proceso finalizado. El estado final es " + currentState.getName() + "\n");
            output.append("Resultado: La cadena \"" + input + "\" es ACEPTADA");
        } else {
            output.append("Proceso finalizado. El estado final es " + currentState.getName() + "\n");
            output.append("Resultado: La cadena \"" + input + "\" es RECHAZADA");
        }

        return output.toString();
    }

    public void addSymbol(String newSymbol) throws ObjectAlreadyExists {
        char symbolChar = newSymbol.toLowerCase().charAt(0);
        for (Character c : symbols) {
            if (Character.toLowerCase(c) == symbolChar) {
                throw new ObjectAlreadyExists("El símbolo ya existe");
            }
        }
        symbols.add(symbolChar);
    }

    public List<State> getStates() {
        return states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Character> getSymbols() {
        return symbols;
    }

}
