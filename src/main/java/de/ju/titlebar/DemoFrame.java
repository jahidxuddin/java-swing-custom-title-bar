package de.ju.titlebar;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DemoFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo");
        frame.setSize(1080, 720);
        frame.setMinimumSize(new Dimension(1080, 720));
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());

        CustomTitleBar customTitleBar = new CustomTitleBar(frame);
        customTitleBar.setTitleBarHeight(40);

        Color bgBlue = new Color(29, 99, 237);
        customTitleBar.setUiColors(
                bgBlue,
                Color.WHITE,
                Color.LIGHT_GRAY,
                new Color(254, 85, 90)
        );

        TitleBarIconLabel titleBarLabel = new TitleBarIconLabel("Custom Title Bar", Color.WHITE);
        titleBarLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        customTitleBar.addComponentLeft(titleBarLabel);
        frame.add(customTitleBar, BorderLayout.NORTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class TitleBarIconLabel extends JPanel {

    public TitleBarIconLabel(String text, Color foregroundColor) {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 12, FlowLayout.CENTER));

        add(new TitleBarIconComponent(foregroundColor));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(foregroundColor);
        add(label);
    }
}

class TitleBarIconComponent extends JComponent {
    private final Color color;

    public TitleBarIconComponent(Color color) {
        this.color = color;
        setPreferredSize(new Dimension(48, 48));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        int winWidth = 36;
        int winHeight = 26;
        int winX = (w - winWidth) / 2;
        int winY = (h - winHeight) / 2;
        int titleBarHeight = 8;

        g2.setColor(color);
        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(new RoundRectangle2D.Float(winX, winY, winWidth, winHeight, 6, 6));

        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(winX, winY + titleBarHeight, winX + winWidth, winY + titleBarHeight);

        int dotSize = 3;
        int dotY = winY + (titleBarHeight / 2) - (dotSize / 2) + 1;
        int startDotX = winX + 5;
        int dotSpacing = 5;

        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
        for (int i = 0; i < 3; i++) {
            g2.fillOval(startDotX + (i * dotSpacing), dotY, dotSize, dotSize);
        }

        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g2.drawLine(winX + 6, winY + 14, winX + 22, winY + 14);
        g2.drawLine(winX + 6, winY + 19, winX + 16, winY + 19);

        g2.dispose();
    }
}