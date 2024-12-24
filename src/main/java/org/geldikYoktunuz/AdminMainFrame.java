package org.geldikYoktunuz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class AdminMainFrame extends JFrame {

    JComboBox<String> comboBoxFilter;

    CustomTable customTable2;

    CustomTable customTable1;

    CustomTable customTableAccount;
    JLayeredPane homeLayer;

    JLabel labelSort;
    RoundedSearchBar searchBarID;

    private String latestFilter = "All Packages"; // Default filter

    private List<Cargo> getAllCargos() {
        return new ArrayList<>(CargoStorage.getAllCargos()); // Return all cargos
    }

    private List<Cargo> getDeliveredCargos() {
        return CargoStorage.getAllCargos().stream()
                .filter(cargo -> cargo.getCargoStatus() == CargoStatus.DELIVERED)
                .collect(Collectors.toList());
    }

    private List<Cargo> getNotDeliveredCargos() {
        return CargoStorage.getAllCargos().stream()
                .filter(cargo -> cargo.getCargoStatus() != CargoStatus.DELIVERED)
                .collect(Collectors.toList());
    }


    public AdminMainFrame() {
//        CargoStorage.setCurrentCargo(CargoStorage.getCargoById(1));
        Cargo currentCargo = CargoStorage.getCargoById(1);

        this.setName("adminFrame");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JLayeredPane frameLayer = new JLayeredPane();
        frameLayer.setBounds(0,0,1116,739);

        homeLayer = new JLayeredPane();
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

//      DELIVERY LAYER

        BackgroundImage bgDelivery = new BackgroundImage("/backgrounds/bg0.png");

        switch (currentCargo.getCargoStatus()){
            case PENDING_APPROVAL:
                bgDelivery = new BackgroundImage("/backgrounds/bg1.png");
                break;
            case IN_PROCESS:
                bgDelivery = new BackgroundImage("/backgrounds/bg2.png");
                break;
            case OUT_FOR_DELIVERY:
                bgDelivery = new BackgroundImage("/backgrounds/bg3.png");
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

        JLabel labelCancel = new JLabel();
        ImageIcon defaultIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancel.png"));
        ImageIcon enteredIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelEntered.png"));
        ImageIcon pressedIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelPressed.png"));

        labelCancel.setIcon(defaultIconRemoveUser);
        labelCancel.setBounds(668, 399, 317, 39);
        labelCancel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CargoCancel.cargoCanceling(CargoStorage.getCurrentCargo());
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


//      MANAGEMENT LAYER

        BackgroundImage bgManagement = new BackgroundImage("/backgrounds/bgManagement.png");
        bgManagement.setBounds(0, 0, 1100, 700);

        RoundedSearchBar searchBarAll = new RoundedSearchBar();
        searchBarAll.setBounds(740, 8, 300, 50);

        List<Object[]> data1List = new ArrayList<>();

        for (Customer customer : CustomerStorage.getAllCustomers()) {
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
        customTable1 = new CustomTable(data1, columnNames1);
        customTable1.setBounds(201, 296, 800, 155);

        List<Object[]> data2List = new ArrayList<>();

        for (Cargo cargo : CargoStorage.getAllCargos()) {
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

        Object[][] data2 = data2List.toArray(new Object[0][]);

        String[] columnNames2 = {"ID", "Cargo Name", "Customer", "Shipment Date", "Delivery Date", "Destination City", "Cargo Status"};

        customTable2 = new CustomTable(data2, columnNames2);
        customTable2.setBounds(201, 510, 800, 155);

        UIManager.put("ComboBox.selectionBackground", new Color(0x159a80));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        UIManager.put("ComboBox.buttonArrowColor", Color.WHITE);
        UIManager.put("ComboBox.buttonBackground", new Color(0x2c3e50));


        String[] filterOptions = {"All Packages", "Delivered Packages", "Not Delivered Packages"};
        comboBoxFilter = new JComboBox<>(filterOptions);

        comboBoxFilter.setBounds(457, 464, 171, 33);
        comboBoxFilter.setBackground(new Color(0x159a80));



        comboBoxFilter.setForeground(Color.WHITE);

        // Customize Border: Focused and Non-Focused
        Border defaultBorder = new CompoundBorder(
                new LineBorder(new Color(0x159a80), 2),
                new EmptyBorder(5, 5, 5, 5)
        );
        Border focusedBorder = new CompoundBorder(
                new LineBorder(Color.WHITE, 2),
                new EmptyBorder(5, 5, 5, 5)
        );

        comboBoxFilter.setBorder(defaultBorder);

        // Add focus listener to change border dynamically
        comboBoxFilter.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                comboBoxFilter.setBorder(focusedBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                comboBoxFilter.setBorder(defaultBorder);
            }
        });


        comboBoxFilter.addActionListener(e -> {
            String selectedFilter = (String) comboBoxFilter.getSelectedItem();
            if (selectedFilter != null) {
                latestFilter = selectedFilter;
                List<Cargo> filteredCargos = getCargosByFilter(latestFilter);
                updateCargoTable(filteredCargos);
                refreshManagementLayer(managementLayer,true);
            }
        });

        labelSort = new JLabel();
        labelSort.setIcon(new ImageIcon(getClass().getResource("/tableButtons/sort.png")));
        labelSort.setBounds(645, 464, 171, 33);

        boolean[] sortOrder = {true};

        labelSort.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<Cargo> sortedCargos;
                if (sortOrder[0]) {
                    sortedCargos = CargoStorage.getSortedUndeliveredCargosByDeliveryTime();
                } else {
                    sortedCargos = CargoStorage.getSortedUndeliveredCargosByDeliveryTimeDescending();
                }

                updateCargoTable(sortedCargos);
                sortOrder[0] = !sortOrder[0];
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelSort.setIcon(new ImageIcon(getClass().getResource("/tableButtons/sortPressed.png")));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelSort.setIcon(new ImageIcon(getClass().getResource("/tableButtons/sort.png")));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelSort.setIcon(new ImageIcon(getClass().getResource("/tableButtons/sortEntered.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelSort.setIcon(new ImageIcon(getClass().getResource("/tableButtons/sort.png")));
            }
        });

        labelSort.setVisible(false);

        searchBarID = new RoundedSearchBar(filterText -> {
            if (filterText != null && !filterText.trim().isEmpty()) {
                try {
                    int targetId = Integer.parseInt(filterText.trim());

                    Cargo foundCargo = CargoStorage.binarySearchById(targetId);

                    if (foundCargo != null) {
                        List<Cargo> searchResult = new ArrayList<>();
                        searchResult.add(foundCargo);
                        updateCargoTable(searchResult);
                    } else {
                        JOptionPane.showMessageDialog(this, "No delivered cargo found with ID: " + targetId, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                        updateCargoTable(new ArrayList<>());
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                List<Cargo> deliveredCargos = CargoStorage.getAllCargos().stream()
                        .filter(cargo -> cargo.getCargoStatus() == CargoStatus.DELIVERED)
                        .collect(Collectors.toList());
                updateCargoTable(deliveredCargos);
            }
        });
        searchBarID.setBounds(834, 464, 171, 33);

        searchBarID.setVisible(false);

        JLabel labelEffectAddUser = new JLabel();
        labelEffectAddUser.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectAddUser.setBounds(125, 22, 286, 256);
        labelEffectAddUser.setVisible(false);

        JLabel labelAddUser = new JLabel();
        labelAddUser.setName("addUser");
        labelAddUser.setIcon(new ImageIcon(getClass().getResource("/managementButtons/addUser.png")));
        labelAddUser.setBounds(193, 90, 150, 120);
        labelAddUser.addMouseListener(new ManagementMouseListener(labelAddUser, labelEffectAddUser, "/managementButtons/pressed.png", this, () -> frameRefresher(managementLayer)));

        JLabel labelEffectEditUser = new JLabel();
        labelEffectEditUser.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectEditUser.setBounds(347, 22, 286, 256);
        labelEffectEditUser.setVisible(false);

        JLabel labelEditUser = new JLabel();
        labelEditUser.setName("editUser");
        labelEditUser.setIcon(new ImageIcon(getClass().getResource("/managementButtons/editUser.png")));
        labelEditUser.setBounds(415, 90, 150, 120);
        labelEditUser.addMouseListener(new ManagementMouseListener(labelEditUser, labelEffectEditUser, "/managementButtons/pressed.png", this, () -> frameRefresher(managementLayer)));

        JLabel labelEffectAddPackage = new JLabel();
        labelEffectAddPackage.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectAddPackage.setBounds(569, 22, 286, 256);
        labelEffectAddPackage.setVisible(false);

        JLabel labelAddPackage = new JLabel();
        labelAddPackage.setName("addPackage");
        labelAddPackage.setIcon(new ImageIcon(getClass().getResource("/managementButtons/addPackage.png")));
        labelAddPackage.setBounds(637, 90, 150, 120);
        labelAddPackage.addMouseListener(new ManagementMouseListener(labelAddPackage, labelEffectAddPackage, "/managementButtons/pressed.png", this, () -> frameRefresher(managementLayer)));

        JLabel labelEffectEditPackage = new JLabel();
        labelEffectEditPackage.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectEditPackage.setBounds(791, 22, 286, 256);
        labelEffectEditPackage.setVisible(false);

        JLabel labelEditPackage = new JLabel();
        labelEditPackage.setName("editPackage");
        labelEditPackage.setIcon(new ImageIcon(getClass().getResource("/managementButtons/editPackage.png")));
        labelEditPackage.setBounds(859, 90, 150, 120);
        labelEditPackage.addMouseListener(new ManagementMouseListener(labelEditPackage, labelEffectEditPackage, "/managementButtons/pressed.png", this, () -> frameRefresher(managementLayer)));

//      ACCOUNT LAYER

        BackgroundImage bgAccount = new BackgroundImage("/backgrounds/bgAccount.png");
        bgAccount.setBounds(0, 0, 1100, 700);


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

        List<Object[]> dataList = new ArrayList<>();

        if (CustomerStorage.getCurrentCustomer() != null){
            for (Cargo cargo : CustomerStorage.getCurrentCustomer().getRecentCargosStack()) {
                dataList.add(new Object[]{
                        cargo.getPostId(),
                        cargo.getCargoName(),
                        cargo.getPostDate().format(df),
                        cargo.getDeliveryDate(),
                        cargo.getCity().getCityName(),
                        cargo.getCargoStatus().toString()
                });
            }
            System.out.println(CustomerStorage.getCurrentCustomer().getRecentCargosStack());
            System.out.println("Recent Cargos: " + CustomerStorage.getCurrentCustomer().getCustomerName());
        }
        else {
            System.out.println("Stack");
        }

        Object[][] data = dataList.toArray(new Object[0][]);

        String[] columnNames = {"ID", "Cargo Name", "Shipment Date", "Delivery Date", "Destination City", "Cargo Status"};

        customTableAccount = new CustomTable(data, columnNames);
        customTableAccount.setBounds(201, 274, 800, 380);

        JTable tableCargos = customTableAccount.getTable();

        RoundedSearchBar searchBar = new RoundedSearchBar(filterText -> filterTable(tableCargos, filterText));
        searchBar.setBounds(700, 218, 300, 50);



//      MAIN FRAME

        JLabel labelHome = new JLabel();
        labelHome.setIcon(new ImageIcon(getClass().getResource("/menuButtons/logoMenu.png")));
        labelHome.setBounds(21, 30, 60, 60);
        labelHome.addMouseListener(new MenuMouseListener(labelHome, "logoMenu", layers, homeLayer, this));

        JLabel labelDelivery = new JLabel();
        labelDelivery.setIcon(new ImageIcon(getClass().getResource("/menuButtons/delivery.png")));
        labelDelivery.setBounds(25, 135, 50, 30);
        labelDelivery.addMouseListener(new MenuMouseListener(labelDelivery, "delivery", layers, deliveryLayer, this, () -> updateDeliveryLayer(deliveryLayer, CargoStorage.getCurrentCargo())));

        JLabel labelManagement = new JLabel();
        labelManagement.setIcon(new ImageIcon(getClass().getResource("/menuButtons/management.png")));
        labelManagement.setBounds(28, 218, 46, 46);
        labelManagement.addMouseListener(new MenuMouseListener(labelManagement, "management", layers, managementLayer, this));

        JLabel labelAccount = new JLabel();
        labelAccount.setIcon(new ImageIcon(getClass().getResource("/menuButtons/account.png")));
        labelAccount.setBounds(25, 615, 50, 50);
        labelAccount.addMouseListener(new MenuMouseListener(labelAccount, "account", layers, accountLayer, this, labelNameSurname, labelID, null, customTableAccount));

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
                CargoRouting cr = new CargoRouting();
                List<Cargo> allCargos=new ArrayList<>(CargoStorage.getAllCargos());
                CargoStatusManager.statusChanger(allCargos);
                cr.routing(allCargos);
                for (Cargo c : allCargos){
                    System.out.println(c.getCargoName()+"--"+c.getCargoStatus().getDescription());
                }
                labelCurrentDate.setText(CurrentDate.getCurrentDate());
                System.out.println("Current date: " + CurrentDate.getCurrentDate());

                updateDeliveryLayer(deliveryLayer, CargoStorage.getCurrentCargo());

                refreshManagementLayer(managementLayer,true);

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
        frameLayer.add(labelAccount, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelSkipDay, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelCurrentDate, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelHome, JLayeredPane.PALETTE_LAYER);

        homeLayer.add(bgHome, JLayeredPane.DEFAULT_LAYER);

        deliveryLayer.add(bgDelivery, JLayeredPane.DEFAULT_LAYER);
        deliveryLayer.add(labelNameAndID, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCustomerName, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCustomerID, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCourierName, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryStatus, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDestinationCity, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelShipmentDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanelCourier, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCancel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDontRing, JLayeredPane.PALETTE_LAYER);


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
        managementLayer.add(comboBoxFilter, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(labelSort, JLayeredPane.PALETTE_LAYER);
        managementLayer.add(searchBarID, JLayeredPane.PALETTE_LAYER);

        accountLayer.add(bgAccount, JLayeredPane.DEFAULT_LAYER);
        accountLayer.add(labelNameSurname, JLayeredPane.PALETTE_LAYER);
        accountLayer.add(labelID, JLayeredPane.PALETTE_LAYER);
//        accountLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);
        accountLayer.add(customTableAccount, JLayeredPane.PALETTE_LAYER);
        accountLayer.add(searchBar, JLayeredPane.PALETTE_LAYER);

        ImageIcon logo = new ImageIcon(getClass().getResource("/menuButtons/logoMenu.png"));
        this.setIconImage(logo.getImage());

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
            } catch (PatternSyntaxException e) {
                JOptionPane.showMessageDialog(null, "Invalid search text.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshManagementLayer(JLayeredPane managementLayer , boolean controller) {
        // Update the customer table
        if (controller) {
            updateCustomerTable();
        }
//        homeLayer.setVisible(false);
//        managementLayer.setVisible(true);
        // Get cargos based on the latest filter
        List<Cargo> cargosToDisplay = getCargosByFilter(latestFilter);
        updateCargoTable(cargosToDisplay);

        updateCargoTableAccount();

        // Toggle visibility of searchBarID and labelSort
        switch (latestFilter) {
            case "All Packages":
                searchBarID.setVisible(false);
                labelSort.setVisible(false);
                System.out.println("Filter: All Packages - searchBarID and labelSort hidden.");
                break;
            case "Delivered Packages":
                searchBarID.setVisible(true);
                labelSort.setVisible(false);
                System.out.println("Filter: Delivered Packages - searchBarID shown, labelSort hidden.");
                break;
            case "Not Delivered Packages":
                searchBarID.setVisible(false);
                labelSort.setVisible(true);
                System.out.println("Filter: Not Delivered Packages - labelSort shown, searchBarID hidden.");
                break;
        }

        // Refresh the management layer
        managementLayer.revalidate();
        managementLayer.repaint();
    }


    private void updateCustomerTable() {
        // Prepare updated data for the customer table
        List<Object[]> data1List = new ArrayList<>();
        for (Customer customer : CustomerStorage.getAllCustomers()) {
            data1List.add(new Object[]{
                    customer.getCustomerId(),
                    customer.getCustomerName() + " " + customer.getCustomerSurname(),
                    customer.getCustomerPhoto()
            });
        }

        Object[][] data1 = data1List.toArray(new Object[0][]);
        String[] columnNames1 = {"ID", "Customer", "Photo Path"};

        // Replace the data model of the customer table
        DefaultTableModel customerTableModel = new DefaultTableModel(data1, columnNames1);
        JTable customerTable = customTable1.getTable(); // Use the correct reference for the customer table
        customerTable.setModel(customerTableModel);

        System.out.println("Customer Table updated.");

        customerTable.revalidate();
        customerTable.repaint();
    }


    private void updateCargoTable(List<Cargo> cargos) {
        if (cargos == null) {
            cargos = getAllCargos(); // Fallback to all cargos if null
        }

        // Prepare updated data for the cargo table
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Object[]> data2List = new ArrayList<>();
        for (Cargo cargo : cargos) {
            data2List.add(new Object[]{
                    cargo.getPostId(),
                    cargo.getCargoName(),
                    cargo.getCustomer().getCustomerName() + " " + cargo.getCustomer().getCustomerSurname(),
                    cargo.getPostDate().format(df),
                    cargo.getDeliveryDate() != null ? cargo.getDeliveryDate().format(df) : "N/A",
                    cargo.getCity().getCityName(),
                    cargo.getCargoStatus().getDescription()
            });
        }

        Object[][] data2 = data2List.toArray(new Object[0][]);
        String[] columnNames2 = {"ID", "Cargo Name", "Customer", "Shipment Date", "Delivery Date", "Destination City", "Cargo Status"};

        // Replace the data model of the cargo table
        DefaultTableModel cargoTableModel = new DefaultTableModel(data2, columnNames2);
        JTable cargoTable = customTable2.getTable(); // Use the correct reference for the cargo table
        cargoTable.setModel(cargoTableModel);
        cargoTable.repaint();
    }

    private void updateCargoTableAccount() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Object[]> dataList = new ArrayList<>();

        if (CustomerStorage.getCurrentCustomer() != null){
            for (Cargo cargo : CustomerStorage.getCurrentCustomer().getRecentCargosStack()) {
                dataList.add(new Object[]{
                        cargo.getPostId(),
                        cargo.getCargoName(),
                        cargo.getPostDate().format(df),
                        cargo.getDeliveryDate(),
                        cargo.getCity().getCityName(),
                        cargo.getCargoStatus().toString()
                });
            }
            System.out.println(CustomerStorage.getCurrentCustomer().getRecentCargosStack());
            System.out.println("Recent Cargos: " + CustomerStorage.getCurrentCustomer().getCustomerName());
        }
        else {
            System.out.println("Stack");
        }

        Object[][] data = dataList.toArray(new Object[0][]);

        String[] columnNames = {"ID", "Cargo Name", "Shipment Date", "Delivery Date", "Destination City", "Cargo Status"};

        DefaultTableModel cargoTableModel = new DefaultTableModel(data, columnNames);
        JTable cargoTable = customTableAccount.getTable(); // Use the correct reference for the cargo table
        cargoTable.setModel(cargoTableModel);
        cargoTable.repaint();
    }


    private List<Cargo> getCargosByFilter(String filter) {
        switch (filter) {
            case "Delivered Packages":
                return getDeliveredCargos();
            case "Not Delivered Packages":
                return getNotDeliveredCargos();
            case "All Packages":
            default:
                return getAllCargos();
        }
    }
    private void frameRefresher(JLayeredPane managementLayer) {
        refreshManagementLayer(managementLayer,false);
        this.dispose();
        AdminMainFrame adminMainFrame = new AdminMainFrame();
    }

    private void updateDeliveryLayer(JLayeredPane deliveryLayer, Cargo currentCargo) {
        // Remove all existing components from the layer
        deliveryLayer.removeAll();

        // Set the updated background based on cargo status
        BackgroundImage bgDelivery;
        switch (currentCargo.getCargoStatus()){
            case PENDING_APPROVAL:
                bgDelivery = new BackgroundImage("/backgrounds/bg1.png");
                break;
            case IN_PROCESS:
                bgDelivery = new BackgroundImage("/backgrounds/bg2.png");
                break;
            case OUT_FOR_DELIVERY:
                bgDelivery = new BackgroundImage("/backgrounds/bg3.png");
                break;
            case DELIVERED:
                bgDelivery = new BackgroundImage("/backgrounds/bg4.png");
                break;
            default:
                bgDelivery = new BackgroundImage("/backgrounds/bg0.png");
        }
        bgDelivery.setBounds(0, 0, 1100, 700);
        deliveryLayer.add(bgDelivery, JLayeredPane.DEFAULT_LAYER);

        // Update cargo information
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JLabel labelNameAndID = createLabel(
                currentCargo.getCargoName() + " : #" + currentCargo.getPostId(),
                new Rectangle(100, 74, 1000, 60),
                new Color(0xf7f7f7),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.BOLD, 20) // Placeholder font
        );

        JLabel labelCustomerName = createLabel(
                currentCargo.getCustomer().getCustomerName() + " " + currentCargo.getCustomer().getCustomerSurname(),
                new Rectangle(325, 575, 300, 40),
                new Color(0x34495e),
                SwingConstants.LEFT,
                new Font("SansSerif", Font.BOLD, 18) // Placeholder font
        );

        JLabel labelDeliveryStatus = createLabel(
                currentCargo.getCargoStatus().getDescription(),
                new Rectangle(144, 172, 181, 30),
                new Color(0x95A5A6),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.PLAIN, 16) // Placeholder font
        );

        JLabel labelDestinationCity = createLabel(
                currentCargo.getCity().toString(),
                new Rectangle(391, 172, 188, 30),
                new Color(0x95A5A6),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.PLAIN, 16) // Placeholder font
        );

        JLabel labelShipmentDate = createLabel(
                currentCargo.getPostDate().format(df),
                new Rectangle(645, 172, 167, 30),
                new Color(0x95A5A6),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.PLAIN, 16) // Placeholder font
        );

        JLabel labelDeliveryDate = createLabel(
                currentCargo.getDeliveryDate() != null ? currentCargo.getDeliveryDate().format(df) : "-",
                new Rectangle(896, 172, 158, 30),
                new Color(0x95A5A6),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.PLAIN, 16) // Placeholder font
        );

        boolean dontRing = false;

        JLabel labelCancel = new JLabel();
        ImageIcon defaultIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancel.png"));
        ImageIcon enteredIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelEntered.png"));
        ImageIcon pressedIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelPressed.png"));

        labelCancel.setIcon(defaultIconRemoveUser);
        labelCancel.setBounds(668, 399, 317, 39);
        labelCancel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CargoCancel.cargoCanceling(CargoStorage.getCurrentCargo());
                updateDeliveryLayer(deliveryLayer, CargoStorage.getCurrentCargo());
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

                currentCargo.setDontRing(dontRingWrapper[0]);
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

        CircularImagePanel circularImagePanel = new CircularImagePanel(currentCargo.getCustomer().getCustomerPhoto(), 150);
        circularImagePanel.setBounds(150, 526, 150, 150);

        CircularImagePanel circularImagePanelCourier = new CircularImagePanel(currentCargo.getCourierPhoto(), 150);
        circularImagePanelCourier.setBounds(641, 526, 150, 150);

        // Add components to delivery layer
        deliveryLayer.add(labelNameAndID, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCustomerName, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryStatus, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDestinationCity, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelShipmentDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanelCourier, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCancel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDontRing, JLayeredPane.PALETTE_LAYER);

        // Revalidate and repaint the delivery layer
        deliveryLayer.revalidate();
        deliveryLayer.repaint();
    }

    private JLabel createLabel(String text, Rectangle bounds, Color color, int alignment, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(bounds);
        label.setForeground(color);
        label.setHorizontalAlignment(alignment);
        label.setFont(font);
        return label;
    }

}
