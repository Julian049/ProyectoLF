package co.edu.uptc.view;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class OptionsPanel extends JPanel {

    private ManagerView managerView;
    private JLabel statesTitle;
    private JTextField statesValues;
    private JButton addStatesButton;
    private JLabel symbolsTitle;
    private JTextField symbolsValue;
    private JButton addSymbolsButton;
    private JLabel initialStateTitle;
    private JTextField initialStateValue;
    private JButton initialStateButton;
    private JLabel finalStateTitle;
    private JTextField finalStateValue;
    private JButton finalStateButton;
    private JTable transitionsTable;
    private DefaultTableModel tableModel;
    private ArrayList<String> columnNames = new ArrayList<>();
    private ArrayList<String> rowValues = new ArrayList<>();

    public OptionsPanel(ManagerView managerView) {
        this.setLayout(new GridBagLayout());
        this.managerView = managerView;
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        createStatesTitle();
        createStatesValues();
        createAddStatesButton();
        createSymbolsTitle();
        createSymbolsValue();
        createAddSymbolsButton();
        createInitialStateTitle();
        createInitialStateValue();
        createInitialStateButton();
        createFinalStateTitle();
        createFinalStateValue();
        createFinalStateButton();
        createTransitionsTable();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(statesTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(statesValues, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(addStatesButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(symbolsTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(symbolsValue, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(addSymbolsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(initialStateTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(initialStateValue, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(initialStateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(finalStateTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(finalStateValue, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(finalStateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        JScrollPane scrollPane = new JScrollPane(transitionsTable);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        this.add(scrollPane, gbc);
    }

    private void createStatesTitle() {
        statesTitle = new JLabel("Agregue los estados separados por comas (q0,q1,q2)");
    }

    private void createStatesValues() {
        statesValues = new JTextField();
        statesValues.setEditable(true);
        statesValues.setColumns(10);
    }

    private void createAddStatesButton() {
        addStatesButton = new JButton("Agregar");
        addStatesButton.addActionListener(e -> {
            for (String state : managerView.separateByComma(statesValues.getText())){
                if (!state.isEmpty()) {
                    try {
                        managerView.addState(state);
                        columnNames.add(state);
                        tableModel.addColumn(state);
                        revalidate();
                        statesValues.setText("");
                    } catch (ObjectAlreadyExists ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }

    private void createSymbolsTitle() {
        symbolsTitle = new JLabel("Agregue los simbolos para el automata separados por comas (a,b,c)");
    }

    private void createSymbolsValue() {
        symbolsValue = new JTextField();
        symbolsValue.setEditable(true);
        symbolsValue.setColumns(10);
    }

    private void createAddSymbolsButton() {
        addSymbolsButton = new JButton("Agregar");
        addSymbolsButton.addActionListener(e -> {
            System.out.println(symbolsValue.getText());
            for (String symbol : managerView.separateByComma(symbolsValue.getText())){
                if (!symbol.isEmpty()) {
                    try {
                        managerView.addSymbol(symbol);
                        Object[] newRow = new Object[tableModel.getColumnCount()];
                        newRow[0] = symbol;
                        tableModel.addRow(newRow);
                        symbolsValue.setText("");
                    } catch (ObjectAlreadyExists ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }

    private void createInitialStateTitle() {
        initialStateTitle = new JLabel("Ingrese el estado incial");
    }

    private void createInitialStateValue() {
        initialStateValue = new JTextField();
        initialStateValue.setEditable(true);
        initialStateValue.setColumns(10);
    }

    private void createInitialStateButton() {
        initialStateButton = new JButton("Agregar");
        initialStateButton.addActionListener(e -> {
            System.out.println(initialStateValue.getText());
        });
    }

    private void createFinalStateTitle() {
        finalStateTitle = new JLabel("Seleccione el estado final, en caso de ser varios separados por comas (q0,q1,q2)");

    }

    private void createFinalStateValue() {
        finalStateValue = new JTextField();
        finalStateValue.setEditable(true);
        finalStateValue.setColumns(10);
    }

    private void createFinalStateButton() {
        finalStateButton = new JButton("Agregar");
        finalStateButton.addActionListener(e -> {
            System.out.println(finalStateValue.getText());
        });
    }

    private void createTransitionsTable() {
        columnNames.add("estado");
        rowValues.add(" ");
        tableModel = new DefaultTableModel(columnNames.toArray(), 0);
        transitionsTable = new JTable(tableModel);
    }
}
