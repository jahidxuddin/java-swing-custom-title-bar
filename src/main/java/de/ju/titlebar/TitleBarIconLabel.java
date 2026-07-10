package de.ju.titlebar;

import javax.swing.*;
import java.awt.*;

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