package org.geldikYoktunuz;

public class City {
    private String cityName;
    private int cityId;
    public City(String cityName, int cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
    }
    public String getCityName() {
        return cityName;
    }
    public int getCityId() {
        return cityId;
    }

    @Override
    public String toString() {
        return cityName;
    }
}
