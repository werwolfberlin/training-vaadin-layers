package industries.werwolf.training.layers.presenter.base;

public class MainLayoutPresenter {

    private MainLayout view;

    public void init(MainLayout view) {
        this.view = view;
    }

    public void logoutClicked() {
        view.logout();
        view.navigateHome();

    }
}
