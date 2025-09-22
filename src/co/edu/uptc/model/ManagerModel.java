package co.edu.uptc.model;

import co.edu.uptc.model.exceptions.ObjectAlreadyExists;
import co.edu.uptc.presenter.ContractMVP;

import java.util.List;

public class ManagerModel implements ContractMVP.Model {

    public ContractMVP.Presenter presenter;
    private DFA dfa;

    public ManagerModel() {
        this.dfa = new DFA();
    }

    @Override
    public void setPresenter(ContractMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public List<State> getStates() {
        return dfa.getStates();
    }

    @Override
    public List<Transition> getTransitions() {
        return dfa.getTransitions();
    }

    public List<Transition> getTransitionsList() {
        return dfa.getTransitions();
    }

    @Override
    public void addTransition(String to, String from, String value) {
        dfa.addTransition(to, from, value);
    }

    @Override
    public void removeTransition(String transition) {
        dfa.deleteTransition(transition);
    }

    @Override
    public void addState() {
        dfa.addState();
    }

    @Override
    public void addSymbol(String symbol) throws ObjectAlreadyExists {
        dfa.addSymbol(symbol);
    }

    @Override
    public void removeState(String state) {
        dfa.deleteState(state);
    }

    @Override
    public String validate(String value) {
        return dfa.validate(value);
    }

}
