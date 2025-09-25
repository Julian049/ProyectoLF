package co.edu.uptc.model;

import co.edu.uptc.model.exceptions.NullException;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DFA {

    private List<State> states;
    private List<Transition> transitions;
    private List<Character> symbols;
    private ArrayList<String> acceptedStrings;
    private String outStrings = "";

    public DFA() {
        this.states = new ArrayList<State>();
        this.transitions = new ArrayList<Transition>();
        this.symbols = new ArrayList<>();
        acceptedStrings = new ArrayList<>();
    }

    //Metodo para generar las primeras 10 cadenas del automata
    //Genera un numero random entre 0 y la cantidad de simbolos del automata
    //con el cual va ingresa a la posicion en el automata, retorna el simbolo
    //y lo guarda en un cadena de texto que despues se valida con el metodo validate
    //y si esta valida se agrega a la lista de cadenas aceptadas
    public void generateStrings() {
        outStrings = "";

        Random random = new Random();
        int index = 0;
        int numberStrings = 0;
        while (acceptedStrings.size() < 10) {
            String stringToEvaluate = "";
            for (int i = -1; i < numberStrings; i++) {
                index = random.nextInt(symbols.size());
                stringToEvaluate += symbols.get(index);
            }

            try {
                if (validate(stringToEvaluate).contains("ACEPTADA")) {
                    acceptedStrings.add(stringToEvaluate);
                }
            } catch (NullException e) {
                throw new RuntimeException(e);
            }

            numberStrings++;
        }

        outStrings += "Cadenas aceptadas \n";
        for (String string : acceptedStrings) {
            outStrings += string + "\n";
        }
    }

    //Metodo para agregar un nuevo estado
    //Recibe como parametro el nombre que se le desea asignar al automata
    //Valida si el nombre del estado ya existe y si es asi lo rechaza
    //De caso contrario agrega el nuevo estado
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

    //Agregar transicion
    //Lo primero que hace este metodo es valida si el valor retornado por la interfaz era en un principio nulo
    //Si no es asi, busca la transicion que corresponde con el valor retornado por la interfaz
    //y la actualiza con el nuevo estado
    public void addTransition(String currentValue, String to, String from, char symbol) {
        if (currentValue.isEmpty()) {
            State stateFrom = searchState(from);
            State stateTo = searchState(to);
            if (symbols.contains(symbol)) {
                Transition transition = new Transition(stateFrom, stateTo, symbol);
                this.transitions.add(transition);
            }
        } else {
            updateTransition(searchTransition(symbol, from, currentValue), to);
        }
    }

    //Buscar un estado por su nombre
    //Este metodo permite retorna un estado con solo su nombre
    public State searchState(String name) {
        for (State state : states) {
            if (state.getName().equalsIgnoreCase(name)) {
                return state;
            }
        }
        return null;
    }

    //Buscar un transicion
    //Las transiciones esta definididas por su estado inicial, estado final y simbolo
    //Por lo cual en este metodo solicitaremos los 3 para buscar una transicion
    public Transition searchTransition(char symbol, String from, String to) {
        for (Transition transition : transitions) {
            boolean sameSymbol = transition.getSymbol() == symbol;
            boolean sameOriginState = transition.getOriginState().getName().equalsIgnoreCase(from);
            boolean sameDestinationState = transition.getDestinationState().getName().equalsIgnoreCase(to);
            if (sameSymbol && sameOriginState && sameDestinationState) {
                return transition;
            }
        }
        return null;
    }

    //Metodo para actualizar una transicion
    //Este metodo llama al metodo de busca de transiciones, para actualizar el estado final
    //de una transicion
    public void updateTransition(Transition transition, String newTo) {
        transition.setDestinationState(searchState(newTo));
    }


    //Metodo para buscar esl estado inicial del automata
    //Valida en la lista de estados, cual de estos tiene como atributo
    //isInitial en true, pare despues retornarlo y utilizarlo en el
    //metodo de validacion
    public State getInitialState() {
        for (State state : this.states) {
            if (state.isInitial()) {
                return state;
            }
        }
        return null;
    }

    //Metodo para pasar de un estado al siguiente dependiento su simbolo en la transicion
    //Lo que busca este metodo es poder desplazarnos dentro del automata atraves de sus
    //transiciones, sera utilizado en el metodo de validacion
    public State getNextState(State current, char symbol) {
        for (Transition transition : this.transitions) {
            boolean sameOriginState = transition.getOriginState().getName().equalsIgnoreCase(current.getName());
            boolean sameSymbol = symbol == transition.getSymbol();
            if (sameSymbol && sameOriginState) {
                return transition.getDestinationState();
            }
        }
        return null;
    }

    //Metodo de validacion de estado final
    //Valida si el atributo isFinal de un estado es final para ser utilizado en la validacion
    //de una automata
    public boolean isFinalState(State state) {
        return state.isFinal();
    }

    //Metodo de validacion de automata
    //Este metodo recibe una cadena de caracteres que seran evaluados
    //1) Se validara que si la cadena es vacia el estado inicial tambien debe ser estado final
    //   si no es asi retornara un mensaje de fallo
    //2) Si la cadena no es vacia, comienza a validar caracter por caracter desplazandose con el metodo
    //   getNextState y si despues de pasar por la cadena de caracteres si el ultimo estado en el que
    //   se encuetra su atributo isFinal tiene como valor true, validara que el la cadena pertenece al
    //   lenguaje del automata propuesto
    public String validate(String input) throws NullException {

        StringBuilder output = new StringBuilder("Evaluando cadena " + input + " \n");
        boolean initialState = false;
        State currentState = getInitialState();

        if (currentState == null) {
            throw new NullException("No se ha definido un estado inicial");
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
                throw new NullException("El simbolo: " + symbol + " no pertenece al alfabeto del automata ");
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

    public String getOutStrings() {
        return outStrings;
    }

}
