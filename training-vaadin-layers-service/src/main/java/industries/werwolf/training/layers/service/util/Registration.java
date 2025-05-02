package industries.werwolf.training.layers.service.util;

import java.util.Collection;

@FunctionalInterface
public interface Registration {

    void remove();

    static <T> Registration addAndRemove(Collection<T> listeners, T listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }
}
