package de.ju.titlebar;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TitleBarIconComponent extends JComponent {

    private final Color baseColor;

    public TitleBarIconComponent(Color baseColor) {
        this.baseColor = baseColor;
        setPreferredSize(new Dimension(48, 48));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Hochwertiges Rendering aktivieren
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();

        // Perfekt proportionierte Maße für ein 48x48 Grid
        int winWidth = 38;
        int winHeight = 28;
        int winX = (w - winWidth) / 2;
        int winY = (h - winHeight) / 2;
        int titleBarHeight = 10;
        int cornerRadius = 8;

        // 1. Hintergrund / Fenster-Body (Subtiler Verlauf für Tiefe)
        g2.setColor(new Color(255, 255, 255, 40));
        g2.fill(new RoundRectangle2D.Float(winX, winY, winWidth, winHeight, cornerRadius, cornerRadius));

        // 2. Fenster-Rahmen (Outer Border)
        g2.setColor(baseColor);
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(new RoundRectangle2D.Float(winX, winY, winWidth, winHeight, cornerRadius, cornerRadius));

        // 3. Titelleisten-Trennlinie
        g2.setStroke(new BasicStroke(1.0f));
        g2.drawLine(winX, winY + titleBarHeight, winX + winWidth, winY + titleBarHeight);

        // 4. "Traffic Light" Punkte (Mac-Style für den "Schönheits"-Faktor)
        // Wenn du strikt monochrom bleiben willst, ersetze die Farben einfach durch 'baseColor' mit Alphas.
        Color[] dotColors = {
                new Color(255, 95, 86),  // Schönes Rot
                new Color(255, 189, 46), // Schönes Gelb
                new Color(39, 201, 63)   // Schönes Grün
        };

        int dotSize = 4;
        int dotY = winY + (titleBarHeight / 2) - (dotSize / 2);
        int startDotX = winX + 6;
        int dotSpacing = 6;

        for (int i = 0; i < 3; i++) {
            g2.setColor(dotColors[i]);
            g2.fillOval(startDotX + (i * dotSpacing), dotY, dotSize, dotSize);
        }

        // 5. "Content"-Linien im Fenster (Simulierter Text/UI)
        // Nutzen die Basisfarbe mit schöner Transparenz für den Flat-Design-Look
        g2.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 120));
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Obere längere Linie
        g2.drawLine(winX + 8, winY + 16, winX + winWidth - 8, winY + 16);
        // Untere kürzere Linie
        g2.drawLine(winX + 8, winY + 22, winX + winWidth - 16, winY + 22);

        g2.dispose();
    }
}