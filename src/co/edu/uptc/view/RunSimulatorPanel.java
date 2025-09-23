package co.edu.uptc.view;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.DFA;
import co.edu.uptc.model.exceptions.NullException;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunSimulatorPanel extends JPanel {

    private ManagerView managerView;
    private JLabel runSimulatorTitle;
    private JTextField runSimulatorInput;
    private JButton runSimulatorButton;
    private JLabel stringValidate;

    public RunSimulatorPanel(ManagerView managerView) {
        this.setLayout(new GridBagLayout());
        this.managerView = managerView;
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        createRunSimulatorTitle();
        createRunSimulatorInput();
        createRunSimulatorButton();
        createStringValidate();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(runSimulatorTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(runSimulatorInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        this.add(runSimulatorButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        this.add(stringValidate, gbc);
    }

    private void createRunSimulatorTitle() {
        runSimulatorTitle = new JLabel("Ejecutar simulador");
        runSimulatorTitle.setHorizontalAlignment(SwingConstants.CENTER);
        runSimulatorTitle.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void createRunSimulatorInput() {
        runSimulatorInput = new JTextField();
        runSimulatorInput.setColumns(20);
    }

    private void createRunSimulatorButton() {
        runSimulatorButton = new JButton("Ejecutar");
        runSimulatorButton.addActionListener(e -> {
            String result = managerView.getPresenter().validate(runSimulatorInput.getText());
            System.out.println(result);
            String htmlText = "<html>" + result.replace("\n", "<br>") + "</html>";
            stringValidate.setText(htmlText);
            revalidate();
        });
    }

    private void createStringValidate(){
        stringValidate = new JLabel("Sin cadena");
        stringValidate.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
