package industries.werwolf.training.layers.ui.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;

import java.util.Optional;
import java.util.function.Consumer;

public class UiUtils {
    public static void doInUiThread(Component component, Consumer<UI> command) {
        doInUiThread(component.getUI(), command);
    }

    public static void doInUiThread(Optional<UI> ui, Consumer<UI> command) {
        ui.ifPresent(u -> doInUiThread(u, command));
    }

    public static void doInUiThread(UI ui, Consumer<UI> command) {
        if (ui.isClosing()) {
            return;
        }
        ui.access(() -> command.accept(ui));
    }

}
