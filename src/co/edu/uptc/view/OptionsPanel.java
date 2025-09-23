package co.edu.uptc.view;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.exceptions.NullException;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

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
    private boolean userChangeTable = true;
    private State initialState = null;

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
            userChangeTable = false;
            for (String state : managerView.separateByComma(statesValues.getText())) {
                if (!state.isEmpty()) {
                    try {
                        managerView.addState(state);
                        tableModel.addColumn(state);
                        revalidate();
                        statesValues.setText("");
                    } catch (ObjectAlreadyExists ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            userChangeTable = true;
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
            userChangeTable = false;
            for (String symbol : managerView.separateByComma(symbolsValue.getText())) {
                if (!symbol.isEmpty()) {
                    try {
                        managerView.addSymbol(symbol);
                        Object[] newRow = new Object[tableModel.getColumnCount()];
                        newRow[0] = symbol;
                        tableModel.addRow(newRow);
                        symbolsValue.setText("");
                    } catch (ObjectAlreadyExists ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            userChangeTable = true;
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
            userChangeTable = false;
            String inputStateName = initialStateValue.getText().trim();

            try {
                State newState = managerView.getPresenter().searchState(inputStateName);

                if (initialState == null) {
                    setAsInitialState(newState);
                } else {
                    managerView.verifyChangeState(initialState.getName(), newState);
                }

                initialStateValue.setText("");

            } catch (NullException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            userChangeTable = true;
        });
    }

    private void setAsInitialState(State state) {
        state.setInitial(true);
        initialState = state;
        updateColumnName(state.getName(), "→ " + state.getName());
    }

    public void changeInitialState(State newState) {
        userChangeTable = false;
        try {
            managerView.getPresenter().searchState(initialState.getName()).setInitial(false);
            managerView.getPresenter().searchState(newState.getName()).setInitial(true);
        } catch (NullException e) {
            throw new RuntimeException(e);
        }
        updateColumnName("→ " + initialState.getName(), initialState.getName());

        updateColumnName(newState.getName(), "→ " + newState.getName());

        initialState = newState;
    }

    private void updateColumnName(String columnName, String newName) {
        TableColumnModel columnModel = transitionsTable.getTableHeader().getColumnModel();
        int columnCount = columnModel.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
            if (columnModel.getColumn(i).getHeaderValue().equals(columnName)) {
                columnModel.getColumn(i).setHeaderValue(newName);
            }
        }
        transitionsTable.getTableHeader().repaint();
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
            userChangeTable = false;
            List<String> finalStates = managerView.separateByComma(finalStateValue.getText());
            if (!finalStates.isEmpty()) {
                for (String state : finalStates) {
                    try {
                        managerView.getPresenter().searchState(state).setFinal(true);
                        String currentName = getCurrentColumnName(state);
                        updateColumnName(currentName, currentName + " ⭕");
                    } catch (NullException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            finalStateValue.setText("");
            userChangeTable = true;
        });
    }

    private String getCurrentColumnName(String stateName) {
        if (initialState != null && initialState.getName().equals(stateName)) {
            return "→ " + stateName;
        }
        return stateName;
    }

    private void createTransitionsTable() {
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("Estado");
        tableModel = new DefaultTableModel(columnNames.toArray(), 0);
        transitionsTable = new JTable(tableModel);


        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (userChangeTable) {
                    TableCellListener tcl = (TableCellListener) e.getSource();

                    int row = tcl.getRow();
                    int column = tcl.getColumn();
                    String oldValue = tcl.getOldValue() != null ? tcl.getOldValue().toString() : "";
                    String newValue = tcl.getNewValue() != null ? tcl.getNewValue().toString() : "";

                    char firstValueRow = tableModel.getValueAt(row, 0).toString().charAt(0);
                    String columnName = tableModel.getColumnName(column);

                    try {
                        managerView.getPresenter().searchState(newValue);
                        managerView.getPresenter().addTransition(oldValue, newValue, columnName, firstValueRow);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        userChangeTable = false;
                        tableModel.setValueAt("", row, column);
                        userChangeTable = true;
                    }
                }
            }
        };

        TableCellListener tcl = new TableCellListener(transitionsTable, action);
    }


    public void updateInterface() {
        userChangeTable = false;
        for (State state : managerView.getPresenter().getStates()) {
            tableModel.addColumn(state.getName());
            revalidate();
        }

        for (char symbol : managerView.getPresenter().getSymbols()) {
            Object[] newRow = new Object[tableModel.getColumnCount()];
            newRow[0] = symbol;
            tableModel.addRow(newRow);
        }

        List<Transition> transitions = managerView.getPresenter().getTransitions();
        for (Transition transition : transitions) {
            int[] rowAndCol = updateTableCells(String.valueOf(transition.getSymbol()), transition.getOriginState().getName());
            System.out.println("VOy a guardar el valor en la fila: " + rowAndCol[1] + " y en la columna: " + rowAndCol[0]);
            tableModel.setValueAt(transition.getDestinationState().getName(), rowAndCol[0], rowAndCol[1]);
        }
        userChangeTable = true;
    }

    private int[] updateTableCells(String rowValue, String columnValue) {
        int[] result = new int[2];
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object value = tableModel.getValueAt(i, 0);
            if (value != null) {
                String row = value.toString();
                if (row.equals(rowValue)) {
                    result[0] = i;
                    break;
                }
            }
        }
        for (int j = 0; j < tableModel.getColumnCount(); j++) {
            String col = tableModel.getColumnName(j);
            if (col.equals(columnValue)) {
                result[1] = j;
                break;
            }
        }

        return result;
    }
}
