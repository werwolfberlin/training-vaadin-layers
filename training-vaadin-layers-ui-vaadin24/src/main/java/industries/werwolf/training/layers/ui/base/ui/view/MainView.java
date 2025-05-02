package industries.werwolf.training.layers.ui.base.ui.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import industries.werwolf.training.layers.ui.base.ui.component.ViewToolbar;
import jakarta.annotation.security.PermitAll;

/**
 * This view shows up when a user navigates to the root ('/') of the application.
 */
@Route
@PermitAll
public final class MainView extends Main {

    // TODO Replace with your own main view.

    MainView() {
        addClassName(LumoUtility.Padding.MEDIUM);
        add(new ViewToolbar("Main"));
        add(new Div("Please select a view from the menu on the left."));
        add(new Image("VaadinLogomark_RGB_500x500.png", "Vaadin logo"));
    }
}
