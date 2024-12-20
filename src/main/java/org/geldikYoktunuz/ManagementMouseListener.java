package org.geldikYoktunuz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;

public class ManagementMouseListener implements MouseListener {
    private String path;
    private JLabel label;
    private JLabel effect;
    private ImageIcon enteredIcon;
    private ImageIcon pressedIcon;
    private JFrame currentFrame;

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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        effect.setVisible(false);

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
                dialogAddUser.dispose();
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

        JLabel labelEffectMan = new JLabel();
        labelEffectMan.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/ppEntered.png")));
        labelEffectMan.setBounds(34, 75, 264, 264);
        labelEffectMan.setVisible(false);

        JLabel labelMan = new JLabel();
        labelMan.setName("labelManEdit");
        labelMan.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/man.png")));
        labelMan.setBounds(99, 140, 134, 134);
        labelMan.addMouseListener(new PPMouseListener(labelMan, labelEffectMan));

        JLabel labelEffectWoman = new JLabel();
        labelEffectWoman.setIcon(new ImageIcon(getClass().getResource("/dialogButtons/ppEntered.png")));
        labelEffectWoman.setBounds(268, 75, 264, 264);
        labelEffectWoman.setVisible(false);

        JLabel labelWoman = new JLabel();
        labelWoman.setName("labelWomanEdit");
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

        JTextField textFieldName = new JTextField();
        textFieldName.setBounds(325, 303, 347, 38);
        textFieldName.setBorder(null);
        textFieldName.setOpaque(false);

        textFieldName.setSelectionColor(new Color(0xe2c3e50));
        textFieldName.setSelectedTextColor(new Color(0xbdc3c7));

        JTextField textFieldSurname = new JTextField();
        textFieldSurname.setBounds(325, 383, 347, 38);
        textFieldSurname.setBorder(null);
        textFieldSurname.setOpaque(false);

        textFieldSurname.setSelectionColor(new Color(0xe2c3e50));
        textFieldSurname.setSelectedTextColor(new Color(0xbdc3c7));

        JLabel labelID = new JLabel("#ID");
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

        dialogAddUser.setSize(816, 539);
        dialogAddUser.setLocationRelativeTo(null);
        dialogAddUser.setResizable(false);
        dialogAddUser.setLayout(null);

        addUserLayer.add(bgAddUser, JLayeredPane.DEFAULT_LAYER);
        addUserLayer.add(labelAddUser, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(labelMan, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(labelEffectMan, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(labelWoman, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(labelEffectWoman, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(labelPlus, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(labelEffectPlus, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(textFieldName, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(textFieldSurname, JLayeredPane.PALETTE_LAYER);
        addUserLayer.add(labelID, JLayeredPane.PALETTE_LAYER);

        dialogAddUser.add(addUserLayer);
        dialogAddUser.setVisible(true);




        JDialog dialogEditUser = new JDialog(currentFrame, "Add User", true);

        JLayeredPane editUserLayer = new JLayeredPane();
        editUserLayer.setBounds(0, 0, 816, 539);

        BackgroundImage bgEditUser = new BackgroundImage("/backgrounds/bgEditUser.png");
        bgEditUser.setBounds(0, 0, 800, 500);

        JLabel labelEditUser = new JLabel();
        ImageIcon defaultIconEditUser = new ImageIcon(getClass().getResource("/dialogButtons/edit.png"));
        ImageIcon enteredIconEditUser = new ImageIcon(getClass().getResource("/dialogButtons/editEntered.png"));
        ImageIcon pressedIconEditUser = new ImageIcon(getClass().getResource("/dialogButtons/editPressed.png"));

        labelEditUser.setIcon(defaultIconEditUser);
        labelEditUser.setBounds(591, 449, 167, 38);
        labelEditUser.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
        editUserLayer.add(labelMan, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(labelEffectMan, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(labelWoman, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(labelEffectWoman, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(labelPlus, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(labelEffectPlus, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(textFieldName, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(textFieldSurname, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(labelID, JLayeredPane.PALETTE_LAYER);
        editUserLayer.add(labelRemoveUser, JLayeredPane.PALETTE_LAYER);

        dialogEditUser.add(editUserLayer);
        dialogEditUser.setVisible(true);
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
}
