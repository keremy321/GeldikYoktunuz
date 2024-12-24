package org.geldikYoktunuz;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CustomTable extends JPanel {

    JTable table;

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

        table = new JTable(tableModel) {
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
                    label.setFont(getMontserratFont(14)); // Set Montserrat font for cells
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
        table.setAutoCreateRowSorter(true); // Enable sorting
        table.setRowHeight(40);

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
        header.setFont(getMontserratSemiBoldFont(14)); // Set Montserrat SemiBold font for header
        header.setBackground(new Color(0x34495e)); // Header background color
        header.setForeground(new Color(0xe74c3c)); // Header text color
        header.setReorderingAllowed(false);

        // Center-align header text and customize grid line color (top, bottom, and vertical)
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(getMontserratSemiBoldFont(14));
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

        Cargo currentCargo = CargoStorage.getCargoById(Integer.parseInt(rowData[0].toString()));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JLayeredPane deliveryLayer = new JLayeredPane();
        deliveryLayer.setName("delivery");
        deliveryLayer.setBounds(0, 0, 1116, 739);

        BackgroundImage bgDelivery = new BackgroundImage("/backgrounds/bg0.png");

        switch (currentCargo.getCargoStatus()){
            case PENDING_APPROVAL:
                bgDelivery = new BackgroundImage("/backgrounds/bg1.png");
                break;
            case IN_PROCESS:
                bgDelivery = new BackgroundImage("/backgrounds/bg2.png");
                break;
            case OUT_FOR_DELIVERY:
                bgDelivery = new BackgroundImage("/backgrounds/bg3.png");
                break;
            case DELIVERED:
                bgDelivery = new BackgroundImage("/backgrounds/bg4.png");
                break;
            default:
                bgDelivery = new BackgroundImage("/backgrounds/bg0.png");
        }

        bgDelivery.setBounds(0, 0, 1100, 700);

        JLabel labelNameAndID = new JLabel();
        labelNameAndID.setText(currentCargo.getCargoName() + " : #" + currentCargo.getPostId());
        labelNameAndID.setBounds(100, 74, 1000, 60);
        labelNameAndID.setForeground(new Color(0xf7f7f7));
        labelNameAndID.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelCustomerName = new JLabel();
        labelCustomerName.setText(currentCargo.getCustomer().getCustomerName() + " " + currentCargo.getCustomer().getCustomerSurname());
        labelCustomerName.setBounds(325, 575, 300, 40);
        labelCustomerName.setForeground(new Color(0x34495e));

        JLabel labelCustomerID = new JLabel();
        labelCustomerID.setText("#" + currentCargo.getCustomer().getCustomerId());
        labelCustomerID.setBounds(325, 605, 300, 40);
        labelCustomerID.setForeground(new Color(0x34495e));

        JLabel labelCourierName = new JLabel();
        labelCourierName.setText(currentCargo.getCourierName());
        labelCourierName.setBounds(817, 575, 300, 40);
        labelCourierName.setForeground(new Color(0x34495e));

        JLabel labelDeliveryStatus = new JLabel();
        labelDeliveryStatus.setText(currentCargo.getCargoStatus().getDescription());
        labelDeliveryStatus.setBounds(144, 172, 181, 30);
        labelDeliveryStatus.setForeground(new Color(0x95A5A6));
        labelDeliveryStatus.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelDestinationCity = new JLabel();
        labelDestinationCity.setText(currentCargo.getCity().toString());
        labelDestinationCity.setBounds(391, 172, 188, 30);
        labelDestinationCity.setForeground(new Color(0x95A5A6));
        labelDestinationCity.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelShipmentDate = new JLabel();
        labelShipmentDate.setText(currentCargo.getPostDate().format(df));
        labelShipmentDate.setBounds(645, 172, 167, 30);
        labelShipmentDate.setForeground(new Color(0x95A5A6));
        labelShipmentDate.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelDeliveryDate = new JLabel();
        labelDeliveryDate.setText(
                currentCargo.getDeliveryDate() != null
                        ? currentCargo.getDeliveryDate().format(df)
                        : "-"
        );        labelDeliveryDate.setBounds(896, 172, 158, 30);
        labelDeliveryDate.setForeground(new Color(0x95A5A6));
        labelDeliveryDate.setHorizontalAlignment(SwingConstants.CENTER);

        CircularImagePanel circularImagePanel = new CircularImagePanel(currentCargo.getCustomer().getCustomerPhoto(), 150);
        circularImagePanel.setBounds(150, 526, 150, 150);

        CircularImagePanel circularImagePanelCourier = new CircularImagePanel(currentCargo.getCourierPhoto(), 150);
        circularImagePanelCourier.setBounds(641, 526, 150, 150);

        try {
            Font montserratBoldFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf")).deriveFont(36f);
            Font montserratBoldSmallFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf")).deriveFont(27f);
            Font montserratLightFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Light.ttf")).deriveFont(27f);
            Font montserratLightSmallFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-Regular.ttf")).deriveFont(20f);
            labelNameAndID.setFont(montserratBoldFont);
            labelCustomerName.setFont(montserratBoldSmallFont);
            labelCourierName.setFont(montserratBoldSmallFont);
            labelCustomerID.setFont(montserratLightFont);
            labelDeliveryStatus.setFont(montserratLightSmallFont);
            labelDestinationCity.setFont(montserratLightSmallFont);
            labelShipmentDate.setFont(montserratLightSmallFont);
            labelDeliveryDate.setFont(montserratLightSmallFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            labelNameAndID.setFont(new Font("SansSerif", Font.PLAIN, 12));
        }

        boolean dontRing = false;

        JLabel labelCancel = new JLabel();
        ImageIcon defaultIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancel.png"));
        ImageIcon enteredIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelEntered.png"));
        ImageIcon pressedIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelPressed.png"));

        labelCancel.setIcon(defaultIconRemoveUser);
        labelCancel.setBounds(668, 399, 317, 39);
        labelCancel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CargoCancel.cargoCanceling(currentCargo);
                updateDeliveryLayer(deliveryLayer, currentCargo);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelCancel.setIcon(pressedIconRemoveUser);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelCancel.setIcon(defaultIconRemoveUser);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelCancel.setIcon(enteredIconRemoveUser);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelCancel.setIcon(defaultIconRemoveUser);
            }
        });

        JLabel labelDontRing = new JLabel();
        ImageIcon defaultIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRing.png"));
        ImageIcon enteredIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRingEntered.png"));
        ImageIcon pressedIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRingClicked.png"));



        labelDontRing.setIcon(defaultIconDontRing);
        labelDontRing.setBounds(223, 399, 317, 39);

        final boolean[] dontRingWrapper = {false};

        labelDontRing.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dontRingWrapper[0] = !dontRingWrapper[0];

                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }

                currentCargo.setDontRing(dontRingWrapper[0]);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelDontRing.setIcon(enteredIconDontRing);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelDontRing.setIcon(enteredIconDontRing);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }
        });

        newFrame.setSize(1119, 739);
        newFrame.setLocationRelativeTo(null);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        newFrame.add(deliveryLayer);

        deliveryLayer.add(bgDelivery, JLayeredPane.DEFAULT_LAYER);
        deliveryLayer.add(labelNameAndID, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCustomerName, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCustomerID, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCourierName, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryStatus, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDestinationCity, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelShipmentDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanelCourier, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCancel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDontRing, JLayeredPane.PALETTE_LAYER);

        newFrame.setVisible(true);
    }

    public JTable getTable() {
        return table; // Expose the JTable instance
    }

    public void setModel(TableModel model) {
        table.setModel(model);
    }

    public TableModel getModel() {
        return table.getModel();
    }

    private void updateDeliveryLayer(JLayeredPane deliveryLayer, Cargo currentCargo) {
        // Remove all existing components from the layer
        deliveryLayer.removeAll();

        // Set the updated background based on cargo status
        BackgroundImage bgDelivery;
        switch (currentCargo.getCargoStatus()){
            case PENDING_APPROVAL:
                bgDelivery = new BackgroundImage("/backgrounds/bg1.png");
                break;
            case IN_PROCESS:
                bgDelivery = new BackgroundImage("/backgrounds/bg2.png");
                break;
            case OUT_FOR_DELIVERY:
                bgDelivery = new BackgroundImage("/backgrounds/bg3.png");
                break;
            case DELIVERED:
                bgDelivery = new BackgroundImage("/backgrounds/bg4.png");
                break;
            default:
                bgDelivery = new BackgroundImage("/backgrounds/bg0.png");
        }
        bgDelivery.setBounds(0, 0, 1100, 700);
        deliveryLayer.add(bgDelivery, JLayeredPane.DEFAULT_LAYER);

        // Update cargo information
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JLabel labelNameAndID = createLabel(
                currentCargo.getCargoName() + " : #" + currentCargo.getPostId(),
                new Rectangle(100, 74, 1000, 60),
                new Color(0xf7f7f7),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.BOLD, 20) // Placeholder font
        );

        JLabel labelCustomerName = createLabel(
                currentCargo.getCustomer().getCustomerName() + " " + currentCargo.getCustomer().getCustomerSurname(),
                new Rectangle(325, 575, 300, 40),
                new Color(0x34495e),
                SwingConstants.LEFT,
                new Font("SansSerif", Font.BOLD, 18) // Placeholder font
        );

        JLabel labelDeliveryStatus = createLabel(
                currentCargo.getCargoStatus().getDescription(),
                new Rectangle(144, 172, 181, 30),
                new Color(0x95A5A6),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.PLAIN, 16) // Placeholder font
        );

        JLabel labelDestinationCity = createLabel(
                currentCargo.getCity().toString(),
                new Rectangle(391, 172, 188, 30),
                new Color(0x95A5A6),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.PLAIN, 16) // Placeholder font
        );

        JLabel labelShipmentDate = createLabel(
                currentCargo.getPostDate().format(df),
                new Rectangle(645, 172, 167, 30),
                new Color(0x95A5A6),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.PLAIN, 16) // Placeholder font
        );

        JLabel labelDeliveryDate = createLabel(
                currentCargo.getDeliveryDate() != null ? currentCargo.getDeliveryDate().format(df) : "-",
                new Rectangle(896, 172, 158, 30),
                new Color(0x95A5A6),
                SwingConstants.CENTER,
                new Font("SansSerif", Font.PLAIN, 16) // Placeholder font
        );

        boolean dontRing = false;

        JLabel labelCancel = new JLabel();
        ImageIcon defaultIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancel.png"));
        ImageIcon enteredIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelEntered.png"));
        ImageIcon pressedIconRemoveUser = new ImageIcon(getClass().getResource("/trackingButtons/cancelPressed.png"));

        labelCancel.setIcon(defaultIconRemoveUser);
        labelCancel.setBounds(668, 399, 317, 39);
        labelCancel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CargoCancel.cargoCanceling(CargoStorage.getCurrentCargo());
                updateDeliveryLayer(deliveryLayer, CargoStorage.getCurrentCargo());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelCancel.setIcon(pressedIconRemoveUser);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelCancel.setIcon(defaultIconRemoveUser);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelCancel.setIcon(enteredIconRemoveUser);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelCancel.setIcon(defaultIconRemoveUser);
            }
        });

        JLabel labelDontRing = new JLabel();
        ImageIcon defaultIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRing.png"));
        ImageIcon enteredIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRingEntered.png"));
        ImageIcon pressedIconDontRing = new ImageIcon(getClass().getResource("/trackingButtons/dontRingClicked.png"));



        labelDontRing.setIcon(defaultIconDontRing);
        labelDontRing.setBounds(223, 399, 317, 39);

        final boolean[] dontRingWrapper = {false};

        labelDontRing.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dontRingWrapper[0] = !dontRingWrapper[0];

                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                labelDontRing.setIcon(enteredIconDontRing);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelDontRing.setIcon(enteredIconDontRing);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (dontRingWrapper[0]) {
                    labelDontRing.setIcon(pressedIconDontRing);
                } else {
                    labelDontRing.setIcon(defaultIconDontRing);
                }
            }
        });

        CircularImagePanel circularImagePanel = new CircularImagePanel(currentCargo.getCustomer().getCustomerPhoto(), 150);
        circularImagePanel.setBounds(150, 526, 150, 150);

        CircularImagePanel circularImagePanelCourier = new CircularImagePanel(currentCargo.getCourierPhoto(), 150);
        circularImagePanelCourier.setBounds(641, 526, 150, 150);

        // Add components to delivery layer
        deliveryLayer.add(labelNameAndID, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCustomerName, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryStatus, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDestinationCity, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelShipmentDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDeliveryDate, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(circularImagePanelCourier, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelCancel, JLayeredPane.PALETTE_LAYER);
        deliveryLayer.add(labelDontRing, JLayeredPane.PALETTE_LAYER);

        // Revalidate and repaint the delivery layer
        deliveryLayer.revalidate();
        deliveryLayer.repaint();
    }

    // Create a helper method to generate labels
    private JLabel createLabel(String text, Rectangle bounds, Color color, int alignment, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(bounds);
        label.setForeground(color);
        label.setHorizontalAlignment(alignment);
        label.setFont(font);
        return label;
    }


}