package org.geldikYoktunuz;

public class City {
    private String cityName;
    private String cityId;
    public City(String cityName, String cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
    }
    public String getCityName() {
        return cityName;
    }
    public String getCityId() {
        return cityId;
    }
}
