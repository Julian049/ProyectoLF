package co.edu.uptc.view;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;
import co.edu.uptc.presenter.ContractMVP;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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
        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        containerPanel.add(optionsPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        containerPanel.add(runSimulatorPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void addInfo() {
        optionsPanel.clearInterface();
        optionsPanel.updateInterface();

        revalidate();
        repaint();
    }

    private void createFrame() {
        setTitle("Simulador de automatas deterministas");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setLayout(new BorderLayout());
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

    public String showSaveFileDialog(String defaultFileName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar AFD");
        fileChooser.setSelectedFile(new File(defaultFileName));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filepath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filepath.toLowerCase().endsWith(".json")) {
                filepath += ".json";
            }
            return filepath;
        }
        return null;
    }

    @Override
    public void showMessage(String message, String title, boolean isSuccess) {
        int messageType = isSuccess ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    @Override
    public String showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Importar AFD");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos JSON", "json"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    @Override
    public boolean confirmReplaceData() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea importar un nuevo AFD?\nEsto reemplazará todos los datos actuales.",
                "Confirmar Importación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
}
