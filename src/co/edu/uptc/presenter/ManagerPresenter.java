package co.edu.uptc.presenter;

import co.edu.uptc.model.DFA;
import co.edu.uptc.model.ManagerModel;
import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.exceptions.NullException;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;
import co.edu.uptc.view.ManagerView;

import java.util.List;

public class ManagerPresenter implements ContractMVP.Presenter {

    private ContractMVP.View view;
    private ContractMVP.Model model;

    @Override
    public void setView(ContractMVP.View view) {
        this.view = view;
    }

    @Override
    public List<State> getStates() {
        return model.getStates();
    }

    @Override
    public List<Transition> getTransitions() {
        return model.getTransitions();
    }

    @Override
    public void addTransition(String to, String from, char value) {
        model.addTransition(to, from, value);
    }

    @Override
    public void removeTransition(char transition) {
        model.removeTransition(transition);
    }

    @Override
    public void addState(String name) throws ObjectAlreadyExists {
        model.addState(name);
    }

    @Override
    public void addSymbol(String symbol) throws ObjectAlreadyExists {
        model.addSymbol(symbol);
    }

    @Override
    public State searchState(String name) throws NullException {
        if (model.searchState(name) == null) {
            NullException ex = new NullException("El estado no existe");
            throw ex;
        }
        return model.searchState(name);
    }

    @Override
    public State getInitialState() throws NullException {
        if (model.getInitialState() != null) {
            NullException ex = new NullException("Ya hay un estado inicial");
            throw ex;
        }
        return model.getInitialState();
    }

    @Override
    public List<Character> getSymbols() {
        return model.getSymbols();
    }

    @Override
    public void removeState(String state) {
        model.removeState(state);
    }

    @Override
    public String validate(String value) {
        return model.validate(value);
    }

    @Override
    public void setModel(ContractMVP.Model model) {
        this.model = model;
    }

    @Override
    public void run() {
        makeMVP();
        view.initUI();

        Thread thread = new Thread(() -> {
            while (true) {
                for (Transition transition : model.getTransitions()) {
                    System.out.println(transition);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //DATOS DE PRUEBA


        try {
// Agregar estados
            model.addState("A");
            model.searchState("A").setInitial(true);
            model.addState("B");
            model.addState("C");
            model.addState("D");
            model.addState("E");
            model.addState("F");
            model.addState("G");
            model.addState("H");
            model.searchState("H").setFinal(true);

// Agregar símbolos
            model.addSymbol("0");
            model.addSymbol("1");
            model.addSymbol("2");

// Agregar transiciones
            model.addTransition("A", "B", Character.valueOf('0'));
            model.addTransition("A", "C", Character.valueOf('1'));
            model.addTransition("A", "D", Character.valueOf('2'));

            model.addTransition("B", "E", Character.valueOf('0'));
            model.addTransition("B", "F", Character.valueOf('1'));
            model.addTransition("B", "A", Character.valueOf('2'));

            model.addTransition("C", "G", Character.valueOf('0'));
            model.addTransition("C", "H", Character.valueOf('1'));
            model.addTransition("C", "B", Character.valueOf('2'));

            model.addTransition("D", "A", Character.valueOf('0'));
            model.addTransition("D", "C", Character.valueOf('1'));
            model.addTransition("D", "H", Character.valueOf('2'));

            model.addTransition("E", "E", Character.valueOf('0'));
            model.addTransition("E", "F", Character.valueOf('1'));
            model.addTransition("E", "G", Character.valueOf('2'));

            model.addTransition("F", "H", Character.valueOf('0'));
            model.addTransition("F", "A", Character.valueOf('1'));
            model.addTransition("F", "B", Character.valueOf('2'));

            model.addTransition("G", "C", Character.valueOf('0'));
            model.addTransition("G", "D", Character.valueOf('1'));
            model.addTransition("G", "H", Character.valueOf('2'));

            model.addTransition("H", "H", Character.valueOf('0'));
            model.addTransition("H", "H", Character.valueOf('1'));
            model.addTransition("H", "H", Character.valueOf('2'));


        } catch (ObjectAlreadyExists e) {
            throw new RuntimeException(e);
        }

        view.addInfo();
        //thread.start();
    }

    @Override
    public DFA getDFA() {
        return model.getDFA();
    }


    private void makeMVP() {
        ManagerView managerView = new ManagerView();
        managerView.setPresenter(this);

        ManagerModel managerModel = new ManagerModel();
        managerModel.setPresenter(this);

        setView(managerView);
        setModel(managerModel);

        System.out.println("Patron MVP listo");
    }
}
