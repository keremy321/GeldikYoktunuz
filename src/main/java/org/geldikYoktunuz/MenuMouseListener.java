package org.geldikYoktunuz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MenuMouseListener implements MouseListener {

    private ImageIcon enteredIcon;
    private ImageIcon pressedIcon;
    private ImageIcon originalIcon;
    private JLabel label;
    private String key;
    private JLayeredPane[] layers;
    private JLayeredPane nextLayer;
    private JFrame currentFrame;
    JComboBox<Customer> comboBox;
    JComboBox<Cargo> comboBoxPackage;
    JComboBox<Cargo> comboBoxPackageCustomer;

    private JLabel labelNameSurname;
    private JLabel labelNameID;
    private CircularImagePanel circularImagePanel;
    private CustomTable customTableAccount;
    private Runnable refreshCallBack;

    public MenuMouseListener(JLabel label, String key, JLayeredPane[] layers, JLayeredPane nextLayer, JFrame currentFrame, JLabel labelNameSurname, JLabel labelNameID, CircularImagePanel circularImagePanel, CustomTable customTableAccount) {
        this.circularImagePanel = circularImagePanel;
        this.labelNameID = labelNameID;
        this.currentFrame = currentFrame;
        this.label = label;
        this.key = key;
        this.layers = layers;
        this.nextLayer = nextLayer;
        this.labelNameSurname = labelNameSurname;
        this.customTableAccount = customTableAccount;
        this.originalIcon = (ImageIcon) label.getIcon();

        this.enteredIcon = loadImageIcon("/menuButtons/" + key + "Entered.png");
        this.pressedIcon = loadImageIcon("/menuButtons/" + key + "Pressed.png");
    }


    public MenuMouseListener(JLabel label, String key, JLayeredPane[] layers , JLayeredPane nextLayer, JFrame currentFrame) {
        this.currentFrame = currentFrame;
        this.label = label;
        this.key = key;
        this.layers = layers;
        this.nextLayer = nextLayer;
        this.originalIcon = (ImageIcon) label.getIcon();

        this.enteredIcon = loadImageIcon("/menuButtons/" + key + "Entered.png");
        this.pressedIcon = loadImageIcon("/menuButtons/" + key + "Pressed.png");
    }

    public MenuMouseListener(JLabel label, String key, JLayeredPane[] layers , JLayeredPane nextLayer, JFrame currentFrame, Runnable refreshCallBack) {
        this.currentFrame = currentFrame;
        this.label = label;
        this.key = key;
        this.layers = layers;
        this.nextLayer = nextLayer;
        this.originalIcon = (ImageIcon) label.getIcon();
        this.refreshCallBack = refreshCallBack;

        this.enteredIcon = loadImageIcon("/menuButtons/" + key + "Entered.png");
        this.pressedIcon = loadImageIcon("/menuButtons/" + key + "Pressed.png");
    }


    private ImageIcon loadImageIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private JLayeredPane getCurrentLayer() {
        JLayeredPane currentLayer = null;

        for (int i = 0; i < layers.length; i++) {
            if (layers[i].isVisible()) {
                currentLayer = layers[i];
                break;
            }
        }

        return currentLayer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (JLayeredPane layer : layers) {
            layer.setVisible(false);
        }

        // Make the target layer visible
        nextLayer.setVisible(true);

        // Force the UI to update immediately
        nextLayer.revalidate();
        nextLayer.repaint();


        if ("customerFrame".equals(currentFrame.getName()) && "account".equals(nextLayer.getName())) {
            Customer currentCustomer = CustomerStorage.getCurrentCustomer();

            getCurrentLayer().setVisible(false);
            nextLayer.setVisible(true);

            if (currentCustomer != null) {
                System.out.println("Updating UI for: " + currentCustomer.getCustomerName());

                // Update Name Surname
                if (labelNameSurname != null) {
                    labelNameSurname.setText(currentCustomer.getCustomerName() + " " + currentCustomer.getCustomerSurname());
                    labelNameSurname.revalidate();
                    labelNameSurname.repaint();
                } else {
                    System.err.println("labelNameSurname is null!");
                }

                // Update Customer ID
                if (labelNameID != null) {
                    labelNameID.setText("#" + currentCustomer.getCustomerId());
                    labelNameID.revalidate();
                    labelNameID.repaint();
                }

                // Update Circular Image
                if (circularImagePanel != null) {
                    nextLayer.remove(circularImagePanel);
                }
                circularImagePanel = new CircularImagePanel(currentCustomer.getCustomerPhoto(), 150);
                circularImagePanel.setBounds(201, 53, 150, 150);
                nextLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);

                nextLayer.revalidate();
                nextLayer.repaint();
            } else {
                System.err.println("Current customer is null!");
            }
        }

        else if ("adminFrame".equals(currentFrame.getName()) && "delivery".equals(nextLayer.getName())) {
            choosePackage();
        }
        else if ("adminFrame".equals(currentFrame.getName()) && "account".equals(nextLayer.getName())){
            chooseAccount();
        }

        else if ("customerFrame".equals(currentFrame.getName()) && "delivery".equals(nextLayer.getName())) {
            choosePackageCustomer();
        }

        else {
            getCurrentLayer().setVisible(false);
            nextLayer.setVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        label.setIcon(pressedIcon);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        label.setIcon(originalIcon);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        label.setIcon(enteredIcon);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        label.setIcon(originalIcon);
    }


    public void chooseAccount() {
        JDialog dialogChooseAccount = new JDialog(currentFrame, "Choose Account", true);

        JLayeredPane chooseUserLayer = new JLayeredPane();
        chooseUserLayer.setBounds(0, 0, 816, 289);

        BackgroundImage byChooseAccount = new BackgroundImage("/backgrounds/bgChooseAccount.png");
        byChooseAccount.setBounds(0, 0, 800, 250);

        JLabel labelChoose = new JLabel();
        ImageIcon defaultIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/choose.png"));
        ImageIcon enteredIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/chooseEntered.png"));
        ImageIcon pressedIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/choosePressed.png"));

        labelChoose.setIcon(defaultIconChoose);
        labelChoose.setBounds(591, 194, 167, 38);
        labelChoose.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Customer selectedCustomer = (Customer) comboBox.getSelectedItem();
                if (selectedCustomer != null) {
                    CustomerStorage.setCurrentCustomer(selectedCustomer);
                    System.out.println("Current Customer: " + selectedCustomer.getCustomerName());

                    // Update labels
                    if (labelNameSurname != null) {
                        labelNameSurname.setText(selectedCustomer.getCustomerName() + " " + selectedCustomer.getCustomerSurname());
                        labelNameSurname.revalidate();
                        labelNameSurname.repaint();
                    }

                    if (labelNameID != null) {
                        labelNameID.setText("#" + selectedCustomer.getCustomerId());
                        labelNameID.revalidate();
                        labelNameID.repaint();
                    }

                    // Update circular image panel
                    if (circularImagePanel != null) {
                        nextLayer.remove(circularImagePanel);
                    }
                    circularImagePanel = new CircularImagePanel(selectedCustomer.getCustomerPhoto(), 150);
                    circularImagePanel.setBounds(201, 53, 150, 150);
                    nextLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);

                    // Update table data
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    List<Object[]> dataList = new ArrayList<>();

                    for (Cargo cargo : selectedCustomer.getRecentCargosStack()) {
                        dataList.add(new Object[]{
                                cargo.getPostId(),
                                cargo.getCargoName(),
                                cargo.getPostDate().format(df),
                                cargo.getDeliveryDate() != null ? cargo.getDeliveryDate().format(df) : "N/A",
                                cargo.getCity().getCityName(),
                                cargo.getCargoStatus().toString()
                        });
                    }

                    String[] columnNames = {"ID", "Cargo Name", "Shipment Date", "Delivery Date", "Destination City", "Cargo Status"};
                    Object[][] data = dataList.toArray(new Object[0][]);

                    JTable tableCargos = customTableAccount.getTable();
                    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                    tableCargos.setModel(tableModel);

                    // Revalidate and repaint table and parent container
                    tableCargos.revalidate();
                    tableCargos.repaint();
                    nextLayer.revalidate();
                    nextLayer.repaint();
                }
                dialogChooseAccount.dispose();
                getCurrentLayer().setVisible(false);
                nextLayer.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelChoose.setIcon(pressedIconChoose);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelChoose.setIcon(defaultIconChoose);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelChoose.setIcon(enteredIconChoose);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelChoose.setIcon(defaultIconChoose);
            }
        });

        UIManager.put("ComboBox.arc", 15); // Set the corner radius to 15


        UIManager.put("ComboBox.selectionBackground", new Color(0x159a80));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        UIManager.put("ComboBox.buttonArrowColor", Color.WHITE); // Set arrow color to black
        UIManager.put("ComboBox.buttonBackground", new Color(0x2c3e50)); // Optional: Background color new Color(0x323232)


        comboBox = new JComboBox<>();
        comboBox.setBounds(117, 108, 567, 54);
        comboBox.setBackground(new Color(0x159a80));

        for (Customer customer : CustomerStorage.getAllCustomers()) {
            comboBox.addItem(customer);
        }

        comboBox.setForeground(Color.WHITE);

        // Customize Border: Focused and Non-Focused
        Border defaultBorder = new CompoundBorder(
                new LineBorder(new Color(0x159a80), 2), // Default border color
                new EmptyBorder(5, 5, 5, 5)
        );
        Border focusedBorder = new CompoundBorder(
                new LineBorder(Color.WHITE, 2), // White border when focused
                new EmptyBorder(5, 5, 5, 5)
        );

        comboBox.setBorder(defaultBorder);

        // Add focus listener to change border dynamically
        comboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                comboBox.setBorder(focusedBorder);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                comboBox.setBorder(defaultBorder);
            }
        });

        dialogChooseAccount.setSize(816, 289);
        dialogChooseAccount.setLocationRelativeTo(null);
        dialogChooseAccount.setResizable(false);
        dialogChooseAccount.setLayout(null);

        chooseUserLayer.add(byChooseAccount, JLayeredPane.DEFAULT_LAYER);
        chooseUserLayer.add(labelChoose, JLayeredPane.PALETTE_LAYER);
        chooseUserLayer.add(comboBox, JLayeredPane.PALETTE_LAYER);

        dialogChooseAccount.add(chooseUserLayer);
        dialogChooseAccount.setVisible(true);
    }

    public void choosePackage() {
        JDialog dialogChoosePackage = new JDialog(currentFrame, "Choose Package", true);

        JLayeredPane choosePackage = new JLayeredPane();
        choosePackage.setBounds(0, 0, 816, 289);

        BackgroundImage byChoosePackage = new BackgroundImage("/backgrounds/bgChoosePackage.png");
        byChoosePackage.setBounds(0, 0, 800, 250);

        JLabel labelChoose = new JLabel();
        ImageIcon defaultIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/choose.png"));
        ImageIcon enteredIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/chooseEntered.png"));
        ImageIcon pressedIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/choosePressed.png"));

        labelChoose.setIcon(defaultIconChoose);
        labelChoose.setBounds(591, 194, 167, 38);
        labelChoose.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Cargo selectedCargo = (Cargo) comboBoxPackage.getSelectedItem();
                if (selectedCargo != null) {
                    CargoStorage.setCurrentCargo(selectedCargo);
                    System.out.println("Current Cargo: " + selectedCargo.getCargoName());
                }


                dialogChoosePackage.dispose();
                refreshCallBack.run();

                if (CargoStorage.getCurrentCargo() == null){
                    JOptionPane.showMessageDialog(currentFrame, "Please select a cargo first!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else if (CargoStorage.getCurrentCargo().getCargoRoute() == null) {
                    JOptionPane.showMessageDialog(currentFrame, "No cargo route found. Please skip day!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    GraphFrame graphFrame = new GraphFrame();
                    graphFrame.setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelChoose.setIcon(pressedIconChoose);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelChoose.setIcon(defaultIconChoose);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelChoose.setIcon(enteredIconChoose);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelChoose.setIcon(defaultIconChoose);
            }
        });

        UIManager.put("ComboBox.selectionBackground", new Color(0x159a80));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        UIManager.put("ComboBox.buttonArrowColor", Color.WHITE); // Set arrow color to black
        UIManager.put("ComboBox.buttonBackground", new Color(0x2c3e50)); // Optional: Background color new Color(0x323232)


        comboBoxPackage = new JComboBox<>();
        comboBoxPackage.setBounds(117, 108, 567, 54);
        comboBoxPackage.setBackground(new Color(0x159a80));

        for (Cargo cargo : CargoStorage.getAllCargos()) {
            comboBoxPackage.addItem(cargo);
        }

        comboBoxPackage.setForeground(Color.WHITE);

        Border defaultBorder = new CompoundBorder(
                new LineBorder(new Color(0x159a80), 2),
                new EmptyBorder(5, 5, 5, 5)
        );
        Border focusedBorder = new CompoundBorder(
                new LineBorder(Color.WHITE, 2),
                new EmptyBorder(5, 5, 5, 5)
        );

        comboBoxPackage.setBorder(defaultBorder);

        // Add focus listener to change border dynamically
        comboBoxPackage.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                comboBoxPackage.setBorder(focusedBorder);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                comboBoxPackage.setBorder(defaultBorder);
            }
        });

        dialogChoosePackage.setSize(816, 289);
        dialogChoosePackage.setLocationRelativeTo(null);
        dialogChoosePackage.setResizable(false);
        dialogChoosePackage.setLayout(null);

        choosePackage.add(byChoosePackage, JLayeredPane.DEFAULT_LAYER);
        choosePackage.add(labelChoose, JLayeredPane.PALETTE_LAYER);
        choosePackage.add(comboBoxPackage, JLayeredPane.PALETTE_LAYER);

        dialogChoosePackage.add(choosePackage);
        dialogChoosePackage.setVisible(true);
    }

    public void choosePackageCustomer() {
        JDialog dialogChoosePackage = new JDialog(currentFrame, "Choose Package", true);

        JLayeredPane choosePackage = new JLayeredPane();
        choosePackage.setBounds(0, 0, 816, 289);

        BackgroundImage byChoosePackage = new BackgroundImage("/backgrounds/bgChoosePackage.png");
        byChoosePackage.setBounds(0, 0, 800, 250);

        JLabel labelChoose = new JLabel();
        ImageIcon defaultIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/choose.png"));
        ImageIcon enteredIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/chooseEntered.png"));
        ImageIcon pressedIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/choosePressed.png"));

        labelChoose.setIcon(defaultIconChoose);
        labelChoose.setBounds(591, 194, 167, 38);
        labelChoose.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Cargo selectedCargo = (Cargo) comboBoxPackageCustomer.getSelectedItem();
                if (selectedCargo != null) {
                    CargoStorage.setCurrentCargo(selectedCargo);
                    System.out.println("Current Cargo: " + selectedCargo.getCargoName());
                }

                dialogChoosePackage.dispose();

                refreshCallBack.run();

                getCurrentLayer().setVisible(false);
                nextLayer.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelChoose.setIcon(pressedIconChoose);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelChoose.setIcon(defaultIconChoose);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelChoose.setIcon(enteredIconChoose);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelChoose.setIcon(defaultIconChoose);
            }
        });

        UIManager.put("ComboBox.selectionBackground", new Color(0x159a80));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        UIManager.put("ComboBox.buttonArrowColor", Color.WHITE); // Set arrow color to black
        UIManager.put("ComboBox.buttonBackground", new Color(0x2c3e50)); // Optional: Background color new Color(0x323232)


        comboBoxPackageCustomer = new JComboBox<>();
        comboBoxPackageCustomer.setBounds(117, 108, 567, 54);
        comboBoxPackageCustomer.setBackground(new Color(0x159a80));

        for (Cargo cargo : CustomerStorage.getCurrentCustomer().getRecentCargosStack()) {
            comboBoxPackageCustomer.addItem(cargo);
        }

        comboBoxPackageCustomer.setForeground(Color.WHITE);

        Border defaultBorder = new CompoundBorder(
                new LineBorder(new Color(0x159a80), 2),
                new EmptyBorder(5, 5, 5, 5)
        );
        Border focusedBorder = new CompoundBorder(
                new LineBorder(Color.WHITE, 2),
                new EmptyBorder(5, 5, 5, 5)
        );

        comboBoxPackageCustomer.setBorder(defaultBorder);

        // Add focus listener to change border dynamically
        comboBoxPackageCustomer.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                comboBoxPackageCustomer.setBorder(focusedBorder);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                comboBoxPackageCustomer.setBorder(defaultBorder);
            }
        });

        dialogChoosePackage.setSize(816, 289);
        dialogChoosePackage.setLocationRelativeTo(null);
        dialogChoosePackage.setResizable(false);
        dialogChoosePackage.setLayout(null);

        choosePackage.add(byChoosePackage, JLayeredPane.DEFAULT_LAYER);
        choosePackage.add(labelChoose, JLayeredPane.PALETTE_LAYER);
        choosePackage.add(comboBoxPackageCustomer, JLayeredPane.PALETTE_LAYER);

        dialogChoosePackage.add(choosePackage);
        dialogChoosePackage.setVisible(true);
    }

}
