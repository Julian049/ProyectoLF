package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;

public class ChangeInitialStateDialog extends JDialog {

    private ManagerView managerView;
    private JLabel changeInitialStateLabel;
    private JLabel changeStateBodyLabel;
    private JButton cancelChangeInitialStateButton;
    private JButton confirmChangeInitialStateButton;

    public ChangeInitialStateDialog(String currentState, String newState) {
        setSize(300, 200);
        setResizable(false);
        setTitle("Change Initial State");
        setLocationRelativeTo(null);
        setVisible(false);
        setLayout(new GridBagLayout());
        initComponents(currentState, newState);
    }

    private void initComponents(String currentState, String newState) {
        GridBagConstraints gbc = new GridBagConstraints();

        createInitialStateLabel();
        createInitialStateBodyLabel(currentState, newState);
        createCancelChangeInitialStateButton();
        createConfirmChangeInitialStateButton();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(changeInitialStateLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(changeStateBodyLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(cancelChangeInitialStateButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 5, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(confirmChangeInitialStateButton, gbc);
    }

    private void createInitialStateLabel() {
        changeInitialStateLabel = new JLabel("Cambiar estado inicial");
    }

    private void createInitialStateBodyLabel(String currentState, String newState) {
        changeStateBodyLabel = new JLabel("Esta seguro de cambiar el estado inicial " + currentState + " por " + newState + "?");
    }

    private void createCancelChangeInitialStateButton() {
        cancelChangeInitialStateButton = new JButton("Cancel");
    }

    private void createConfirmChangeInitialStateButton() {
        confirmChangeInitialStateButton = new JButton("Confirm");
        confirmChangeInitialStateButton.addActionListener(e -> {
            managerView.changeInitialState();
            this.dispose();
        });

    }

    public void showDialog() {
        setVisible(true);
    }

    public void setManagerView(ManagerView managerView) {
        this.managerView = managerView;
    }
}
