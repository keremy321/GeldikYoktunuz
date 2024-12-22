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
        Cargo cargo1 = new Cargo( CurrentDate.currentDate.plusDays(1), false, "Mehmet Aydın", "Photo","Cargo1", CityStorage.getCityById(35), customer1);
        Cargo cargo2 = new Cargo( CurrentDate.currentDate.plusDays(1), false, "Mehmet Aydın", "Photo","Cargo2",CityStorage.getCityById(10), customer1);
        Cargo cargo3 = new Cargo( CurrentDate.currentDate.plusDays(1), false, "Ahmet Yılmaz", "Photo","Cargo3",CityStorage.getCityById(41), customer2);
        Cargo cargo4 = new Cargo( CurrentDate.currentDate, false, "Semih Bekdaş", "Photo","Cargo4",CityStorage.getCityById(45), customer4);
        Cargo cargo5 = new Cargo( CurrentDate.currentDate, true, "Mehmet Aydın", "Photo","Cargo5",CityStorage.getCityById(11), customer3);
        Cargo cargo6 = new Cargo( CurrentDate.currentDate, true, "Semih Bekdaş", "Photo","Cargo6",CityStorage.getCityById(64), customer1);
//        cargo5.setCargoStatus(CargoStatus.DELIVERED);



////         Kargoları müşteriye ekliyoruz
//        customer1.addCargo(cargo1);
//        customer2.addCargo(cargo2);
//        customer1.addCargo(cargo3);
//        customer3.addCargo(cargo4);
//        customer1.addCargo(cargo5);
//        customer4.addCargo(cargo6); // 6. kargo da eklendi, böylece stack 5 kargo tutacak

//        // Müşterinin son 5 kargosunu alıyoruz
////        Stack<Cargo> recentCargos = customer1.getRecentCargosStack();
//
//        // Son 5 gönderiyi yazdırıyoruz
//        System.out.println("Son 5 Gönderi:");
//        for (Cargo cargo : recentCargos) {
//            System.out.println(cargo);
//        }
//
//        // Tüm gönderim geçmişini yazdırıyoruz
//        System.out.println("\nTüm Gönderim Geçmişi:");
//        for (Cargo cargo : customer1.getCargos()) {
//            System.out.println(cargo);
//        }
//
//        // Teslim edilmiş kargoyu ID'ye göre arama
//        int targetId = 14;
//        Cargo foundCargo = customer1.binarySearchById(targetId);
//        System.out.println("\nAranan Kargo (ID: " + targetId + "): " + (foundCargo != null ? foundCargo : "Bulunamadı"));
//
//        Cargo foundCargo2 = customer1.binarySearchById(16);
//        System.out.println("\nAranan Kargo (ID: " + targetId + "): " + (foundCargo2 != null ? foundCargo2 : "Bulunamadı"));
//
//        // Teslim edilmemiş kargoları teslimat süresine göre sıralama
//        List<Cargo> sortedUndeliveredCargos = customer1.getSortedUndeliveredCargosByDeliveryTime();
//        System.out.println("\nTeslim Edilmemiş Kargolar (Teslimat Süresine Göre Sıralı):");
//        for (Cargo cargo : sortedUndeliveredCargos) {
//            System.out.println(cargo);
//        }
//
//        System.out.println("\nMüşteri Bilgileri:");
//        System.out.println(customer1);
//        System.out.println(customer2);
//        System.out.println(customer3);
//
//
//        // Kargo önceliği sıralaması
//        System.out.println("\nKargo Öncelik Sırası:");
//        System.out.println(CargoPrioritization.prioritization(customer1));
//
        CargoRouting cr = new CargoRouting();
        List<Cargo> allCargos=new ArrayList<>(CargoStorage.getAllCargos().values());
        cr.routing(allCargos);
//        System.out.println(cargo1.getPostDate().plusDays(1));
//        System.out.println(cargo1.getPostDate());



        SignInFrame signInFrame = new SignInFrame();

    }
}

/*package org.geldikYoktunuz;

public class Main {

    public static void main(String[] args) {

        Customer customer1=new Customer(1221,"Ahmed Er","photoAdress");
        Cargo cargo1=new Cargo(12,"20 mayıs 2025","23 mayıs 2025",3,false,"Mehmet Aydın");
        Cargo cargo2=new Cargo(12,"1 haziran 2025","6 temmuz 2025",35,true,"Mehmet Aydın");
        Cargo cargo3=new Cargo(12,"1 haziran 2025","20 haziran 2025",19,true,"Semih Bekdaş");
        cargo1.setCargoStatus(CargoStatus.DELIVERED);
        customer1.addCargo(cargo1);
        customer1.addCargo(cargo2);
        customer1.addCargo(cargo3);
        System.out.println("\n\n");
        System.out.println(CargoPrioritization.prioritization(customer1));
        System.out.println("\n\n");
        System.out.println(customer1);


//        MainFrame mainFrame = new MainFrame();
    }

}
 */