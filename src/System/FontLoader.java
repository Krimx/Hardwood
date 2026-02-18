package System;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

    /**
     * Overload 1: Loads a standard system font (e.g., Arial, Times New Roman).
     * * @param fontName The name of the font installed on the system.
     * @param style    The font style (e.g., Font.PLAIN, Font.BOLD, Font.ITALIC).
     * @param size     The size of the font.
     */
    public static Font loadFont(String fontName, int style, int size) {
        return new Font(fontName, style, size);
    }

    /**
     * Overload 2: Loads a custom font (.ttf or .otf) from your source/resource folder.
     * * @param filePath The internal path to the font (e.g., "/fonts/MyCustomFont.ttf").
     * @param size     The size of the font (float is required by deriveFont).
     */
    public static Font loadFont(String filePath, float size) {
        try {
            // Read the file from inside the project structure/jar
            InputStream is = FontLoader.class.getResourceAsStream(filePath);
            
            if (is == null) {
                System.err.println("Font file not found: " + filePath + ". Using fallback.");
                return new Font("Arial", Font.PLAIN, (int) size); // Fallback font
            }

            // Create the raw font (base size is 1pt)
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            
            // Register it to the system so Java's Graphics2D can render it properly
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            // Scale the font up to the requested size
            return customFont.deriveFont(size);

        } catch (FontFormatException | IOException e) {
            System.err.println("Error loading font: " + filePath);
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size); // Fallback font
        }
    }
    
    public static Dimension getTextDimensions(Font font, String text) {
        // Create a tiny 1x1 pixel invisible image just to get a Graphics object
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Apply the font to the graphics object
        g2d.setFont(font);
        
        // Get the metrics (the math behind the font's sizing)
        FontMetrics metrics = g2d.getFontMetrics();
        
        // Calculate dimensions
        int width = metrics.stringWidth(text);
        int height = metrics.getHeight(); // Gets the total height (ascent + descent)
        
        // Clean up memory
        g2d.dispose();
        
        return new Dimension(width, height);
    }
}