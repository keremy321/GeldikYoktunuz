package org.geldikYoktunuz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class MenuMouseListener implements MouseListener {

    private ImageIcon enteredIcon;
    private ImageIcon pressedIcon;
    private ImageIcon originalIcon;
    private JLabel label;
    private String key;
    private JLayeredPane[] layers;
    private JLayeredPane nextLayer;
    private JFrame currentFrame;

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
        if ((currentFrame.getName() == "adminFrame") && (nextLayer.getName() == "account")) {
            chooseAccount();
        }
        else{
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

        dialogChooseAccount.setSize(816, 289);
        dialogChooseAccount.setLocationRelativeTo(null);
        dialogChooseAccount.setResizable(false);
        dialogChooseAccount.setLayout(null);

        chooseUserLayer.add(byChooseAccount, JLayeredPane.DEFAULT_LAYER);
        chooseUserLayer.add(labelChoose, JLayeredPane.PALETTE_LAYER);

        dialogChooseAccount.add(chooseUserLayer);
        dialogChooseAccount.setVisible(true);
    }
}
