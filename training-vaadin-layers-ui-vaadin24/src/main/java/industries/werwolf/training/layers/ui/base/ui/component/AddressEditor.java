package industries.werwolf.training.layers.ui.base.ui.component;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import industries.werwolf.training.layers.persistence.contacts.Address;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

public class AddressEditor extends Div implements HasValue<HasValue.ValueChangeEvent<Address>, Address> {
    private final Collection<ValueChangeListener<? super ValueChangeEvent<Address>>> listeners = Collections.synchronizedSet(new LinkedHashSet<>());
    private final Binder<Address> binder = new Binder<>();
    private final TextField address1 = new TextField();
    private final TextField address2 = new TextField();
    private final TextField zipCode = new TextField();
    private final TextField city = new TextField();
    private final TextField country = new TextField();
    private Address oldValue;
    private boolean readOnly;

    public AddressEditor() {
        address1.setPlaceholder("Address Line 1");
        address2.setPlaceholder("Address Line 2");
        zipCode.setPlaceholder("Zip Code");
        city.setPlaceholder("City");
        country.setPlaceholder("Country");

        city.addClassName(LumoUtility.Flex.AUTO);

        Div cityDiv = new Div(zipCode, city);
        cityDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.MEDIUM);

        add(address1, address2, cityDiv, country);
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.Gap.MEDIUM, LumoUtility.AlignItems.STRETCH);

        binder.forField(address1).bind(Address::getAddress1, Address::setAddress1);
        binder.forField(address2).bind(Address::getAddress2, Address::setAddress2);
        binder.forField(zipCode).bind(Address::getZipCode, Address::setZipCode);
        binder.forField(city).bind(Address::getCity, Address::setCity);
        binder.forField(country).bind(Address::getCountry, Address::setCountry);

        binder.addValueChangeListener(this::fireStatusChanged);
    }

    private void fireStatusChanged(ValueChangeEvent<?> e) {
        new LinkedHashSet<>(listeners).forEach(l -> l.valueChanged(new AbstractField.ComponentValueChangeEvent<>(this, this, oldValue, e.isFromClient())));
        oldValue = binder.getBean();
    }

    @Override
    public void setValue(Address value) {
        this.oldValue = value;
        binder.setBean(value);
    }

    @Override
    public Address getValue() {
        return binder.getBean();
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<Address>> listener) {
        return Registration.addAndRemove(listeners, listener);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        address1.setReadOnly(readOnly);
        address2.setReadOnly(readOnly);
        zipCode.setReadOnly(readOnly);
        city.setReadOnly(readOnly);
        country.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        address1.setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return address1.isRequiredIndicatorVisible();
    }

    public void setLabel(String label) {
        address1.setLabel(label);
    }
}
