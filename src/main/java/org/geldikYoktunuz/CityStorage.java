package org.geldikYoktunuz;

import java.util.HashMap;
import java.util.Map;

public class CityStorage {

    private static Map<Integer, City> cityMap = new HashMap<>();

    static {
        addCity(new City("Istanbul", 34));
        addCity(new City("Kocaeli", 41));
        addCity(new City("Bursa", 16));
        addCity(new City("Tekirdag", 59));
        addCity(new City("Yalova", 77));
        addCity(new City("Sakarya", 54));
        addCity(new City("Bilecik", 11));
        addCity(new City("Edirne", 22));
        addCity(new City("Canakkale", 17));
        addCity(new City("Kirklareli", 39));
        addCity(new City("Balikesir", 10));
        addCity(new City("Izmir", 35));
        addCity(new City("Aydin", 9));
        addCity(new City("Manisa", 45));
        addCity(new City("Kutahya", 43));
        addCity(new City("Usak", 64));
        addCity(new City("Denizli", 20));
        addCity(new City("Afyonkarahisar", 3));
        addCity(new City("Mugla", 48));

    }

    public static void addCity(City city) {
        cityMap.put(city.getCityId(), city);
    }

    public static City getCityById(int cityId) {
        return cityMap.get(cityId);
    }

    public static Map<Integer, City> getAllCities() {
        return cityMap;
    }

    public static String[] getAllCityNames() {
        return cityMap.values().stream()
                .map(City::getCityName)
                .toArray(String[]::new);
    }
}
