package org.geldikYoktunuz;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import raven.datetime.component.date.DateSelectionAble;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManagementMouseListener implements MouseListener {
    private String path;
    private JLabel label;
    private JLabel effect;
    private ImageIcon enteredIcon;
    private ImageIcon pressedIcon;
    private JFrame currentFrame;
    private static boolean dontRing = false;
    private LocalDate selectedDate;
    private JComboBox<Customer> comboBox;
    private JComboBox<Cargo> comboBoxPackage;
    private JComboBox<City> comboBoxCity;
    private JComboBox<Customer> comboBoxCustomer;

    private JTextField textFieldName;
    private JTextField textFieldSurname;
    private JTextField textFieldCourierName;
    private JTextField textFieldDeliveryName;
    private JLabel labelIDDelivery;
    private JLabel labelID;
    private Runnable refreshCallback;


    public ManagementMouseListener(JLabel label, JLabel effect, String path, JFrame currentFrame) {
        this.currentFrame = currentFrame;
        this.label = label;
        this.effect = effect;
        this.path = path;

        this.enteredIcon = (ImageIcon) effect.getIcon();

        try {
            URL effectURL = getClass().getResource(path);
            if (effectURL != null) {
                pressedIcon = new ImageIcon(effectURL);
            } else {
                System.err.println("Pressed icon not found: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlatMacDarkLaf.setup();
    }



    public ManagementMouseListener(JLabel label, JLabel effect, String path, JFrame currentFrame, Runnable refreshCallback) {
        this.currentFrame = currentFrame;
        this.label = label;
        this.effect = effect;
        this.path = path;
        this.refreshCallback = refreshCallback;

        this.enteredIcon = (ImageIcon) effect.getIcon();

        try {
            URL effectURL = getClass().getResource(path);
            if (effectURL != null) {
                pressedIcon = new ImageIcon(effectURL);
            } else {
                System.err.println("Pressed icon not found: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlatMacDarkLaf.setup();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        effect.setVisible(false);

        if ("addUser".equals(label.getName())) {
            addUser();
            System.out.println("addUser");
        } else if ("editUser".equals(label.getName())) {
            chooseAccount();
            System.out.println("editUser");
        } else if ("addPackage".equals(label.getName())) {
            addPackage();
            System.out.println("addPackage");
        } else if ("editPackage".equals(label.getName())) {
            choosePackage();
            System.out.println("editPackage");
        }

        if (refreshCallback != null) {
            refreshCallback.run();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        effect.setVisible(true);
        effect.setIcon(pressedIcon);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        effect.setVisible(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        effect.setVisible(true);
        effect.setIcon(enteredIcon);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        effect.setVisible(false);
    }

    private void addUser() {
//      ADD USER ---------------------------------------------------------------

        JDialog dialogAddUser = new JDialog(currentFrame, "Add User", true);

        JLayeredPane addUserLayer = new JLayeredPane();
        addUserLayer.setBounds(0, 0, 816, 539);

        BackgroundImage bgAddUser = new BackgroundImage("/backgrounds/bgAddUser.png");
        bgAddUser.setBounds(0, 0, 800, 500);

        JLabel labelAddUser = new JLabel();
        ImageIcon defaultIconAddUser = new ImageIcon(getClass().getResource("/dialogButtons/create.png"));
        ImageIcon enteredIconAddUser = new ImageIcon(getClass().getResource("/dialogButtons/createEntered.png"));
        ImageIcon pressedIconAddUser = new ImageIcon(getClass().getResource("/dialogButtons/createPressed.png"));

        labelAddUser.setIcon(defaultIconAddUser);
        labelAddUser.setBounds(591, 449, 167, 38);
        labelAddUser.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textFieldName.getText().isEmpty() || textFieldSurname.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(addUserLayer,
                            "Error: Please ensure all fields are filled in:\n" +
                                    "- Name\n" +
                                    "- Surname",
                            "Missing Information",
                            JOptionPane.WARNING_MESSAGE);
                }
                else{
                    Customer customer = new Customer(textFieldName.getText(), textFieldSurname.getText(), PPMouseListener.getPpPath());
                    System.out.println("Customer: " + customer);
                    dialogAddUser.dispose();
                }


            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelAddUser.setIcon(pressedIconAddUser);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelAddUser.setIcon(defaultIconAddUser);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelAddUser.setIcon(enteredIconAddUser);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelAddUser.setIcon(defaultIconAddUser);
            }
        });

        ppLabels(addUserLayer);
        userAdditions(addUserLayer);

        dialogAddUser.setSize(816, 539);
        dialogAddUser.setLocationRelativeTo(null);
        dialogAddUser.setResizable(false);
        dialogAddUser.setLayout(null);

        addUserLayer.add(bgAddUser, JLayeredPane.DEFAULT_LAYER);
        addUserLayer.add(labelAddUser, JLayeredPane.PALETTE_LAYER);

        dialogAddUser.add(addUserLayer);
        dialogAddUser.setVisible(true);
    }

    private void editUser() {
//      EDIT USER ---------------------------------------------------------------

        JDialog dialogEditUser = new JDialog(currentFrame, "Edit User", true);

        JLayeredPane editUserLayer = new JLayeredPane();
        editUserLayer.setBounds(0, 0, 816, 539);

        BackgroundImage bgEditUser = new BackgroundImage("/backgrounds/bgEditUser.png");
        bgEditUser.setBounds(0, 0, 800, 500);



        JLabel labelEditUser = new JLabel();
        ImageIcon defaultIconEditUser = new ImageIcon(getClass().getResource("/dialogButtons/edit.png"));
        ImageIcon enteredIconEditUser = new ImageIcon(getClass().getResource("/dialogButtons/editEntered.png"));
        ImageIcon pressedIconEditUser = new ImageIcon(getClass().getResource("/dialogButtons/editPressed.png"));

        ppLabels(editUserLayer);
        userAdditions(editUserLayer);

        labelID.setText("#" + CustomerStorage.getCurrentCustomer().getCustomerId());

        dialogEditUser.addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                labelEditUser.requestFocusInWindow(); // Or any other component
            }
        });

        textFieldName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldName.getText().equals(CustomerStorage.getCurrentCustomer().getCustomerName())) {
                    textFieldName.setText("");
                    textFieldName.setForeground(new Color(0x363f4f));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldName.getText().isEmpty()) {
                    textFieldName.setForeground(Color.GRAY);
                    textFieldName.setText(CustomerStorage.getCurrentCustomer().getCustomerName());
                }
            }
        });

