package org.geldikYoktunuz;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class PPMouseListener implements MouseListener {
    private JLabel label;
    private JLabel effect;
    private ImageIcon enteredIcon;
    private ImageIcon pressedIcon;
    private ImageIcon selectedIcon;
    private String ppPath;

    private static JLabel lastSelectedEffect;

    public PPMouseListener(JLabel label, JLabel effect) {
        this.label = label;
        this.effect = effect;

        this.enteredIcon = (ImageIcon) effect.getIcon();

        pressedIcon = new ImageIcon(getClass().getResource("/dialogButtons/ppPressed.png"));
        selectedIcon = new ImageIcon(getClass().getResource("/dialogButtons/ppSelected.png"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String selectedFileAbsolutePath = null;

        if (lastSelectedEffect != null && lastSelectedEffect != effect) {
            lastSelectedEffect.setVisible(false);
            lastSelectedEffect.setIcon(enteredIcon);
        }

        if ("labelPlus".equals(label.getName())) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose a PNG or JPG file");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().toLowerCase().endsWith(".png")
                            || file.getName().toLowerCase().endsWith(".jpg");
                }

                @Override
                public String getDescription() {
                    return "Image Files (*.png, *.jpg)";
                }
            });

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFileAbsolutePath = selectedFile.getAbsolutePath();
            }
        }

        switch (label.getName()) {
            case "labelMan":
                ppPath = "/dialogButtons/man.png";
                break;
            case "labelWoman":
                ppPath = "/dialogButtons/woman.png";
                break;
            case "labelPlus":
                if (selectedFileAbsolutePath != null) {
                    ppPath = selectedFileAbsolutePath;
                }
                break;
            default:
                ppPath = "/dialogButtons/plus.png";
        }

        System.out.println("Selected Path: " + ppPath);

        effect.setVisible(true);
        effect.setIcon(selectedIcon);

        lastSelectedEffect = effect;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (effect != lastSelectedEffect) {
            effect.setVisible(true);
            effect.setIcon(pressedIcon);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (effect != lastSelectedEffect) {
            effect.setVisible(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (effect != lastSelectedEffect) {
            effect.setVisible(true);
            effect.setIcon(enteredIcon);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (effect != lastSelectedEffect) {
            effect.setVisible(false);
        }
    }
}
