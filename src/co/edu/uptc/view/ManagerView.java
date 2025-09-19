package co.edu.uptc.view;

import co.edu.uptc.presenter.ContractMVP;

public class ManagerView implements ContractMVP.View {

    private ContractMVP.Presenter presenter;

    @Override
    public void setPresenter(ContractMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initUI() {

    }
}
