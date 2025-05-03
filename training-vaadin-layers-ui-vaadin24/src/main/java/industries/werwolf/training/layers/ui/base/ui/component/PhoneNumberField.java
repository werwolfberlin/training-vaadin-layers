package industries.werwolf.training.layers.ui.base.ui.component;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import industries.werwolf.training.layers.persistence.contacts.PhoneNumber;
import io.micrometer.common.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneNumberField extends Composite<Div> implements HasValue<HasValue.ValueChangeEvent<PhoneNumber>, PhoneNumber> {

    private final Collection<ValueChangeListener<? super ValueChangeEvent<PhoneNumber>>> listeners = Collections.synchronizedSet(new LinkedHashSet<>());
    private final Div content = new Div();
    private final Binder<PhoneNumber> binder = new Binder<>();
    private final TextField countryCode = new TextField();
    private final TextField areaCode = new TextField();
    private final TextField number = new TextField();
    private boolean readOnly;
    private PhoneNumber oldValue;

    public PhoneNumberField() {
        countryCode.setPlaceholder("Country Code");
        countryCode.setWidth(120, Unit.PIXELS);

        areaCode.setPlaceholder("Area Code");
        areaCode.setWidth(120, Unit.PIXELS);

        number.setPlaceholder("Number");
        number.setWidth(150, Unit.PIXELS);
        number.addClassName(LumoUtility.Flex.AUTO);

        Div fields = new Div(countryCode, areaCode, number);
        fields.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL, LumoUtility.AlignItems.END);

        Span errorSpan = new Span();
        errorSpan.setWidth(100, Unit.PERCENTAGE);
        errorSpan.addClassNames(LumoUtility.TextColor.ERROR, LumoUtility.Whitespace.NORMAL, LumoUtility.Display.BLOCK);

        content.add(fields, errorSpan);

        Map<HasValue<?, ?>, String> statusMap = new HashMap<>();

        binder.setValidationStatusHandler(statusChange -> {
            if (statusChange.getFieldValidationErrors().isEmpty()) {
                statusMap.clear();
            }
            statusChange.getFieldValidationErrors().forEach(f -> {
                statusMap.put(f.getField(), f.isError() ? f.getMessage().orElse("") : "");
                if (f.getField() == countryCode) {
                    countryCode.setInvalid(f.isError());
                } else if (f.getField() == areaCode) {
                    areaCode.setInvalid(f.isError());
                } else if (f.getField() == number) {
                    number.setInvalid(f.isError());
                }
            });
            errorSpan.setText(statusMap.values().stream().filter(s -> !s.isEmpty()).collect(Collectors.joining(" ")));
        });

        binder.addValueChangeListener(this::fireStatusChanged);

        binder.forField(countryCode)
                .withValidator(c -> StringUtils.isBlank(c) || c.length() <= 4, "Country code must not be longer than 4 characters")
                .withValidator(new RegexpValidator("Please use just Numbers and + for the Country Code.", "[\\+]?[0-9]*"))
                .bind(PhoneNumber::getCountryCode, PhoneNumber::setCountryCode);

        binder.forField(areaCode)
                .withValidator(c -> StringUtils.isBlank(c) || c.length() <= 6, "Area code must not be longer than 6 characters")
                .withValidator(new RegexpValidator("Please use just Numbers for the Area Code.", "[0-9]*"))
                .bind(PhoneNumber::getAreaCode, PhoneNumber::setAreaCode);

        binder.forField(number)
                .withValidator(c -> StringUtils.isBlank(c) || c.length() <= 10, "Number must not be longer than 10 characters")
                .withValidator(new RegexpValidator("Please use just Numbers for the number.", "[0-9]*"))
                .bind(PhoneNumber::getNumber, PhoneNumber::setNumber);
    }

    private void fireStatusChanged(ValueChangeEvent<?> e) {
        new LinkedHashSet<>(listeners).forEach(l -> l.valueChanged(new AbstractField.ComponentValueChangeEvent<>(this, this, oldValue, e.isFromClient())));
        oldValue = binder.getBean();
    }

    public void setLabel(String label) {
        countryCode.setLabel(label);
    }

    @Override
    protected Div initContent() {
        return content;
    }

    @Override
    public void setValue(PhoneNumber value) {
        this.oldValue = value;
        binder.setBean(value);
    }

    @Override
    public PhoneNumber getValue() {
        return binder.getBean();
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<PhoneNumber>> listener) {
        return Registration.addAndRemove(listeners, listener);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        binder.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        countryCode.setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return countryCode.isRequiredIndicatorVisible();
    }
}
