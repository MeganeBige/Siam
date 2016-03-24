package siam.graphics;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontTools {

    private Font menuFont;
    private Font police;
    private Font textFont;
    private Font menuFontStarWars;
    private Font menuFontChristmas;
    private Font textFontChristmas;
    private Font textFontStarWars;

    public FontTools() {
        try {
            menuFont = loadFont("/fonts/Frazzle.ttf");
            textFont = loadFont("/fonts/Alabama.ttf");
            menuFontStarWars = loadFont("/fonts/SFDistantGalaxy.ttf");
            textFontStarWars = loadFont("/fonts/SFDistantGalaxy.ttf");
            menuFontChristmas = loadFont("/fonts/Candcu.ttf");
            textFontChristmas = loadFont("/fonts/kr.ttf");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Font loadFont(String string) throws FontFormatException, IOException {
        return Font.createFont(Font.TRUETYPE_FONT, InputStream.class.getResourceAsStream(string));
    }

    public void updateFontJButton(JButton button, int size, Color color, Font font) {
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(color);
        button.setFont(police);
        button.setFocusPainted(false);
    }

    public void updateFontJLabel(JLabel label, int size, Color color, Font font) {
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        label.setForeground(color);
        label.setOpaque(false);
        label.setFont(police);
    }

    public void updateFontJTextField(JTextField textField, int size, Color color, Font font) {
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        textField.setForeground(color);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createLineBorder(color));
        textField.setFont(police);
    }

    public void updateFontJCheckBox(JCheckBox checkBox, int size, Color color, Font font) {
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        checkBox.setForeground(color);
        checkBox.setOpaque(false);
        checkBox.setBorderPainted(false);
        checkBox.setContentAreaFilled(false);
        checkBox.setFont(police);
    }

    public void updateFondJRadioButton(JRadioButton radioButton, int size, Color color, Font font) {
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        radioButton.setForeground(color);
        radioButton.setOpaque(false);
        radioButton.setBorderPainted(false);
        radioButton.setContentAreaFilled(false);
        radioButton.setFont(police);
    }

    public Font getMenuFont() {
        return menuFont;
    }

    public Font getTextFont() {
        return textFont;
    }

    public Font getMenuFontStarWars() {
        return menuFontStarWars;
    }

    public Font getTextFontStarWars() {
        return textFontStarWars;
    }

    public Font getMenuFontChristmas() {
        return menuFontChristmas;
    }

    public Font getTextFontChristmas() {
        return textFontChristmas;
    }
}