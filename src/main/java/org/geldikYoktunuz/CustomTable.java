package org.geldikYoktunuz;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CustomTable extends JPanel {

    public CustomTable(Object[][] data, String[] columnNames) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 380)); // Set panel size

        // Custom TableModel to handle ID column as Integer for sorting
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class; // Define the ID column as Integer
                }
                return String.class; // Other columns are Strings
            }
        };

        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);

                // Selection colors
                if (isCellSelected(row, column)) {
                    component.setBackground(new Color(0x16a085)); // Selection background
                    component.setForeground(new Color(0xFFFFFF)); // Selected text color
                } else {
                    component.setBackground(getBackground());
                    component.setForeground(getForeground());
                }

                // Add gray borders to the left, right, and bottom of rows
                if (component instanceof JComponent) {
                    JComponent jc = (JComponent) component;
                    jc.setBorder(BorderFactory.createMatteBorder(
                            0,                                // Top border (no border)
                            (column == 0 ? 1 : 0),           // Left border for the first column
                            1,                                // Bottom border for all rows
                            (column == getColumnCount() - 1 ? 1 : 0), // Right border for the last column
                            new Color(0xCCCCCC)));           // Gray border color
                }

                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFont(getMontserratFont(16)); // Set Montserrat font for cells
                }
                return component;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        // Clear initial focus but keep row selection
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Enable sorting
        table.setAutoCreateRowSorter(true);

        // Adjust column widths: ID column tighter, others equal
        int totalWidth = 800; // Total width of the table
        int idColumnWidth = 50; // Width for the ID column
        int otherColumnWidth = (totalWidth - idColumnWidth) / (columnNames.length - 1);

        table.getColumnModel().getColumn(0).setPreferredWidth(idColumnWidth); // Set tighter ID column width
        for (int i = 1; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(otherColumnWidth); // Set equal width for other columns
        }

        // Set table properties
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setOpaque(false);
        table.setBackground(new Color(0x2c3e50)); // Background color
        table.setForeground(new Color(0xFFFFFF)); // Text color
        table.setSelectionBackground(new Color(0x16a085)); // Selection color
        table.setSelectionForeground(new Color(0xFFFFFF)); // Selected text color

        // Set header properties
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(100, 40));
        header.setFont(getMontserratSemiBoldFont(18)); // Set Montserrat SemiBold font for header
        header.setBackground(new Color(0x34495e)); // Header background color
        header.setForeground(new Color(0xe74c3c)); // Header text color
        header.setReorderingAllowed(false);

        // Center-align header text and customize grid line color (top, bottom, and vertical)
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(getMontserratSemiBoldFont(18));
                label.setForeground(new Color(0xe74c3c));
                label.setBackground(new Color(0x34495e));
                label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0x16a085))); // Top, bottom, and vertical lines
                return label;
            }
        });

        // Add a MouseListener for row double-click events
        table.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double-click
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        openNewFrameForRow(data[row]);
                    }
                }
            }
        });

        // Wrap the table in a rounded JPanel
        JPanel roundedPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fill the entire panel with the outer background color
                g2d.setColor(new Color(0xf7f7f7)); // Outer area background color
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Draw rounded rectangle for the panel background
                g2d.setColor(new Color(0x2c3e50)); // Inner panel background color
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 60, 60);

                g2d.dispose();
            }
        };


        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.setPreferredSize(new Dimension(800, 380)); // Set rounded panel size
        roundedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        roundedPanel.add(new JScrollPane(table) {
            {
                getViewport().setOpaque(false); // Make viewport transparent
                setBorder(BorderFactory.createEmptyBorder()); // Remove border
                setBackground(new Color(0xf7f7f7)); // Outer area background color
            }
        }, BorderLayout.CENTER);

        add(roundedPanel, BorderLayout.CENTER);
    }

    private Font getMontserratFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Regular.ttf"))
                    .deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size); // Fallback font
        }
    }

    private Font getMontserratSemiBoldFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-SemiBold.ttf"))
                    .deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.BOLD, (int) size); // Fallback font
        }
    }

    private void openNewFrameForRow(Object[] rowData) {
        JFrame newFrame = new JFrame("Row Details");
        newFrame.setSize(400, 300);
        newFrame.setLocationRelativeTo(null);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0xf4f4f4));

        for (int i = 0; i < rowData.length; i++) {
            JLabel label = new JLabel("Column " + (i + 1) + ": " + rowData[i]);
            label.setFont(getMontserratFont(16));
            label.setForeground(new Color(0x34495e));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
        }

        newFrame.add(panel);
        newFrame.setVisible(true);
    }
}
