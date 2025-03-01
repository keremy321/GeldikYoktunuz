package org.geldikYoktunuz;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;

public class SignInMouseListener implements MouseListener {
        private String path;
        private JLabel label;
        private JLabel effect;
        private ImageIcon enteredIcon;
        private ImageIcon pressedIcon;
        private JFrame currentFrame;
        private static boolean dontRing = false;
        private LocalDate[] selectedDates = new LocalDate[2];
        JComboBox<Customer> comboBox;

        public SignInMouseListener(JLabel label, JLabel effect, String path, JFrame currentFrame) {
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
        }

    @Override
    public void mouseClicked(MouseEvent e) {
        effect.setVisible(false);

        if (label.getName().equals("asAdmin")) {
            currentFrame.dispose();
            new AdminMainFrame();
        } else if (label.getName().equals("asCustomer")) {
            chooseAccount();
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

    public void chooseAccount() {
        FlatMacDarkLaf.setup();

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
                currentFrame.dispose();
                CustomerStorage.setCurrentCustomer((Customer) comboBox.getSelectedItem());
                new CustomerMainFrame();
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

        UIManager.put("ComboBox.arc", 15);


        UIManager.put("ComboBox.selectionBackground", new Color(0x159a80));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        UIManager.put("ComboBox.buttonArrowColor", Color.WHITE);
        UIManager.put("ComboBox.buttonBackground", new Color(0x2c3e50));


        comboBox = new JComboBox<>();
        comboBox.setBounds(117, 108, 567, 54);
        comboBox.setBackground(new Color(0x159a80));


        comboBox.setForeground(Color.WHITE);

        Border defaultBorder = new CompoundBorder(
                new LineBorder(new Color(0x159a80), 2),
                new EmptyBorder(5, 5, 5, 5)
        );
        Border focusedBorder = new CompoundBorder(
                new LineBorder(Color.WHITE, 2),
                new EmptyBorder(5, 5, 5, 5)
        );

        comboBox.setBorder(defaultBorder);

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


        for (Customer customer : CustomerStorage.getAllCustomers()) {
            comboBox.addItem(customer);
        }


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
}
