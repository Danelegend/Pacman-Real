package me.dane.pacman.screens;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    private Image background;

    public Menu() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(background, 0, 0, this);
        }

    }

    public void drawButton(Graphics g, int x, int y, int height, int width, Color buttonColour, String text, Color textColour, String font, int size, int style) {
        g.setColor(buttonColour);
        g.fillRect(x + 450, y, width, height);

        Font stringFont = new Font(font, style, size);

        FontMetrics metrics = g.getFontMetrics(stringFont);

        x = ((getWidth() - metrics.stringWidth(text))/2);
        y = y + 22;

        g.setFont(stringFont);
        g.setColor(textColour);
        g.drawString(text, x, y+height/2);
    }

    public void drawTitle(Graphics g, int y, String title, Color textColor, String font, int size, int style) {
        Font stringFont = new Font(font, style, size);
        FontMetrics metrics = g.getFontMetrics(stringFont);

        int x = ((getWidth() - metrics.stringWidth(title)) / 2);
        y = y + 18;

        g.setColor(textColor);
        g.setFont(stringFont);
        g.drawString(title, x, y);
    }

    public void setBackground(String location) {
        background = Toolkit.getDefaultToolkit().createImage(location);
    }

    public void changeBackgroundImage(String location) {
        background = Toolkit.getDefaultToolkit().createImage(location);
    }

}