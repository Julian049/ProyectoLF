package co.edu.uptc.presenter;

import co.edu.uptc.model.DFA;
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
        void addTransition(String currentValue,String to, String from, char value);
        void addState(String name) throws ObjectAlreadyExists;
        String validate(String value) throws NullException;
        void addSymbol(String symbol) throws ObjectAlreadyExists;
        State searchState(String name);
        List<Character> getSymbols();
        boolean canExport();
        boolean exportDFA(String filePath);
        DFA importDFA(String filePath) throws Exception;
        void replaceDFA(DFA dfa);
        String generateStrings();
    }

    interface Presenter {
        void setModel(Model model);
        void setView(View view);
        List<State> getStates();
        List<Transition> getTransitions();
        void addTransition(String currentValue,String to, String from, char value);
        void addState(String name) throws ObjectAlreadyExists;
        String validate(String value) throws NullException ;
        void addSymbol(String symbol) throws ObjectAlreadyExists;
        void run();
        State searchState(String name) throws NullException;
        List<Character> getSymbols();
        void exportDFA();
        void importDFA();
        String generateStrings();
    }

    interface View {
        void setPresenter(Presenter presenter);
        void initUI();
        void addInfo();
        String showSaveFileDialog(String defaultFileName);
        void showMessage(String message, String title, boolean isSuccess);
        String showOpenFileDialog();
        boolean confirmReplaceData();
    }
}
