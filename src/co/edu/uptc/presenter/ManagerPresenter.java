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
        view.addInfo();
        importDFA1("/home/julian/Documents/ProyectoLF/terminados1.json");
    }

    private void makeMVP() {
        ManagerView managerView = new ManagerView();
        managerView.setPresenter(this);

        ManagerModel managerModel = new ManagerModel();
        managerModel.setPresenter(this);

        setView(managerView);
        setModel(managerModel);
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

    private void importDFA1(String path) {
        if (model.canExport()) {
            boolean confirmed = view.confirmReplaceData();
            if (!confirmed) {
                return;
            }
        }

        String filePath = path;
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
