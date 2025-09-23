package co.edu.uptc.presenter;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.exceptions.NullException;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;

import java.util.List;

public interface ContractMVP {

    interface Model {
        void setPresenter(Presenter presenter);
        List<State> getStates();
        List<Transition> getTransitions();
        void addTransition(String to, String from, char value);
        void removeTransition(char transition);
        void addState(String name) throws ObjectAlreadyExists;
        void removeState(String state);
        String validate(String value);
        void addSymbol(String symbol) throws ObjectAlreadyExists;
        State searchState(String name);
        State getInitialState();
        List<Character> getSymbols();
    }

    interface Presenter {
        void setModel(Model model);
        void setView(View view);
        List<State> getStates();
        List<Transition> getTransitions();
        void addTransition(String to, String from, char value);
        void removeTransition(char transition);
        void addState(String name) throws ObjectAlreadyExists;
        void removeState(String state);
        String validate(String value);
        void addSymbol(String symbol) throws ObjectAlreadyExists;
        void run();
        State searchState(String name) throws NullException;
        State getInitialState() throws NullException;
        List<Character> getSymbols();
    }

    interface View {
        void setPresenter(Presenter presenter);
        void initUI();
        void addInfo();
    }
}
