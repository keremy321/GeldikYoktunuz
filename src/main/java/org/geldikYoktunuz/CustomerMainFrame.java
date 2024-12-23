package org.geldikYoktunuz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CustomerMainFrame extends JFrame {



    public CustomerMainFrame() {
        this.setName("customerFrame");

        Customer currentCustomer = CustomerStorage.getCustomerById(1);
        Cargo currentCargo = CargoStorage.getCargoById(1);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (CustomerStorage.getCurrentCustomer() != null){
            currentCustomer = CustomerStorage.getCurrentCustomer();
        }
        else {
            System.out.println("Current Customer: null");
        }

        if (CargoManager.getInstance().getCurrentCargo() != null){
            currentCargo = CargoStorage.getCurrentCargo();
        }
        else {
            System.out.println("Current Cargo: null");
        }

        JLayeredPane frameLayer = new JLayeredPane();
        frameLayer.setBounds(0, 0, 1116, 739);

        JLayeredPane homeLayer = new JLayeredPane();
        homeLayer.setName("home");
        homeLayer.setBounds(0, 0, 1116, 739);

        JLayeredPane deliveryLayer = new JLayeredPane();
        deliveryLayer.setName("delivery");
        deliveryLayer.setBounds(0, 0, 1116, 739);

        JLayeredPane accountLayer = new JLayeredPane();
        accountLayer.setName("account");
        accountLayer.setBounds(0, 0, 1116, 739);

        JLayeredPane[] layers = {homeLayer, deliveryLayer, accountLayer};

//      HOME LAYER

        BackgroundImage bgHome = new BackgroundImage("/backgrounds/bgHome.png");
        bgHome.setBounds(0, 0, 1100, 700);

//      DELIVERY LAYER
        BackgroundImage bgDelivery = new BackgroundImage("/backgrounds/bg0.png");

        switch (currentCargo.getCargoStatus()){
            case PENDING_APPROVAL:
                bgDelivery = new BackgroundImage("/backgrounds/bg1.png");
                break;
            case IN_PROCESS:
                bgDelivery = new BackgroundImage("/backgrounds/bg3.png");
                break;
            case OUT_FOR_DELIVERY:
                bgDelivery = new BackgroundImage("/backgrounds/bg2.png");
                break;
            case DELIVERED:
                bgDelivery = new BackgroundImage("/backgrounds/bg4.png");
                break;
            default:
                bgDelivery = new BackgroundImage("/backgrounds/bg0.png");
        }

        bgDelivery.setBounds(0, 0, 1100, 700);

        JLabel labelNameAndID = new JLabel();
        labelNameAndID.setText(currentCargo.getCargoName() + " : #" + currentCargo.getPostId());
        labelNameAndID.setBounds(100, 74, 1000, 60);
        labelNameAndID.setForeground(new Color(0xf7f7f7));
        labelNameAndID.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelCustomerName = new JLabel();
        labelCustomerName.setText(currentCargo.getCustomer().getCustomerName() + " " + currentCargo.getCustomer().getCustomerSurname());
        labelCustomerName.setBounds(325, 575, 300, 40);
        labelCustomerName.setForeground(new Color(0x34495e));

        JLabel labelCustomerID = new JLabel();
        labelCustomerID.setText("#" + currentCargo.getCustomer().getCustomerId());
        labelCustomerID.setBounds(325, 605, 300, 40);
        labelCustomerID.setForeground(new Color(0x34495e));

        JLabel labelCourierName = new JLabel();
        labelCourierName.setText(currentCargo.getCourierName());
        labelCourierName.setBounds(817, 575, 300, 40);
        labelCourierName.setForeground(new Color(0x34495e));

        JLabel labelDeliveryStatus = new JLabel();
        labelDeliveryStatus.setText(currentCargo.getCargoStatus().getDescription());
        labelDeliveryStatus.setBounds(144, 172, 181, 30);
        labelDeliveryStatus.setForeground(new Color(0x95A5A6));
        labelDeliveryStatus.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelDestinationCity = new JLabel();
        labelDestinationCity.setText(currentCargo.getCity().toString());
        labelDestinationCity.setBounds(391, 172, 188, 30);
        labelDestinationCity.setForeground(new Color(0x95A5A6));
        labelDestinationCity.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelShipmentDate = new JLabel();
        labelShipmentDate.setText(currentCargo.getPostDate().format(df));
        labelShipmentDate.setBounds(645, 172, 167, 30);
        labelShipmentDate.setForeground(new Color(0x95A5A6));
        labelShipmentDate.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelDeliveryDate = new JLabel();
        labelDeliveryDate.setText(
                currentCargo.getDeliveryDate() != null
                        ? currentCargo.getDeliveryDate().format(df)
                        : "-"
        );        labelDeliveryDate.setBounds(896, 172, 158, 30);
        labelDeliveryDate.setForeground(new Color(0x95A5A6));
        labelDeliveryDate.setHorizontalAlignment(SwingConstants.CENTER);

        CircularImagePanel circularImagePanel = new CircularImagePanel(currentCargo.getCustomer().getCustomerPhoto(), 150);
        circularImagePanel.setBounds(150, 526, 150, 150);

        CircularImagePanel circularImagePanelCourier = new CircularImagePanel(currentCargo.getCourierPhoto(), 150);
        circularImagePanelCourier.setBounds(641, 526, 150, 150);

        try {
            Font montserratBoldFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf")).deriveFont(36f);
            Font montserratBoldSmallFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf")).deriveFont(27f);
            Font montserratLightFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Light.ttf")).deriveFont(27f);
            Font montserratLightSmallFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Regular.ttf")).deriveFont(20f);
            labelNameAndID.setFont(montserratBoldFont);
            labelCustomerName.setFont(montserratBoldSmallFont);
            labelCourierName.setFont(montserratBoldSmallFont);
            labelCustomerID.setFont(montserratLightFont);
            labelDeliveryStatus.setFont(montserratLightSmallFont);
            labelDestinationCity.setFont(montserratLightSmallFont);
            labelShipmentDate.setFont(montserratLightSmallFont);
            labelDeliveryDate.setFont(montserratLightSmallFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            labelNameAndID.setFont(new Font("SansSerif", Font.PLAIN, 12));
        }

        boolean dontRing = false;
        boolean isCancelled;

        JLabel labelCancel = new JLabel();
        ImageIcon defaultIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancel.png"));
        ImageIcon enteredIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelEntered.png"));
        ImageIcon pressedIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelPressed.png"));

        labelCancel.setIcon(defaultIconRemoveUser);
        labelCancel.setBounds(668, 399, 317, 39);
        labelCancel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelCancel.setIcon(pressedIconRemoveUser);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelCancel.setIcon(defaultIconRemoveUser);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelCancel.setIcon(enteredIconRemoveUser);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelCancel.setIcon(defaultIconRemoveUser);
            }
        });

        JLabel labelDontRing = new JLabel();
        ImageIcon defaultIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRing.png"));
        ImageIcon enteredIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRingEntered.png"));
        ImageIcon pressedIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRingClicked.png"));



        labelDontRing.setIcon(defaultIconDontRing);
        labelDontRing.setBounds(223, 399, 317, 39);

        final boolean[] dontRingWrapper = {false};

        labelDontRing.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dontRingWrapper[0] = !dontRingWrapper[0];

                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelDontRing.setIcon(enteredIconDontRing);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelDontRing.setIcon(enteredIconDontRing);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }
        });

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
        String[] columnNames = {"ID", "Cargo Name", "Gönderi Tarihi", "Teslim Tarihi", "Kargo Durumu"};


        // Create the custom table
        CustomTable customTable = new CustomTable(data, columnNames);
        customTable.setBounds(201, 274, 800, 380);

        JTable table = customTable.getTable();



        RoundedSearchBar searchBar = new RoundedSearchBar(filterText -> filterTable(table, filterText));
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
        labelDelivery.addMouseListener(new MenuMouseListener(labelDelivery, "delivery", layers, deliveryLayer, this));

        JLabel labelHelp = new JLabel();
        labelHelp.setIcon(new ImageIcon(getClass().getResource("/menuButtons/help.png")));
        labelHelp.setBounds(25, 525, 50, 50);
        labelHelp.addMouseListener(new MenuMouseListener(labelHelp, "help", layers, homeLayer, this));

        JLabel labelAccount = new JLabel();
        labelAccount.setIcon(new ImageIcon(getClass().getResource("/menuButtons/account.png")));
        labelAccount.setBounds(25, 615, 50, 50);
        labelAccount.addMouseListener(new MenuMouseListener(labelAccount, "account", layers, accountLayer, this));

        JLabel labelCurrentDate = new JLabel();
        labelCurrentDate.setText(CurrentDate.getCurrentDate());
        labelCurrentDate.setBounds(0, 430, 100, 26);
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
        accountLayer.setVisible(false);

        frameLayer.add(homeLayer, JLayeredPane.DEFAULT_LAYER);
        frameLayer.add(deliveryLayer, JLayeredPane.DEFAULT_LAYER);
        frameLayer.add(accountLayer, JLayeredPane.DEFAULT_LAYER);

        frameLayer.add(labelDelivery, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelHelp, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelAccount, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelSkipDay, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelCurrentDate, JLayeredPane.PALETTE_LAYER);

        homeLayer.add(bgHome, JLayeredPane.DEFAULT_LAYER);

        deliveryLayer.add(bgDelivery, JLayeredPane.DEFAULT_LAYER);
        deliveryLayer.add(labelNameAndID, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCustomerName, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCourierName, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCustomerID, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryStatus, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDestinationCity, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelShipmentDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanelCourier, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCancel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDontRing, JLayeredPane.PALETTE_LAYER);

        accountLayer.add(bgAccount, JLayeredPane.DEFAULT_LAYER);
        accountLayer.add(labelNameSurname, JLayeredPane.PALETTE_LAYER);
        accountLayer.add(labelID, JLayeredPane.PALETTE_LAYER);
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
