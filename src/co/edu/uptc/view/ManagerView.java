package co.edu.uptc.view;

import co.edu.uptc.presenter.ContractMVP;

import javax.swing.*;
import javax.swing.text.html.Option;

public class ManagerView extends JFrame implements ContractMVP.View {

    private ContractMVP.Presenter presenter;
    private OptionsPanel optionsPanel;

    public ManagerView() {
        createFrame();
        optionsPanel = new OptionsPanel();
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
}
