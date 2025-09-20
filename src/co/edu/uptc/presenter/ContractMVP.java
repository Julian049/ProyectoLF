package co.edu.uptc.presenter;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;

import java.util.List;

public interface ContractMVP {

    interface Model {
        void setPresenter(Presenter presenter);
        List<State> getStates();
        List<State> getTransitions();
        void addTransition(String to, String from, String value);
        void removeTransition(String transition);
        void addState();
        void removeState(String state);
    }

    interface Presenter {
        void setModel(Model model);
        void setView(View view);
        List<State> getStates();
        List<State> getTransitions();
        void addTransition(String to, String from, String value);
        void removeTransition(String transition);
        void addState();
        void removeState(String state);
        void run();
    }

    interface View {
        void setPresenter(Presenter presenter);
        void initUI();
    }
}
