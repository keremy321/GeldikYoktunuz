package org.geldikYoktunuz;

import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        // Test için bir müşteri nesnesi oluşturuyoruz
        Customer customer1 = new Customer("Ahmed Er", "photoAdress");
        Customer customer2 = new Customer("Ahmed", "photoAdress");
        Customer customer3 = new Customer("Er", "photoAdress");

        // 6 adet kargo nesnesi oluşturuyoruz
        Cargo cargo1 = new Cargo( "20 Mayıs 2025", "23 Mayıs 2025", 3, false, "Mehmet Aydın");
        Cargo cargo2 = new Cargo("1 Haziran 2025", "6 Temmuz 2025", 35, true, "Mehmet Aydın");
        Cargo cargo3 = new Cargo( "1 Haziran 2025", "20 Haziran 2025", 19, true, "Semih Bekdaş");
        Cargo cargo4 = new Cargo( "10 Temmuz 2025", "15 Temmuz 2025", 25, false, "Ahmet Yılmaz");
        Cargo cargo5 = new Cargo( "15 Temmuz 2025", "25 Temmuz 2025", 30, true, "Mehmet Aydın");
        Cargo cargo6 = new Cargo( "20 Temmuz 2025", "30 Temmuz 2025", 40, false, "Semih Bekdaş");
        cargo5.setCargoStatus(CargoStatus.DELIVERED);

        // Kargoları müşteriye ekliyoruz
        customer1.addCargo(cargo1);
        customer1.addCargo(cargo2);
        customer1.addCargo(cargo3);
        customer1.addCargo(cargo4);
        customer1.addCargo(cargo5);
        customer1.addCargo(cargo6); // 6. kargo da eklendi, böylece stack 5 kargo tutacak

        // Müşterinin son 5 kargosunu alıyoruz
        Stack<Cargo> recentCargos = customer1.getRecentCargosStack();

        // Son 5 gönderiyi yazdırıyoruz
        System.out.println("Son 5 Gönderi:");
        for (Cargo cargo : recentCargos) {
            System.out.println(cargo);
        }

        // Tüm gönderim geçmişini yazdırıyoruz
        System.out.println("\nTüm Gönderim Geçmişi:");
        for (Cargo cargo : customer1.getCargos()) {
            System.out.println(cargo);
        }

        // Teslim edilmiş kargoyu ID'ye göre arama
        int targetId = 14;
        Cargo foundCargo = customer1.binarySearchById(targetId);
        System.out.println("\nAranan Kargo (ID: " + targetId + "): " + (foundCargo != null ? foundCargo : "Bulunamadı"));

        Cargo foundCargo2 = customer1.binarySearchById(16);
        System.out.println("\nAranan Kargo (ID: " + targetId + "): " + (foundCargo2 != null ? foundCargo2 : "Bulunamadı"));

        // Teslim edilmemiş kargoları teslimat süresine göre sıralama
        List<Cargo> sortedUndeliveredCargos = customer1.getSortedUndeliveredCargosByDeliveryTime();
        System.out.println("\nTeslim Edilmemiş Kargolar (Teslimat Süresine Göre Sıralı):");
        for (Cargo cargo : sortedUndeliveredCargos) {
            System.out.println(cargo);
        }

        System.out.println("\nMüşteri Bilgileri:");
        System.out.println(customer1);
        System.out.println(customer2);
        System.out.println(customer3);


        // Kargo önceliği sıralaması
        System.out.println("\nKargo Öncelik Sırası:");
        System.out.println(CargoPrioritization.prioritization(customer1));

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