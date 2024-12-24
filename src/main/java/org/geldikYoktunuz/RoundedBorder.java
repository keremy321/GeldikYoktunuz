package org.geldikYoktunuz;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundedBorder extends AbstractBorder {
    private final int cornerRadius;
    private final Color borderColor;
    private final int thickness;

    public RoundedBorder(int cornerRadius, Color borderColor, int thickness) {
        this.cornerRadius = cornerRadius;
        this.borderColor = borderColor;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(thickness));

        g2.setColor(borderColor);

        g2.drawRoundRect(x + thickness / 2, y + thickness / 2,
                width - thickness, height - thickness,
                cornerRadius, cornerRadius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness + 4, thickness + 8, thickness + 4, thickness + 8);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = thickness + 8;
        return insets;
    }
}
