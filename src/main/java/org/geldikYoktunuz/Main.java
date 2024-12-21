package org.geldikYoktunuz;

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