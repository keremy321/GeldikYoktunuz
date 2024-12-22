package org.geldikYoktunuz;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CircularImagePanel extends JPanel {
    private BufferedImage image;
    private int diameter;

    public CircularImagePanel(String imagePath, int diameter) {
        this.diameter = diameter;
        setPreferredSize(new Dimension(diameter, diameter));
        setOpaque(false); // Ensure the panel background is transparent

        try {
            File file = new File(imagePath);
            if (file.exists()) {
                image = ImageIO.read(file);
                System.out.println("Loaded image from file: " + imagePath);
            } else {
                var resourceStream = getClass().getResourceAsStream(imagePath);
                if (resourceStream != null) {
                    image = ImageIO.read(resourceStream);
                    System.out.println("Loaded image from resource: " + imagePath);
                } else {
                    System.err.println("Image not found: " + imagePath);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            BufferedImage resizedImage = getResizedImage(image, diameter, diameter);
            Graphics2D g2d = (Graphics2D) g.create();

            // Enable high-quality rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            // Create the circular clip
            Shape circle = new Ellipse2D.Double(0, 0, diameter, diameter);
            g2d.setClip(circle);

            // Draw the resized image inside the circular clip
            g2d.drawImage(resizedImage, 0, 0, null);

            g2d.dispose();
        } else {
            System.err.println("Image is null, cannot render!");
        }
    }

    private BufferedImage getResizedImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();

        // Enable high-quality scaling
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();

        return resizedImage;
    }
}
