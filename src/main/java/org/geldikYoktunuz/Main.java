package org.geldikYoktunuz;

import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Customer customer1 = new Customer("Ahmed Er", "Faruk", "/dialogButtons/man.png");
        Customer customer2 = new Customer("Emre", "KarateKid", "/dialogButtons/woman.png");
        Customer customer3 = new Customer("Mahmut", "Mahmutoğlu", "/dialogButtons/man.png");
        Customer customer4 = new Customer("İbrahim", "Çetin", "/dialogButtons/woman.png");


        // 6 adet kargo nesnesi oluşturuyoruz
        Cargo cargo1 = new Cargo( CurrentDate.currentDate.plusDays(1), false, "Mehmet Aydın", "/dialogButtons/man.png","Cargo1", CityStorage.getCityById(35));
        Cargo cargo2 = new Cargo( CurrentDate.currentDate.plusDays(1), false, "Mehmet Aydın", "/dialogButtons/man.png","Cargo2",CityStorage.getCityById(10));
        Cargo cargo3 = new Cargo( CurrentDate.currentDate.plusDays(1), false, "Ahmet Yılmaz", "/dialogButtons/man.png","Cargo3",CityStorage.getCityById(41));
        Cargo cargo4 = new Cargo( CurrentDate.currentDate, false, "Semih Bekdaş", "/dialogButtons/man.png","Cargo4",CityStorage.getCityById(45));
        Cargo cargo5 = new Cargo( CurrentDate.currentDate, true, "Mehmet Aydın", "/dialogButtons/man.png","Cargo5",CityStorage.getCityById(11));
        Cargo cargo6 = new Cargo( CurrentDate.currentDate, true, "Semih Bekdaş", "/dialogButtons/man.png","Cargo6",CityStorage.getCityById(54));
        Cargo cargo7 = new Cargo( CurrentDate.currentDate, true, "Semih Bekdaş", "/dialogButtons/man.png","Cargo7",CityStorage.getCityById(22));
        Cargo cargo8 = new Cargo( CurrentDate.currentDate, true, "Semih Bekdaş", "/dialogButtons/man.png","Cargo8",CityStorage.getCityById(48));
        Cargo cargo9 = new Cargo( CurrentDate.currentDate, true, "Semih Bekdaş", "/dialogButtons/man.png","Cargo9",CityStorage.getCityById(16));
        Cargo cargo10 = new Cargo( CurrentDate.currentDate, true, "Semih Bekdaş", "/dialogButtons/man.png","Cargo10",CityStorage.getCityById(64));

        customer1.addCargo(cargo1);
        customer2.addCargo(cargo2);
        customer1.addCargo(cargo3);
        customer3.addCargo(cargo4);
        customer1.addCargo(cargo5);
        customer4.addCargo(cargo6);
        customer1.addCargo(cargo7);
        customer1.addCargo(cargo8);
        customer1.addCargo(cargo9);
        customer1.addCargo(cargo10);

        CargoRouting cr = new CargoRouting();
        List<Cargo> allCargos=new ArrayList<>(CargoStorage.getAllCargos());
        cr.routing(allCargos);

        SignInFrame signInFrame = new SignInFrame();

    }
}