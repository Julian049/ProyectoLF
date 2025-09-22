package co.edu.uptc.presenter;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;

import java.util.List;

public interface ContractMVP {

    interface Model {
        void setPresenter(Presenter presenter);
        List<State> getStates();
        List<Transition> getTransitions();
        void addTransition(String to, String from, String value);
        void removeTransition(String transition);
        void addState();
        void removeState(String state);
        String validate(String value);
        void addSymbol(String symbol) throws ObjectAlreadyExists;
    }

    interface Presenter {
        void setModel(Model model);
        void setView(View view);
        List<State> getStates();
        List<Transition> getTransitions();
        void addTransition(String to, String from, String value);
        void removeTransition(String transition);
        void addState();
        void removeState(String state);
        String validate(String value);
        void addSymbol(String symbol) throws ObjectAlreadyExists;
        void run();
    }

    interface View {
        void setPresenter(Presenter presenter);
        void initUI();

    }
}
