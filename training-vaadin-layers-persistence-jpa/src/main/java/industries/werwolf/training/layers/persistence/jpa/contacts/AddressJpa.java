package industries.werwolf.training.layers.persistence.jpa.contacts;

import industries.werwolf.training.layers.persistence.contacts.Address;
import industries.werwolf.training.layers.persistence.jpa.base.AbstractEntityJpa;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

@Entity(name = "Address")
@Table(name = "address")
public class AddressJpa extends AbstractEntityJpa<Long> implements Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_1", nullable = false)
    private String address1;

    @Column(name = "address_2")
    @Nullable
    private String address2;

    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    @Column(name = "city", nullable = false, length = 255)
    private String city;

    @Column(name = "country", nullable = false, length = 64)
    private String country;

    public AddressJpa() {
    }

    public AddressJpa(String address1, String address2, String zipCode, String city, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public @Nullable Long getId() {
        return id;
    }

    @Override
    public String getAddress1() {
        return address1;
    }

    @Override
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Override
    public String getAddress2() {
        return address2;
    }

    @Override
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }
}
