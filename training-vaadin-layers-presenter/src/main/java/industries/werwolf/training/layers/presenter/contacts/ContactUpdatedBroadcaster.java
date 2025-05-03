package industries.werwolf.training.layers.presenter.contacts;

import industries.werwolf.training.layers.service.util.Registration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

@Component
@SessionScope
public class ContactUpdatedBroadcaster {
    private final Collection<Runnable> listeners = Collections.synchronizedSet(new LinkedHashSet<>());

    public Registration addListener(Runnable listener) {
        return Registration.addAndRemove(listeners, listener);
    }

    public void contactUpdated(){
        new LinkedHashSet<>(listeners).forEach(Runnable::run);
    }
}
