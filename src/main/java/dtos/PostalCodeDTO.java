package dtos;

import entities.CityInfo;

public class PostalCodeDTO {

    private String zipCode;
    private String city;

    public PostalCodeDTO() {
    }

    public PostalCodeDTO(CityInfo cityInfo){
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
