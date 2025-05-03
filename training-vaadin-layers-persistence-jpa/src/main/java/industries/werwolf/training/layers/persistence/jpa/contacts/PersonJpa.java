package industries.werwolf.training.layers.persistence.jpa.contacts;

import industries.werwolf.training.layers.persistence.contacts.Address;
import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.persistence.contacts.PhoneNumber;
import industries.werwolf.training.layers.persistence.jpa.base.AbstractEntityJpa;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

@Entity(name = "Person")
@Table(name = "person")
public class PersonJpa extends AbstractEntityJpa<Long> implements Person {

    @Id
    @Column(name = "person_id")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @OneToOne(targetEntity = PhoneNumberJpa.class)
    @JoinColumn(name = "phone_number_id")
    private PhoneNumber phoneNumber;

    @OneToOne(targetEntity = AddressJpa.class)
    @JoinColumn(name = "address_id")
    private Address address;

    @Override
    public @Nullable Long getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String countryCode, String areaCode, String number) {
        phoneNumber = new PhoneNumberJpa(countryCode, areaCode, number);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setAddress(String type, String address1, String address2, String zipCode, String city, String country) {
        address = new AddressJpa(address1, address2, zipCode, city, country);
    }
}
