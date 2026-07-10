package de.ju.titlebar;

import javax.swing.*;
import java.awt.*;

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