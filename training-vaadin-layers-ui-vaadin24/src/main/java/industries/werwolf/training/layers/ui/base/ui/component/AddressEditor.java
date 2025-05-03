package industries.werwolf.training.layers.ui.base.ui.component;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import industries.werwolf.training.layers.persistence.contacts.Address;

public class AddressEditor extends Div implements HasValue<HasValue.ValueChangeEvent<Address>, Address> {

    public AddressEditor() {
    }

    @Override
    public void setValue(Address value) {
    }

    @Override
    public Address getValue() {
        return null;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<Address>> listener) {
        return null;
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

    public void setLabel(String label) {
    }
}
