package industries.werwolf.training.layers.ui.base.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import industries.werwolf.training.layers.persistence.contacts.PhoneNumber;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

public class PhoneNumberField extends Composite<Div> implements HasValue<HasValue.ValueChangeEvent<PhoneNumber>, PhoneNumber> {

    private final Collection<ValueChangeListener<? super ValueChangeEvent<PhoneNumber>>> listeners = Collections.synchronizedSet(new LinkedHashSet<>());

    public PhoneNumberField() {
    }

    public void setLabel(String label) {
    }

    @Override
    protected Div initContent() {
        return null;
    }

    @Override
    public void setValue(PhoneNumber value) {
    }

    @Override
    public PhoneNumber getValue() {
        return null;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<PhoneNumber>> listener) {
        return Registration.addAndRemove(listeners, listener);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }
}
