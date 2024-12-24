package org.geldikYoktunuz;

import javax.swing.*;

public class SignInFrame extends JFrame {

    public SignInFrame () {

        JLayeredPane frameLayer = new JLayeredPane();
        frameLayer.setBounds(0,0,1116,739);

        BackgroundImage bgSignIn = new BackgroundImage("/backgrounds/bgSignIn.png");
        bgSignIn.setBounds(0, 0, 1100, 700);

        JLabel labelEffectAsAdmin = new JLabel();
        labelEffectAsAdmin.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectAsAdmin.setBounds(151, 381, 286, 256);
        labelEffectAsAdmin.setVisible(false);

        JLabel labelAsAdmin = new JLabel();
        labelAsAdmin.setName("asAdmin");
        labelAsAdmin.setIcon(new ImageIcon(getClass().getResource("/signInButtons/asAdmin.png")));
        labelAsAdmin.setBounds(219, 449, 150, 120);
        labelAsAdmin.addMouseListener(new SignInMouseListener(labelAsAdmin, labelEffectAsAdmin, "/managementButtons/pressed.png", this));

        JLabel labelEffectAsCustomer = new JLabel();
        labelEffectAsCustomer.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectAsCustomer.setBounds(407, 381, 286, 256);
        labelEffectAsCustomer.setVisible(false);

        JLabel labelAsCustomer = new JLabel();
        labelAsCustomer.setName("asCustomer");
        labelAsCustomer.setIcon(new ImageIcon(getClass().getResource("/signInButtons/asCustomer.png")));
        labelAsCustomer.setBounds(475, 449, 150, 120);
        labelAsCustomer.addMouseListener(new SignInMouseListener(labelAsCustomer, labelEffectAsCustomer, "/managementButtons/pressed.png", this));

        JLabel labelEffectAddCustomer = new JLabel();
        labelEffectAddCustomer.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/entered.png")));
        labelEffectAddCustomer.setBounds(663, 381, 286, 256);
        labelEffectAddCustomer.setVisible(false);

        JLabel labelAddCustomer = new JLabel();
        labelAddCustomer.setName("addUser");
        labelAddCustomer.setIcon(new ImageIcon(getClass().getResource("/signInButtons/addCustomer.png")));
        labelAddCustomer.setBounds(731, 449, 150, 120);
        labelAddCustomer.addMouseListener(new ManagementMouseListener(labelAddCustomer, labelEffectAddCustomer, "/managementButtons/pressed.png", this));

        frameLayer.add(bgSignIn, JLayeredPane.DEFAULT_LAYER);
        frameLayer.add(labelEffectAsAdmin, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelAsAdmin, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelEffectAsCustomer, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelAsCustomer, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelEffectAddCustomer, JLayeredPane.PALETTE_LAYER);
        frameLayer.add(labelAddCustomer, JLayeredPane.PALETTE_LAYER);

        ImageIcon logo = new ImageIcon(getClass().getResource("/menuButtons/logoMenu.png"));
        this.setIconImage(logo.getImage());

        this.add(frameLayer);

        this.setLayout(null);
        this.setTitle("Geldik Yoktunuz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1116, 739);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }
}
