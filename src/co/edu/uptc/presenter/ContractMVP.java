package co.edu.uptc.presenter;

public interface ContractMVP {

    interface Model {
        void setPresenter(Presenter presenter);

    }

    interface Presenter {
        void setModel(Model model);
        void setView(View view);
        void run();
    }

    interface View {
        void setPresenter(Presenter presenter);
        void initUI();
    }
}
