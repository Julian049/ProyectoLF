package co.edu.uptc.view;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;
import co.edu.uptc.presenter.ContractMVP;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerView extends JFrame implements ContractMVP.View {

    private ContractMVP.Presenter presenter;
    private OptionsPanel optionsPanel;
    private RunSimulatorPanel runSimulatorPanel;
    private ChangeInitialStateDialog changeInitialStateDialog;
    private State newInitialState = null;

    public ManagerView() {
        createFrame();
        optionsPanel = new OptionsPanel(this);
        runSimulatorPanel = new RunSimulatorPanel(this);
    }

    @Override
    public void setPresenter(ContractMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initUI() {
//        GridBagConstraints gbc = new GridBagConstraints();
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        this.add(optionsPanel);
//
//        gbc.gridx = 1;
//        this.add(runSimulatorPanel);

        this.add(runSimulatorPanel);
        setVisible(true);
    }

    @Override
    public void addInfo(){
        optionsPanel.updateInterface();
    }

    private void createFrame() {
        setTitle("Simulador de automatas deterministas");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setLayout(new GridBagLayout());
    }

    public List<String> separateByComma(String text) {
        List<String> list = new ArrayList<String>();
        String[] array = text.split(",");

        for (String element : array) {
            list.add(element.trim());
        }

        return list;
    }

    public void verifyChangeState(String currentState, State newState) {
        newInitialState = newState;
        changeInitialStateDialog = new ChangeInitialStateDialog(currentState, newState.getName());
        changeInitialStateDialog.setManagerView(this);
        changeInitialStateDialog.showDialog();
    }

    public void changeInitialState() {
        optionsPanel.changeInitialState(newInitialState);
    }

    public List<Transition> getTransitions() {
        return presenter.getTransitions();
    }

    public List<State> getStates() {
        return presenter.getStates();
    }

    public void addSymbol(String symbol) throws ObjectAlreadyExists {
        presenter.addSymbol(symbol);
    }

    public void addState(String nameState) throws ObjectAlreadyExists {
        presenter.addState(nameState);
    }

    public ContractMVP.Presenter getPresenter() {
        return presenter;
    }
}
