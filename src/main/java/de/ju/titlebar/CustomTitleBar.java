package de.ju.titlebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTitleBar extends JPanel {

    private final JFrame frame;
    private final JButton maxBtn;
    private final JPanel leftComponentBox;

    private int pX, pY;
    private int paddingX = 10;
    private int paddingY = 15;

    private Color iconColor = new Color(150, 160, 180);
    private Color hoverColor = Color.WHITE;
    private Color closeHoverColor = new Color(239, 68, 68);

    public CustomTitleBar(JFrame frame) {
        this.frame = frame;

        setOpaque(false);
        setLayout(new BorderLayout());
        updatePadding();

        leftComponentBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        leftComponentBox.setOpaque(false);

        JPanel titleContainer = new JPanel(new GridBagLayout());
        titleContainer.setOpaque(false);
        titleContainer.add(leftComponentBox);
        add(titleContainer, BorderLayout.WEST);

        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 24, 0));
        actionButtonPanel.setOpaque(false);

        JButton minBtn = new JButton() {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    @Override
                    public void mouseExited(MouseEvent e) { hover = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? hoverColor : iconColor);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(2, 14, 16, 14);
                g2.dispose();
            }
        };
        minBtn.setPreferredSize(new Dimension(18, 18));
        minBtn.setBorder(BorderFactory.createEmptyBorder());
        minBtn.setContentAreaFilled(false);
        minBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minBtn.addActionListener(e -> frame.setExtendedState(JFrame.ICONIFIED));

        maxBtn = new JButton() {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    @Override
                    public void mouseExited(MouseEvent e) { hover = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? hoverColor : iconColor);
                g2.setStroke(new BasicStroke(2.0f));

                if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    g2.drawRect(2, 5, 9, 9);
                    g2.fillRect(6, 2, 9, 2);
                    g2.fillRect(13, 2, 2, 9);
                } else {
                    g2.drawRoundRect(2, 2, 14, 14, 2, 2);
                }
                g2.dispose();
            }
        };
        maxBtn.setPreferredSize(new Dimension(18, 18));
        maxBtn.setBorder(BorderFactory.createEmptyBorder());
        maxBtn.setContentAreaFilled(false);
        maxBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        maxBtn.addActionListener(e -> toggleMaximize());

        JButton closeBtn = new JButton() {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    @Override
                    public void mouseExited(MouseEvent e) { hover = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? closeHoverColor : iconColor);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(2, 2, 16, 16);
                g2.drawLine(16, 2, 2, 16);
                g2.dispose();
            }
        };
        closeBtn.setPreferredSize(new Dimension(18, 18));
        closeBtn.setBorder(BorderFactory.createEmptyBorder());
        closeBtn.setContentAreaFilled(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> System.exit(0));

        actionButtonPanel.add(minBtn);
        actionButtonPanel.add(maxBtn);
        actionButtonPanel.add(closeBtn);

        JPanel actionContainer = new JPanel(new GridBagLayout());
        actionContainer.setOpaque(false);
        actionContainer.add(actionButtonPanel);
        add(actionContainer, BorderLayout.EAST);

        setupDragListeners();
    }

    private void toggleMaximize() {
        if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            frame.setExtendedState(JFrame.NORMAL);
            frame.setSize(1080, 720);
            frame.setLocationRelativeTo(null);
        } else {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        maxBtn.repaint();
    }

    public void addComponentLeft(JComponent comp) {
        leftComponentBox.add(comp);
        leftComponentBox.revalidate();
        leftComponentBox.repaint();
    }

    public void setTitleBarHeight(int height) {
        this.paddingY = Math.max(0, (height - 18) / 2);
        updatePadding();
    }

    public void setTitleBarPadding(int paddingX, int paddingY) {
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        updatePadding();
    }

    private void updatePadding() {
        setBorder(BorderFactory.createEmptyBorder(paddingY, paddingX, paddingY, paddingX));
        revalidate();
    }

    public void setUiColors(Color backgroundColor, Color tintColor, Color hoverColor, Color closeHoverColor) {
        if (backgroundColor != null) {
            setOpaque(true);
            setBackground(backgroundColor);
        } else {
            setOpaque(false);
        }
        this.iconColor = tintColor;
        this.hoverColor = hoverColor;
        this.closeHoverColor = closeHoverColor;
        repaint();
    }

    private void setupDragListeners() {
        MouseAdapter dragListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pX = e.getX();
                pY = e.getY();

                if (e.getClickCount() == 2 && frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    toggleMaximize();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    frame.setExtendedState(JFrame.NORMAL);
                    frame.setSize(1080, 720);
                    frame.setLocationRelativeTo(null);
                    maxBtn.repaint();

                    pX = frame.getWidth() / 2;
                    pY = e.getY();
                }

                int newX = frame.getLocation().x + e.getX() - pX;
                int newY = frame.getLocation().y + e.getY() - pY;
                if (newY < 0) newY = 0;
                frame.setLocation(newX, newY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (frame.getLocation().y <= 0 && frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    maxBtn.repaint();
                }
            }
        };
        addMouseListener(dragListener);
        addMouseMotionListener(dragListener);
    }
}