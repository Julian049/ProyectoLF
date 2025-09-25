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
    public List<Character> getSymbols() {
        return model.getSymbols();
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
// Estados
            model.addState("q0");
            model.searchState("q0").setInitial(true);
            model.addState("q1");
            model.searchState("q1").setFinal(true);

// Símbolos
            model.addSymbol("0");
            model.addSymbol("1");

// Transiciones
// q0 --> q0 : 0
            model.addTransition("", "q0", "q0", Character.valueOf('0'));
// q0 --> q1 : 1
            model.addTransition("", "q1", "q0", Character.valueOf('1'));
// q1 --> q0 : 0
            model.addTransition("", "q0", "q1", Character.valueOf('0'));
// q1 --> q1 : 1
            model.addTransition("", "q1", "q1", Character.valueOf('1'));


        } catch (ObjectAlreadyExists e) {
            throw new RuntimeException(e);
        }


        view.addInfo();
        //thread.start();
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
