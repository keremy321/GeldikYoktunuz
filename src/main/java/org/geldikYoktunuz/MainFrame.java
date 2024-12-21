package org.geldikYoktunuz;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame implements ActionListener {

    public static LocalDate currentDate = LocalDate.now();

    public MainFrame() {
        JLayeredPane frameLayer = new JLayeredPane();
        frameLayer.setBounds(0,0,1116,739);

        JLayeredPane homeLayer = new JLayeredPane();
        homeLayer.setBounds(0,0,1116,739);

        JLayeredPane deliveryLayer = new JLayeredPane();
        deliveryLayer.setBounds(0,0,1116,739);


        JLayeredPane managementLayer = new JLayeredPane();
        managementLayer.setBounds(0,0,1116,739);

        JLayeredPane accountLayer = new JLayeredPane();
        accountLayer.setBounds(0,0,1116,739);

        JLayeredPane[] layers = {homeLayer, deliveryLayer, managementLayer, accountLayer};

//      HOME LAYER

        BackgroundImage bgHome = new BackgroundImage("/backgrounds/bgHome.png");
        bgHome.setBounds(0, 0, 1100, 700);

//      MANAGEMENT LAYER

        BackgroundImage bgManagement = new BackgroundImage("/backgrounds/bgManagement.png");
        bgManagement.setBounds(0, 0, 1100, 700);

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

//      MAIN FRAME

        JLabel labelDelivery = new JLabel();
        labelDelivery.setIcon(new ImageIcon(getClass().getResource("/menuButtons/delivery.png")));
        labelDelivery.setBounds(25, 135, 50, 30);
        labelDelivery.addMouseListener(new MenuMouseListener(labelDelivery, "delivery", layers, homeLayer));

        JLabel labelManagement = new JLabel();
        labelManagement.setIcon(new ImageIcon(getClass().getResource("/menuButtons/management.png")));
        labelManagement.setBounds(28, 218, 46, 46);
        labelManagement.addMouseListener(new MenuMouseListener(labelManagement, "management", layers, managementLayer));

        JLabel labelHelp = new JLabel();
        labelHelp.setIcon(new ImageIcon(getClass().getResource("/menuButtons/help.png")));
        labelHelp.setBounds(25, 525, 50, 50);
        labelHelp.addMouseListener(new MenuMouseListener(labelHelp, "help", layers, homeLayer));

        JLabel labelAccount = new JLabel();
        labelAccount.setIcon(new ImageIcon(getClass().getResource("/menuButtons/account.png")));
        labelAccount.setBounds(25, 615, 50, 50);
        labelAccount.addMouseListener(new MenuMouseListener(labelAccount, "account", layers, accountLayer));

        JLabel labelCurrentDate = new JLabel();
        labelCurrentDate.setText(getCurrentDate());
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
                currentDate = currentDate.plusDays(1);
                labelCurrentDate.setText(getCurrentDate());
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


        accountLayer.add(bgAccount, JLayeredPane.DEFAULT_LAYER);

        this.setLayout(null);
        this.setTitle("Geldik Yoktunuz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1116, 739);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(formatter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
