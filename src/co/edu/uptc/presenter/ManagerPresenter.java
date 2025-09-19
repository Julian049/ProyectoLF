package co.edu.uptc.presenter;

import co.edu.uptc.model.ManagerModel;
import co.edu.uptc.view.ManagerView;

public class ManagerPresenter implements ContractMVP.Presenter {

    private ContractMVP.View view;
    private ContractMVP.Model model;

    @Override
    public void setView(ContractMVP.View view) {
        this.view = view;
    }

    @Override
    public void setModel(ContractMVP.Model model) {
        this.model = model;
    }

    @Override
    public void run() {
        makeMVP();
        //view.initUI();
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
}
