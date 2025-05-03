package industries.werwolf.training.layers.persistence.contacts;

public interface PhoneNumber {
    String getCountryCode();
    void setCountryCode(String countryCode);
    String getAreaCode();
    void setAreaCode(String areaCode);
    String getNumber();
    void setNumber(String number);
}
