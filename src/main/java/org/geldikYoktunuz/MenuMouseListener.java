package org.geldikYoktunuz;

import javax.swing.*;
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

    public MenuMouseListener(JLabel label, String key, JLayeredPane[] layers , JLayeredPane nextLayer) {

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
        getCurrentLayer().setVisible(false);
        nextLayer.setVisible(true);
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
}
