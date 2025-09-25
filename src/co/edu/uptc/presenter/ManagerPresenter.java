package co.edu.uptc.presenter;

import co.edu.uptc.model.DFA;
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
    public void addTransition(String currentValue, String to, String from, char value) {
        model.addTransition(currentValue, to, from, value);
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
    public String validate(String value) throws NullException {
        return model.validate(value);
    }

    @Override
    public void setModel(ContractMVP.Model model) {
        this.model = model;
    }

    @Override
    public String generateStrings() {
        return model.generateStrings();
    }

    @Override
    public void run() {
        makeMVP();
        view.initUI();

        Thread thread = new Thread(() -> {
            while (true) {
                for (Transition transition : model.getTransitions()) {
                    System.out.println(transition);
                    System.out.println(getTransitions().size());
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
// Estados
            model.addState("A");
            model.searchState("A").setInitial(true);
            model.addState("B");
            model.addState("C");
            model.addState("D");
            model.addState("E");
            model.addState("F");
            model.addState("G");
            model.addState("H");
            model.searchState("H").setFinal(true);

// Agregar símbolos
            model.addSymbol("0");
            model.addSymbol("1");
            model.addSymbol("2");

// Agregar transiciones (AFD - determinista)
            model.addTransition("", "D", "A", Character.valueOf('0'));
            model.addTransition("", "F", "A", Character.valueOf('1'));
            model.addTransition("", "B", "A", Character.valueOf('2'));
            model.addTransition("", "A", "B", Character.valueOf('0'));
            model.addTransition("", "A", "B", Character.valueOf('1'));
            model.addTransition("", "C", "B", Character.valueOf('2'));
            model.addTransition("", "G", "C", Character.valueOf('0'));
            model.addTransition("", "A", "C", Character.valueOf('1'));
            model.addTransition("", "B", "C", Character.valueOf('2'));
            model.addTransition("", "A", "D", Character.valueOf('0'));
            model.addTransition("", "G", "D", Character.valueOf('1'));
            model.addTransition("", "A", "D", Character.valueOf('2'));
            model.addTransition("", "E", "E", Character.valueOf('0'));
            model.addTransition("", "F", "E", Character.valueOf('1'));
            model.addTransition("", "G", "E", Character.valueOf('2'));
            model.addTransition("", "H", "F", Character.valueOf('0'));
            model.addTransition("", "E", "F", Character.valueOf('1'));
            model.addTransition("", "B", "F", Character.valueOf('2'));
            model.addTransition("", "C", "G", Character.valueOf('0'));
            model.addTransition("", "D", "G", Character.valueOf('1'));
            model.addTransition("", "E", "G", Character.valueOf('2'));
            model.addTransition("", "H", "H", Character.valueOf('0'));
            model.addTransition("", "H", "H", Character.valueOf('1'));
            model.addTransition("", "H", "H", Character.valueOf('2'));


        } catch (ObjectAlreadyExists e) {
            throw new RuntimeException(e);
        }


        view.addInfo();
        //thread.start();
    }

    @Override
    public DFA getDFA() {
        return model.getDFA();
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


    //Exportar
    @Override
    public void exportDFA() {
        if (!model.canExport()) {
            view.showMessage("No hay ningún DFA para exportar", "Sin Datos", false);
            return;
        }

        String filePath = view.showSaveFileDialog("automata.json");
        if (filePath != null) {
            if (model.exportDFA(filePath)) {
                view.showMessage("DFA exportado exitosamente", "Exportación Exitosa", true);
            } else {
                view.showMessage("Error al exportar el archivo", "Error", false);
            }
        }
    }

    //Importar
    @Override
    public void importDFA() {
        if (model.canExport()) {
            boolean confirmed = view.confirmReplaceData();
            if (!confirmed) {
                return;
            }
        }

        String filePath = view.showOpenFileDialog();
        if (filePath != null) {
            try {
                DFA importedDFA = model.importDFA(filePath);
                model.replaceDFA(importedDFA);
                view.showMessage("DFA importado exitosamente", "Importacion Exitosa", true);
                view.addInfo();
            } catch (Exception ex) {
                view.showMessage("Error al importar el archivo" + ex.getMessage(), "Error", false);
            }
        }
    }
}
