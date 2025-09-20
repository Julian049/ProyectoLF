package co.edu.uptc.model;

import co.edu.uptc.presenter.ContractMVP;

public class ManagerModel implements ContractMVP.Model {

    private ContractMVP.Presenter presenter;
    private DFA dfa;



    @Override
    public void setPresenter(ContractMVP.Presenter presenter) {
        this.presenter = presenter;
    }
}
