package org.geldikYoktunuz;

public class Main {

    public static void main(String[] args) {

        Customer customer1=new Customer(1221,"Ahmed Er","photoAdress");
        Cargo cargo1=new Cargo(12,"20 mayıs 2025","23 mayıs 2025",3,"Teslimatta",false);
        Cargo cargo2=new Cargo(12,"1 haziran 2025","12 haziran 2025",11,"İşleme alındı",true);
        customer1.addCargo(cargo1);
        customer1.addCargo(cargo2);
        System.out.println(customer1);

        MainFrame mainFrame = new MainFrame();
    }

}