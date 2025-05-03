package industries.werwolf.training.layers.persistence.contacts;

public interface Address {
    String getAddress1();
    void setAddress1(String address1);
    String getAddress2();
    void setAddress2(String address2);
    String getZipCode();
    void setZipCode(String zipCode);
    String getCity();
    void setCity(String city);
    String getCountry();
    void setCountry(String country);
}
