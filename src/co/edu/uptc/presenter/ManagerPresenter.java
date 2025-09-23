package co.edu.uptc.presenter;

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
            model.addState("A");
            model.addState("B");
            model.addSymbol("1");
            model.addSymbol("0");
        } catch (ObjectAlreadyExists e) {
            throw new RuntimeException(e);
        }

        view.addInfo();
        thread.start();
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
