package co.edu.uptc.view;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;
import co.edu.uptc.presenter.ContractMVP;

import javax.swing.*;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;

public class ManagerView extends JFrame implements ContractMVP.View {

    private ContractMVP.Presenter presenter;
    private OptionsPanel optionsPanel;

    public ManagerView() {
        createFrame();
        optionsPanel = new OptionsPanel(this);
    }

    @Override
    public void setPresenter(ContractMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initUI() {
        this.add(optionsPanel);
        setVisible(true);
    }

    private void createFrame() {
        setTitle("Simulador de automatas deterministas");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    public List<String> separateByComma(String text){
        List<String> list = new ArrayList<String>();
        String[] array = text.split(",");

        for(String element : array) {
            list.add(element.trim());
        }

        return list;
    }

    public List<Transition> getTransitions(){
        return presenter.getTransitions();
    }

    public List<State> getStates(){
        return presenter.getStates();
    }

    public void addSymbol(String symbol) throws ObjectAlreadyExists {
        presenter.addSymbol(symbol);
    }

    public void addState(String nameState) throws ObjectAlreadyExists {
        presenter.addState(nameState);
    }
}
