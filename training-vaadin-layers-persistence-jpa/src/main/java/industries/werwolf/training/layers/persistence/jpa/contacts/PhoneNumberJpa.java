package industries.werwolf.training.layers.persistence.jpa.contacts;

import industries.werwolf.training.layers.persistence.contacts.PhoneNumber;
import industries.werwolf.training.layers.persistence.jpa.base.AbstractEntityJpa;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

@Entity(name = "PhoneNumber")
@Table(name = "phone_number")
public class PhoneNumberJpa extends AbstractEntityJpa<Long> implements PhoneNumber {

    @Id
    @Column(name = "phone_number_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_code", length = 4)
    @Nullable
    private String countryCode;

    @Column(name = "area_code", length = 6)
    @Nullable
    private String areaCode;

    @Column(name = "number", nullable = false, length = 10)
    private String number;

    public PhoneNumberJpa() {
    }

    public PhoneNumberJpa(@Nullable String countryCode, @Nullable String areaCode, String number) {
        this.countryCode = countryCode;
        this.areaCode = areaCode;
        this.number = number;
    }

    @Override
    public @Nullable Long getId() {
        return id;
    }

    @Override
    @Nullable
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public void setCountryCode(@Nullable String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    @Nullable
    public String getAreaCode() {
        return areaCode;
    }

    @Override
    public void setAreaCode(@Nullable String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }
}
