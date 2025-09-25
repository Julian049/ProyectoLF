package co.edu.uptc.model;

import co.edu.uptc.model.exceptions.NullException;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;
import co.edu.uptc.presenter.ContractMVP;

import java.util.List;

public class ManagerModel implements ContractMVP.Model {

    public ContractMVP.Presenter presenter;
    private DFA dfa;
    private DFAExportManager exportManager;
    private DFAImportManager importManager;

    public ManagerModel() {
        this.dfa = new DFA();

        this.exportManager = new DFAExportManager();
        this.importManager = new DFAImportManager();
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

    @Override
    public void addTransition(String currentValue, String to, String from, char value) {
        dfa.addTransition(currentValue, to, from, value);
    }

    @Override
    public void addState(String name) throws ObjectAlreadyExists {
        dfa.addState(name);
    }

    @Override
    public List<Character> getSymbols() {
        return dfa.getSymbols();
    }

    @Override
    public void addSymbol(String symbol) throws ObjectAlreadyExists {
        dfa.addSymbol(symbol);
    }

    @Override
    public String validate(String value) throws NullException {
        return dfa.validate(value);
    }

    @Override
    public String generateStrings(){
        dfa.generateStrings();
        return dfa.getOutStrings();
    }
    
    @Override
    public State searchState(String name) {
        return dfa.searchState(name);
    }

    @Override
    public boolean canExport() {
        return !dfa.getStates().isEmpty();
    }

    @Override
    public boolean exportDFA(String filePath) {
        try {
            exportManager.exportToFile(dfa, filePath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public DFA importDFA(String filePath) throws Exception {
        return importManager.importFromFile(filePath);
    }

    @Override
    public void replaceDFA(DFA newdfa) {
        this.dfa = newdfa;
    }

}
