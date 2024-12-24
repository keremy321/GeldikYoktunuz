package org.geldikYoktunuz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.function.Consumer;

public class RoundedSearchBar extends JPanel {

    public RoundedSearchBar() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xf7f7f7));
        setPreferredSize(new Dimension(300, 50));

        JLabel searchIcon = new JLabel();
        searchIcon.setIcon(new ImageIcon(getClass().getResource("/icons/search-icon.png")));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 5));

        JTextField searchField = new JTextField("Search...") {
            private boolean clicked = false;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!clicked) {
                    setCaretPosition(0);
                    getCaret().setVisible(false);
                }
            }

            @Override
            public void processMouseEvent(java.awt.event.MouseEvent e) {
                super.processMouseEvent(e);
                if (e.getID() == java.awt.event.MouseEvent.MOUSE_CLICKED) {
                    clicked = true;
                    getCaret().setVisible(true);
                }
            }
        };
        searchField.setFont(getMontserratFont(26));
        searchField.setForeground(new Color(0x95a5a6));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));
        searchField.setOpaque(false);

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search...");
                    searchField.setForeground(new Color(0x95a5a6));
                }
            }
        });

        add(searchIcon, BorderLayout.WEST);
        add(searchField, BorderLayout.CENTER);
    }

    public RoundedSearchBar(Consumer<String> onSearch) {
        setLayout(new BorderLayout());
        setBackground(new Color(0xf7f7f7));
        setPreferredSize(new Dimension(300, 50));

        JLabel searchIcon = new JLabel();
        searchIcon.setIcon(new ImageIcon(getClass().getResource("/icons/search-icon.png")));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 5));

        JTextField searchField = new JTextField("Search...") {
            private boolean clicked = false;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!clicked) {
                    setCaretPosition(0);
                    getCaret().setVisible(false);
                }
            }

            @Override
            public void processMouseEvent(java.awt.event.MouseEvent e) {
                super.processMouseEvent(e);
                if (e.getID() == java.awt.event.MouseEvent.MOUSE_CLICKED) {
                    clicked = true;
                    getCaret().setVisible(true);
                }
            }
        };
        searchField.setFont(getMontserratFont(26));
        searchField.setForeground(new Color(0x95a5a6));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));
        searchField.setOpaque(false);

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search...");
                    searchField.setForeground(new Color(0x95a5a6));
                }
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                onSearch.accept(searchField.getText());
            }
        });

        add(searchIcon, BorderLayout.WEST);
        add(searchField, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);


        float arcSize;
        if (this.getWidth() == 171 && this.getHeight() == 33) {
            arcSize = 35f;
        } else {
            arcSize = 50f;
        }


        float borderWidth = 2.5f;
        float halfBorder = borderWidth / 2f;

        g2d.setColor(new Color(0xf7f7f7));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), (int) arcSize, (int) arcSize);

        g2d.setColor(new Color(0x16a085));
        g2d.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawRoundRect(
                Math.round(halfBorder),
                Math.round(halfBorder),
                Math.round(getWidth() - borderWidth),
                Math.round(getHeight() - borderWidth),
                Math.round(arcSize),
                Math.round(arcSize)
        );

        g2d.dispose();
    }

    private Font getMontserratFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Thin.ttf"))
                    .deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size); // Fallback font
        }
    }
}