// Set placeholder manually at initialization
        textFieldName.setText(CustomerStorage.getCurrentCustomer().getCustomerName());
        textFieldName.setForeground(Color.GRAY);

        textFieldSurname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldSurname.getText().equals(CustomerStorage.getCurrentCustomer().getCustomerSurname())) {
                    textFieldSurname.setText("");
                    textFieldSurname.setForeground(new Color(0x363f4f));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldSurname.getText().isEmpty()) {
                    textFieldSurname.setForeground(Color.GRAY);
                    textFieldSurname.setText(CustomerStorage.getCurrentCustomer().getCustomerSurname());
                }
            }
        });

        textFieldSurname.setText(CustomerStorage.getCurrentCustomer().getCustomerSurname());
        textFieldSurname.setForeground(Color.GRAY);

        labelEditUser.setIcon(defaultIconEditUser);
        labelEditUser.setBounds(591, 449, 167, 38);
        labelEditUser.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CustomerStorage.getCurrentCustomer().setCustomerName(textFieldName.getText());
                CustomerStorage.getCurrentCustomer().setCustomerSurname(textFieldSurname.getText());
                CustomerStorage.getCurrentCustomer().setCustomerPhoto(PPMouseListener.getPpPath());

                dialogEditUser.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelEditUser.setIcon(pressedIconEditUser);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelEditUser.setIcon(defaultIconEditUser);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelEditUser.setIcon(enteredIconEditUser);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelEditUser.setIcon(defaultIconEditUser);
            }
        });

        JLabel labelRemoveUser = new JLabel();
        ImageIcon defaultIconRemoveUser = new ImageIcon(getClass().getResource("/dialogButtons/remove.png"));
        ImageIcon enteredIconRemoveUser = new ImageIcon(getClass().getResource("/dialogButtons/removeEntered.png"));
        ImageIcon pressedIconRemoveUser = new ImageIcon(getClass().getResource("/dialogButtons/removePressed.png"));

        labelRemoveUser.setIcon(defaultIconRemoveUser);
        labelRemoveUser.setBounds(42, 449, 167, 38);
        labelRemoveUser.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialogEditUser.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelRemoveUser.setIcon(pressedIconRemoveUser);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelRemoveUser.setIcon(defaultIconRemoveUser);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelRemoveUser.setIcon(enteredIconRemoveUser);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelRemoveUser.setIcon(defaultIconRemoveUser);
            }
        });



        dialogEditUser.setSize(816, 539);
        dialogEditUser.setLocationRelativeTo(null);
        dialogEditUser.setResizable(false);
        dialogEditUser.setLayout(null);

        editUserLayer.add(bgEditUser, JLayeredPane.DEFAULT_LAYER);
        editUserLayer.add(labelEditUser, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(labelRemoveUser, JLayeredPane.PALETTE_LAYER);

        dialogEditUser.add(editUserLayer);
        dialogEditUser.setVisible(true);
    }

    private void addPackage() {
        //      ADD PACKAGE ---------------------------------------------------------------

        JDialog dialogAddPackage = new JDialog(currentFrame, "Add Package", true);

        JLayeredPane editAddPackage = new JLayeredPane();
        editAddPackage.setBounds(0, 0, 816, 639);

        BackgroundImage bgAddPackage = new BackgroundImage("/backgrounds/bgAddPackage.png");
        bgAddPackage.setBounds(0, 0, 800, 600);

        JLabel labelAddPackage = new JLabel();
        ImageIcon defaultIconAddPackage = new ImageIcon(getClass().getResource("/dialogButtons/create.png"));
        ImageIcon enteredIconAddPackage = new ImageIcon(getClass().getResource("/dialogButtons/createEntered.png"));
        ImageIcon pressedIconAddPackage = new ImageIcon(getClass().getResource("/dialogButtons/createPressed.png"));

        labelAddPackage.setIcon(defaultIconAddPackage);
        labelAddPackage.setBounds(591, 547, 167, 38);
        labelAddPackage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textFieldCourierName.getText().isEmpty() || textFieldDeliveryName.getText().isEmpty() || selectedDate == null || comboBoxCustomer.getSelectedItem() == null || comboBoxCity.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(bgAddPackage,
                            "Error: Please ensure all fields are filled in:\n" +
                                    "- Courier Name\n" +
                                    "- Delivery Name\n" +
                                    "- Delivery Date\n" +
                                    "- Customer Selection\n" +
                                    "- City Selection",
                            "Missing Information",
                            JOptionPane.WARNING_MESSAGE);

                }
                else {
                    Cargo cargo = new Cargo(selectedDate, dontRing, textFieldCourierName.getText(), PPMouseListener.getPpPath(),textFieldDeliveryName.getText(), (City) comboBoxCity.getSelectedItem());

                    Customer customer = (Customer) comboBoxCustomer.getSelectedItem();

                    customer.addCargo(cargo);


                    System.out.println(cargo);
                    dialogAddPackage.dispose();

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelAddPackage.setIcon(pressedIconAddPackage);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelAddPackage.setIcon(defaultIconAddPackage);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelAddPackage.setIcon(enteredIconAddPackage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelAddPackage.setIcon(defaultIconAddPackage);
            }
        });

        ppLabels(editAddPackage);
        packageAdditions(editAddPackage);

        dialogAddPackage.setSize(816, 639);
        dialogAddPackage.setLocationRelativeTo(null);
        dialogAddPackage.setResizable(false);
        dialogAddPackage.setLayout(null);

        editAddPackage.add(bgAddPackage, JLayeredPane.DEFAULT_LAYER);
        editAddPackage.add(labelAddPackage, JLayeredPane.PALETTE_LAYER);

        dialogAddPackage.add(editAddPackage);
        dialogAddPackage.setVisible(true);
    }

    private void editPackage() {
        JDialog dialogEditPackage = new JDialog(currentFrame, "Edit Package", true);

        JLayeredPane editEditPackage = new JLayeredPane();
        editEditPackage.setName("editPackageLayer");
        editEditPackage.setBounds(0, 0, 816, 639);

        BackgroundImage bgEditPackage = new BackgroundImage("/backgrounds/bgEditPackage.png");
        bgEditPackage.setBounds(0, 0, 800, 600);

        ppLabels(editEditPackage);
        packageAdditions(editEditPackage);

        Cargo currentCargo = CargoStorage.getCurrentCargo();
        if (currentCargo != null) {
            textFieldCourierName.setText(currentCargo.getCourierName());
            textFieldDeliveryName.setText(currentCargo.getCargoName());
            comboBoxCity.setSelectedItem(currentCargo.getCity());
            comboBoxCustomer.setSelectedItem(currentCargo.getCustomer());
        }

        JLabel labelEditPackage = new JLabel();
        ImageIcon defaultIconEditPackage = new ImageIcon(getClass().getResource("/dialogButtons/edit.png"));
        ImageIcon enteredIconEditPackage = new ImageIcon(getClass().getResource("/dialogButtons/editEntered.png"));
        ImageIcon pressedIconEditPackage = new ImageIcon(getClass().getResource("/dialogButtons/editPressed.png"));

        labelEditPackage.setIcon(defaultIconEditPackage);
        labelEditPackage.setBounds(591, 547, 167, 38);

        labelEditPackage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textFieldCourierName.getText().isEmpty() || textFieldDeliveryName.getText().isEmpty() && selectedDate == null || comboBoxCustomer.getSelectedItem() == null || comboBoxCity.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(editEditPackage,
                            "Error: Please ensure all fields are filled in:\n" +
                                    "- Courier Name\n" +
                                    "- Delivery Name\n" +
                                    "- Delivery Date\n" +
                                    "- Customer Selection\n" +
                                    "- City Selection",
                            "Missing Information",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                    currentCargo.setCourierName(textFieldCourierName.getText());
                    currentCargo.setCargoName(textFieldDeliveryName.getText());
                    currentCargo.setCity((City) comboBoxCity.getSelectedItem());
                    currentCargo.setCourierPhoto(PPMouseListener.getPpPath());
                    currentCargo.setCustomer((Customer) comboBoxCustomer.getSelectedItem());

                    dialogEditPackage.dispose();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelEditPackage.setIcon(pressedIconEditPackage);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelEditPackage.setIcon(defaultIconEditPackage);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelEditPackage.setIcon(enteredIconEditPackage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelEditPackage.setIcon(defaultIconEditPackage);
            }
        });

        editEditPackage.add(bgEditPackage, JLayeredPane.DEFAULT_LAYER);
        editEditPackage.add(labelEditPackage, JLayeredPane.PALETTE_LAYER);

        dialogEditPackage.setSize(816, 639);
        dialogEditPackage.setLocationRelativeTo(null);
        dialogEditPackage.setResizable(false);
        dialogEditPackage.setLayout(null);
        dialogEditPackage.add(editEditPackage);
        dialogEditPackage.setVisible(true);
    }


    private void ppLabels(JLayeredPane layeredPane) {
        JLabel labelEffectMan = new JLabel();
        labelEffectMan.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/ppEntered.png")));
        labelEffectMan.setBounds(34, 75, 264, 264);
        labelEffectMan.setVisible(false);

        JLabel labelMan = new JLabel();
        labelMan.setName("labelMan");
        labelMan.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/man.png")));
        labelMan.setBounds(99, 140, 134, 134);
        labelMan.addMouseListener(new PPMouseListener(labelMan, labelEffectMan));

        JLabel labelEffectWoman = new JLabel();
        labelEffectWoman.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/ppEntered.png")));
        labelEffectWoman.setBounds(268, 75, 264, 264);
        labelEffectWoman.setVisible(false);

        JLabel labelWoman = new JLabel();
        labelWoman.setName("labelWoman");
        labelWoman.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/woman.png")));
        labelWoman.setBounds(333, 140, 134, 134);
        labelWoman.addMouseListener(new PPMouseListener(labelWoman, labelEffectWoman));

        JLabel labelEffectPlus = new JLabel();
        labelEffectPlus.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/ppEntered.png")));
        labelEffectPlus.setBounds(502, 75, 264, 264);
        labelEffectPlus.setVisible(false);

        JLabel labelPlus = new JLabel();
        labelPlus.setName("labelPlus");
        labelPlus.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/plus.png")));
        labelPlus.setBounds(567, 140, 134, 134);
        labelPlus.addMouseListener(new PPMouseListener(labelPlus, labelEffectPlus));

        layeredPane.add(labelMan, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(labelEffectMan, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(labelWoman, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(labelEffectWoman, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(labelPlus, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(labelEffectPlus, JLayeredPane.PALETTE_LAYER);
    }

    private void userAdditions(JLayeredPane layeredPane){
        textFieldName = new JTextField();
        textFieldName.setBounds(325, 303, 347, 38);
        textFieldName.setBorder(null);
        textFieldName.setOpaque(false);

        textFieldName.setSelectionColor(new Color(0xe2c3e50));
        textFieldName.setSelectedTextColor(new Color(0xbdc3c7));

        textFieldSurname = new JTextField();
        textFieldSurname.setBounds(325, 383, 347, 38);
        textFieldSurname.setBorder(null);
        textFieldSurname.setOpaque(false);

        textFieldSurname.setSelectionColor(new Color(0xe2c3e50));
        textFieldSurname.setSelectedTextColor(new Color(0xbdc3c7));

        labelID = new JLabel("#" + Customer.idCounter);
        labelID.setBounds(318, 449, 252, 38);
        labelID.setForeground(new Color(0xbdc3c7));
        labelID.setHorizontalAlignment(SwingConstants.RIGHT);

        try {
            Font montserratFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Medium.ttf")).deriveFont(26f);
            textFieldName.setFont(montserratFont);
            textFieldSurname.setFont(montserratFont);
            Font labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Light.ttf")).deriveFont(26f);
            labelID.setFont(labelFont);
        } catch (FontFormatException | IOException exception) {
            exception.printStackTrace();
            textFieldName.setFont(new Font("SansSerif", Font.PLAIN, 12));
            textFieldSurname.setFont(new Font("SansSerif", Font.PLAIN, 12));
        }

        textFieldName.setForeground(new Color(0x363f4f));
        textFieldSurname.setForeground(new Color(0x363f4f));

        layeredPane.add(textFieldName, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(textFieldSurname, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(labelID, JLayeredPane.PALETTE_LAYER);
    }

    private void packageAdditions(JLayeredPane layeredPane){
        textFieldCourierName = new JTextField();
        textFieldCourierName.setBounds(325, 303, 347, 38);
        textFieldCourierName.setBorder(null);
        textFieldCourierName.setOpaque(false);

        textFieldCourierName.setSelectionColor(new Color(0xe2c3e50));
        textFieldCourierName.setSelectedTextColor(new Color(0xbdc3c7));

        textFieldDeliveryName = new JTextField();
        textFieldDeliveryName.setBounds(325, 383, 347, 38);
        textFieldDeliveryName.setBorder(null);
        textFieldDeliveryName.setOpaque(false);

        textFieldDeliveryName.setSelectionColor(new Color(0xe2c3e50));
        textFieldDeliveryName.setSelectedTextColor(new Color(0xbdc3c7));

        labelIDDelivery = new JLabel("#" + Cargo.idCounter);
        labelIDDelivery.setBounds(318, 547, 252, 38);
        labelIDDelivery.setForeground(new Color(0xbdc3c7));
        labelIDDelivery.setHorizontalAlignment(SwingConstants.RIGHT);

        try {
            Font montserratFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Medium.ttf")).deriveFont(26f);
            textFieldCourierName.setFont(montserratFont);
            textFieldDeliveryName.setFont(montserratFont);
            Font labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Light.ttf")).deriveFont(26f);
            labelIDDelivery.setFont(labelFont);
        } catch (FontFormatException | IOException exception) {
            exception.printStackTrace();
            textFieldCourierName.setFont(new Font("SansSerif", Font.PLAIN, 12));
            textFieldDeliveryName.setFont(new Font("SansSerif", Font.PLAIN, 12));
        }

        textFieldCourierName.setForeground(new Color(0x363f4f));
        textFieldDeliveryName.setForeground(new Color(0x363f4f));

        JLabel labelDontRing = new JLabel();
        ImageIcon defaultIconDontRing = new ImageIcon(getClass().getResource("/dialogButtons/dontRing.png"));
        ImageIcon enteredIconDontRing = new ImageIcon(getClass().getResource("/dialogButtons/dontRingEntered.png"));
        ImageIcon pressedIconDontRing = new ImageIcon(getClass().getResource("/dialogButtons/dontRingClicked.png"));

        labelDontRing.setIcon(defaultIconDontRing);
        labelDontRing.setBounds(42, 547, 167, 38);

        if (layeredPane.getName() == "editPackageLayer")
            dontRing = CargoStorage.getCurrentCargo().isDontRing();

        labelDontRing.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dontRing = !dontRing;

                if (dontRing) {
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
                if (dontRing) {
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
                if (dontRing) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }
        });

        DatePicker datePicker = new DatePicker();
        datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
        datePicker.setUsePanelOption(true);
        datePicker.setColor(new Color(0xe74c3c));
        datePicker.setDateSelectionAble(new DateSelectionAble() {
            @Override
            public boolean isDateSelectedAble(LocalDate localDate) {
                return !localDate.isBefore(CurrentDate.currentDate);
            }
        });

        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {
                selectedDate = datePicker.getSelectedDate();
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                if (selectedDate == null) {
                    System.out.println("No date selected");
                } else {
                    System.out.println("Selected date: " + selectedDate.format(df));
                }
            }
        });

        JFormattedTextField editor = new JFormattedTextField();
        editor.setBounds(16, 482, 232, 39);
        editor.setBackground(new Color(0x2c3e50));

        editor.setBorder(new RoundedBorder(20, new Color(0x159a80), 2));

        datePicker.setEditor(editor);

        try {
            Font montserratFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Regular.ttf")).deriveFont(17f);
            editor.setFont(montserratFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            editor.setFont(new Font("SansSerif", Font.PLAIN, 12));
        }

        editor.setSelectionColor(new Color(0xbdc3c7));
        editor.setSelectedTextColor(new Color(0x2c3e50));

        UIManager.put("ComboBox.selectionBackground", new Color(0x159a80));
        UIManager.put("ComboBox.selectionForeground", new Color(0xbdc3c7));

        UIManager.put("ComboBox.buttonArrowColor", Color.WHITE); // Set arrow color to black
        UIManager.put("ComboBox.buttonBackground", new Color(0x2c3e50)); // Optional: Background color new Color(0x323232)

        comboBoxCustomer = new JComboBox<>();
        comboBoxCustomer.setBounds(284, 482, 232, 39);
        comboBoxCustomer.setBackground(new Color(0x2c3e50));

        for (Customer customer : CustomerStorage.getAllCustomers()) {
            comboBoxCustomer.addItem(customer);
        }

        comboBoxCustomer.setForeground(Color.WHITE);

        comboBoxCustomer.setBorder(new RoundedBorder(20, new Color(0x159a80), 2));

        comboBoxCity = new JComboBox<>();
        comboBoxCity.setBounds(552, 482, 232, 39);
        comboBoxCity.setBackground(new Color(0x2c3e50));

        for (City city : CityStorage.getAllCities().values()) {
            comboBoxCity.addItem(city);
        }

        comboBoxCity.setForeground(Color.WHITE);

        comboBoxCity.setBorder(new RoundedBorder(20, new Color(0x159a80), 2));

        layeredPane.add(textFieldCourierName, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(textFieldDeliveryName, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(labelIDDelivery, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(labelDontRing, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(editor, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(comboBoxCity, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(comboBoxCustomer, JLayeredPane.PALETTE_LAYER);
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
                dialogChooseAccount.dispose();
                CustomerStorage.setCurrentCustomer((Customer) comboBox.getSelectedItem());
                System.out.println("Current Customer: " + CustomerStorage.getCurrentCustomer().getCustomerName());
                editUser();
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

        JLayeredPane packageLayer = new JLayeredPane();
        packageLayer.setBounds(0, 0, 816, 289);

        // Set up background image
        BackgroundImage bgChoosePackage = new BackgroundImage("/backgrounds/bgChoosePackage.png");
        bgChoosePackage.setBounds(0, 0, 800, 250);

        // Set up "Choose" button
        JLabel labelChoose = new JLabel();
        ImageIcon defaultIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/choose.png"));
        ImageIcon enteredIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/chooseEntered.png"));
        ImageIcon pressedIconChoose = new ImageIcon(getClass().getResource("/dialogButtons/choosePressed.png"));

        labelChoose.setIcon(defaultIconChoose);
        labelChoose.setBounds(591, 194, 167, 38);
        labelChoose.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialogChoosePackage.dispose();
                CargoStorage.setCurrentCargo(((Cargo) comboBoxPackage .getSelectedItem()));
                System.out.println("Current Cargo: " + CargoStorage.getCurrentCargo().getPostId());
                editPackage();
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

        // Configure ComboBox
        UIManager.put("ComboBox.selectionBackground", new Color(0x159a80));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        comboBoxPackage = new JComboBox<>();
        comboBoxPackage.setBounds(117, 108, 567, 54);
        comboBoxPackage.setBackground(new Color(0x159a80));
        comboBoxPackage.setForeground(Color.WHITE);

        // Add all customers to ComboBox
        for (Cargo cargo : CargoStorage.getAllCargos()) {
            comboBoxPackage.addItem(cargo);
        }

        // Customize ComboBox border
        Border defaultBorder = new CompoundBorder(
                new LineBorder(new Color(0x159a80), 2), // Default border color
                new EmptyBorder(5, 5, 5, 5)
        );
        Border focusedBorder = new CompoundBorder(
                new LineBorder(Color.WHITE, 2), // White border when focused
                new EmptyBorder(5, 5, 5, 5)
        );

        comboBoxPackage.setBorder(defaultBorder);

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

        // Dialog setup
        dialogChoosePackage.setSize(816, 289);
        dialogChoosePackage.setLocationRelativeTo(null);
        dialogChoosePackage.setResizable(false);
        dialogChoosePackage.setLayout(null);

        // Add components to layered pane
        packageLayer.add(bgChoosePackage, JLayeredPane.DEFAULT_LAYER);
        packageLayer.add(labelChoose, JLayeredPane.PALETTE_LAYER);
        packageLayer.add(comboBoxPackage, JLayeredPane.PALETTE_LAYER);

        // Add layered pane to dialog
        dialogChoosePackage.add(packageLayer);
        dialogChoosePackage.setVisible(true);
    }

}

