package industries.werwolf.training.layers.presenter.base;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
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
