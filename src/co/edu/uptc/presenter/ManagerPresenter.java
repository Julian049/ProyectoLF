package co.edu.uptc.presenter;

import co.edu.uptc.model.ManagerModel;
import co.edu.uptc.model.State;
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
    public List<State> getTransitions() {
        return model.getTransitions();
    }

    @Override
    public void addTransition(String to, String from, String value) {
        model.addTransition(to, from, value);
    }

    @Override
    public void removeTransition(String transition) {
        model.removeTransition(transition);
    }

    @Override
    public void addState() {
        model.addState();
    }

    @Override
    public void removeState(String state) {
        model.removeState(state);
    }

    @Override
    public void setModel(ContractMVP.Model model) {
        this.model = model;
    }

    @Override
    public void run() {
        makeMVP();
        //view.initUI();
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
