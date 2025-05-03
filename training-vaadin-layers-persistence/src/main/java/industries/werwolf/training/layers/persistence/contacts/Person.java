package industries.werwolf.training.layers.persistence.contacts;

public interface Person {
    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    PhoneNumber getPhoneNumber();
    void setPhoneNumber(String countryCode, String areaCode, String number);

    Address getAddress();
    void setAddress(String type, String address1, String address2, String zipCode, String city, String country);
}
