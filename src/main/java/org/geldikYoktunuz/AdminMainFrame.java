package org.geldikYoktunuz;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdminMainFrame extends JFrame {

    public AdminMainFrame() {
        this.setName("adminFrame");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        JLayeredPane frameLayer = new JLayeredPane();
        frameLayer.setBounds(0,0,1116,739);

        JLayeredPane homeLayer = new JLayeredPane();
        homeLayer.setName("home");
        homeLayer.setBounds(0,0,1116,739);

        JLayeredPane deliveryLayer = new JLayeredPane();
        deliveryLayer.setName("delivery");
        deliveryLayer.setBounds(0,0,1116,739);


        JLayeredPane managementLayer = new JLayeredPane();
        managementLayer.setName("management");
        managementLayer.setBounds(0,0,1116,739);

        JLayeredPane accountLayer = new JLayeredPane();
        accountLayer.setName("account");
        accountLayer.setBounds(0,0,1116,739);

        JLayeredPane[] layers = {homeLayer, deliveryLayer, managementLayer, accountLayer};

//      HOME LAYER

        BackgroundImage bgHome = new BackgroundImage("/backgrounds/bgHome.png");
        bgHome.setBounds(0, 0, 1100, 700);

//      MANAGEMENT LAYER

        BackgroundImage bgManagement = new BackgroundImage("/backgrounds/bgManagement.png");
        bgManagement.setBounds(0, 0, 1100, 700);

        RoundedSearchBar searchBarAll = new RoundedSearchBar();
        searchBarAll.setBounds(740, 8, 300, 50);

        List<Object[]> data1List = new ArrayList<>();

        for (Customer customer : CustomerStorage.getAllCustomers().values()) {
            data1List.add(new Object[]{
                    customer.getCustomerId(),
                    customer.getCustomerName() + " " + customer.getCustomerSurname(),
                    customer.getCustomerPhoto()
            });
        }

        Object[][] data1 = data1List.toArray(new Object[0][]);

        // Column names
        String[] columnNames1 = {"ID", "Customer", "Photo Path"};

        // Create the custom table
        CustomTable customTable1 = new CustomTable(data1, columnNames1);
        customTable1.setBounds(201, 296, 800, 155);

        List<Object[]> data2List = new ArrayList<>();

// Populate the list with cargo details
        for (Cargo cargo : CargoStorage.getAllCargos().values()) {
            data2List.add(new Object[]{
                    cargo.getPostId(),
                    cargo.getCargoName(),
                    cargo.getCustomer().getCustomerName() + " " + cargo.getCustomer().getCustomerSurname(),
                    cargo.getPostDate().format(df),
                    cargo.getDeliveryDate(),
                    cargo.getCity().getCityName(),
                    cargo.getCargoStatus().toString()
            });
        }

// Convert the list to a two-dimensional array
        Object[][] data2 = data2List.toArray(new Object[0][]);

// Column names
        String[] columnNames2 = {"ID", "Cargo Name", "Customer", "Shipment Date", "Delivery Date", "Destination City", "Cargo Status"};

// Create the custom table using data2 and columnNames2
        CustomTable customTable2 = new CustomTable(data2, columnNames2);
        customTable2.setBounds(201, 510, 800, 155);


        JLabel labelEffectAddUser = new JLabel();
        labelEffectAddUser.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectAddUser.setBounds(125, 22, 286, 256);
        labelEffectAddUser.setVisible(false);

        JLabel labelAddUser = new JLabel();
        labelAddUser.setName("addUser");
        labelAddUser.setIcon(new ImageIcon(getClass().getResource("/managementButtons/addUser.png")));
        labelAddUser.setBounds(193, 90, 150, 120);
        labelAddUser.addMouseListener(new ManagementMouseListener(labelAddUser, labelEffectAddUser, "/managementButtons/pressed.png", this));

        JLabel labelEffectEditUser = new JLabel();
        labelEffectEditUser.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectEditUser.setBounds(347, 22, 286, 256);
        labelEffectEditUser.setVisible(false);

        JLabel labelEditUser = new JLabel();
        labelEditUser.setName("editUser");
        labelEditUser.setIcon(new ImageIcon(getClass().getResource("/managementButtons/editUser.png")));
        labelEditUser.setBounds(415, 90, 150, 120);
        labelEditUser.addMouseListener(new ManagementMouseListener(labelEditUser, labelEffectEditUser, "/managementButtons/pressed.png", this));

        JLabel labelEffectAddPackage = new JLabel();
        labelEffectAddPackage.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectAddPackage.setBounds(569, 22, 286, 256);
        labelEffectAddPackage.setVisible(false);

        JLabel labelAddPackage = new JLabel();
        labelAddPackage.setName("addPackage");
        labelAddPackage.setIcon(new ImageIcon(getClass().getResource("/managementButtons/addPackage.png")));
        labelAddPackage.setBounds(637, 90, 150, 120);
        labelAddPackage.addMouseListener(new ManagementMouseListener(labelAddPackage, labelEffectAddPackage, "/managementButtons/pressed.png", this));

        JLabel labelEffectEditPackage = new JLabel();
        labelEffectEditPackage.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectEditPackage.setBounds(791, 22, 286, 256);
        labelEffectEditPackage.setVisible(false);

        JLabel labelEditPackage = new JLabel();
        labelEditPackage.setName("editPackage");
        labelEditPackage.setIcon(new ImageIcon(getClass().getResource("/managementButtons/editPackage.png")));
        labelEditPackage.setBounds(859, 90, 150, 120);
        labelEditPackage.addMouseListener(new ManagementMouseListener(labelEditPackage, labelEffectEditPackage, "/managementButtons/pressed.png", this));

//      ACCOUNT LAYER

        BackgroundImage bgAccount = new BackgroundImage("/backgrounds/bgAccount.png");
        bgAccount.setBounds(0, 0, 1100, 700);


        Object[][] data = {
                {1, "Package A", "2024-12-01", "2024-12-03", "Delivered"},
                {2, "Package B", "2024-12-02", "2024-12-05", "In Transit"},
                {3, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {4, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {5, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {6, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {7, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {8, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {9, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {10, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {11, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {12, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {13, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {14, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {15, "Package C", "2024-12-03", "2024-12-06", "Pending"},
                {16, "Package C", "2024-12-03", "2024-12-06", "Pending"},
        };

        // Column names
        String[] columnNames = {"ID", "Customer", "Photo"};

        // Create the custom table
        CustomTable customTable = new CustomTable(data, columnNames);
        customTable.setBounds(201, 274, 800, 380);

        JTable tableCargos = customTable.getTable();



        RoundedSearchBar searchBar = new RoundedSearchBar(filterText -> filterTable(tableCargos, filterText));
        searchBar.setBounds(700, 218, 300, 50); // Position the search bar


//        CircularImagePanel circularImagePanel = new CircularImagePanel("CustomerStorage.getCurrentCustomer().getCustomerPhoto()", 150);
//        circularImagePanel.setBounds(201, 53, 150, 150);

        JLabel labelNameSurname = new JLabel();
        if (CustomerStorage.getCurrentCustomer() != null){
            labelNameSurname.setText(CustomerStorage.getCurrentCustomer().getCustomerName() + " " + CustomerStorage.getCurrentCustomer().getCustomerSurname());
            System.out.println("Current Customer: " + CustomerStorage.getCurrentCustomer().getCustomerName() + " " + CustomerStorage.getCurrentCustomer().getCustomerSurname());
        }
        else {
            labelNameSurname.setText("Default Name Surname");

            System.out.println("Current Customer: null");
        }

        labelNameSurname.setBounds(373,90, 680, 60);
        labelNameSurname.setForeground(new Color(0x34495e));

        JLabel labelID = new JLabel();
        if (CustomerStorage.getCurrentCustomer() != null){
            labelID.setText("#" + CustomerStorage.getCurrentCustomer().getCustomerId());
            System.out.println("Current Customer: " + CustomerStorage.getCurrentCustomer().getCustomerName() + " " + CustomerStorage.getCurrentCustomer().getCustomerSurname());
        }
        else {
            labelID.setText("Default ID");

            System.out.println("Current Customer: null");
        }

        labelID.setBounds(373,130, 680, 60);
        labelID.setForeground(new Color(0x34495e));
        try {
            Font montserratFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf")).deriveFont(36f);
            Font montserratLightFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Light.ttf")).deriveFont(26f);
            labelNameSurname.setFont(montserratFont);
            labelID.setFont(montserratLightFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            labelNameSurname.setFont(new Font("SansSerif", Font.PLAIN, 12));
            labelID.setFont(new Font("SansSerif", Font.PLAIN, 12));
        }

//      MAIN FRAME

        JLabel labelDelivery = new JLabel();
        labelDelivery.setIcon(new ImageIcon(getClass().getResource("/menuButtons/delivery.png")));
        labelDelivery.setBounds(25, 135, 50, 30);
        labelDelivery.addMouseListener(new MenuMouseListener(labelDelivery, "delivery", layers, homeLayer, this));

        JLabel labelManagement = new JLabel();
        labelManagement.setIcon(new ImageIcon(getClass().getResource("/menuButtons/management.png")));
        labelManagement.setBounds(28, 218, 46, 46);
        labelManagement.addMouseListener(new MenuMouseListener(labelManagement, "management", layers, managementLayer, this));

        JLabel labelHelp = new JLabel();
        labelHelp.setIcon(new ImageIcon(getClass().getResource("/menuButtons/help.png")));
        labelHelp.setBounds(25, 525, 50, 50);
        labelHelp.addMouseListener(new MenuMouseListener(labelHelp, "help", layers, homeLayer, this));

        JLabel labelAccount = new JLabel();
        labelAccount.setIcon(new ImageIcon(getClass().getResource("/menuButtons/account.png")));
        labelAccount.setBounds(25, 615, 50, 50);
        labelAccount.addMouseListener(new MenuMouseListener(labelAccount, "account", layers, accountLayer, this, labelNameSurname, labelID, null));

        JLabel labelCurrentDate = new JLabel();
        labelCurrentDate.setText(CurrentDate.getCurrentDate());
        labelCurrentDate.setBounds(0,430, 100, 26);
        labelCurrentDate.setForeground(new Color(0xf7f7f7));
        labelCurrentDate.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            Font montserratFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Medium.ttf")).deriveFont(17f);
            labelCurrentDate.setFont(montserratFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            labelCurrentDate.setFont(new Font("SansSerif", Font.PLAIN, 12));
        }

        JLabel labelSkipDay = new JLabel();
        labelSkipDay.setIcon(new ImageIcon(getClass().getResource("/menuButtons/skipDay.png")));
        labelSkipDay.setBounds(8, 404, 85, 20);
        labelSkipDay.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CurrentDate.passDay();
                labelCurrentDate.setText(CurrentDate.getCurrentDate());
                System.out.println("Current date: " + CurrentDate.getCurrentDate());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelSkipDay.setIcon(new ImageIcon(getClass().getResource("/menuButtons/skipDayPressed.png")));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelSkipDay.setIcon(new ImageIcon(getClass().getResource("/menuButtons/skipDay.png")));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelSkipDay.setIcon(new ImageIcon(getClass().getResource("/menuButtons/skipDayEntered.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelSkipDay.setIcon(new ImageIcon(getClass().getResource("/menuButtons/skipDay.png")));
            }
        });

        this.add(frameLayer);

        homeLayer.setVisible(true);
        managementLayer.setVisible(false);
        accountLayer.setVisible(false);

        frameLayer.add(homeLayer, JLayeredPane.DEFAULT_LAYER);
        frameLayer.add(deliveryLayer, JLayeredPane.DEFAULT_LAYER);
        frameLayer.add(managementLayer, JLayeredPane.DEFAULT_LAYER);
        frameLayer.add(accountLayer, JLayeredPane.DEFAULT_LAYER);

        frameLayer.add(labelDelivery, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelManagement, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelHelp, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelAccount, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelSkipDay, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelCurrentDate, JLayeredPane.PALETTE_LAYER);

        homeLayer.add(bgHome, JLayeredPane.DEFAULT_LAYER);

        managementLayer.add(bgManagement, JLayeredPane.DEFAULT_LAYER);
        managementLayer.add(labelAddUser, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(labelEditUser, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(labelAddPackage, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(labelEditPackage, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(labelEffectAddUser, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(labelEffectEditUser, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(labelEffectAddPackage, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(labelEffectEditPackage, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(customTable1, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(customTable2, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(searchBarAll, JLayeredPane.PALETTE_LAYER);


        accountLayer.add(bgAccount, JLayeredPane.DEFAULT_LAYER);
        accountLayer.add(labelNameSurname, JLayeredPane.PALETTE_LAYER);
        accountLayer.add(labelID, JLayeredPane.PALETTE_LAYER);
//        accountLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);
        accountLayer.add(customTable, JLayeredPane.PALETTE_LAYER);
        accountLayer.add(searchBar, JLayeredPane.PALETTE_LAYER);

        this.setLayout(null);
        this.setTitle("Geldik Yoktunuz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1116, 739);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    private static void filterTable(JTable table, String filterText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        table.setRowSorter(sorter);

        if (filterText == null || filterText.trim().isEmpty()) {
            // If filter text is empty, show all rows
            sorter.setRowFilter(null);
        } else {
            try {
                // Filter rows based on the text
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filterText));
            } catch (java.util.regex.PatternSyntaxException e) {
                JOptionPane.showMessageDialog(null, "Invalid search text.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
