package System;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ImageLoader {

    /**
     * Loads an image from the project's source/resource folder.
     * @param filePath The internal path to the image (e.g., "/sprites/player.png").
     * @return A BufferedImage containing the loaded image, or a fallback image if it fails.
     */
    public static BufferedImage loadImage(String filePath) {
        try {
            // 1. Get the raw data stream from the project resources
            InputStream is = ImageLoader.class.getResourceAsStream(filePath);
            
            if (is == null) {
                System.err.println("ERROR: Image file not found: " + filePath);
                return createFallbackImage();
            }

            // 2. Tell ImageIO to convert that data into a usable Image object
            return ImageIO.read(is);

        } catch (IOException e) {
            System.err.println("ERROR: Could not read image data: " + filePath);
            e.printStackTrace();
            return createFallbackImage();
        }
    }

    /**
     * Generates a bright magenta 32x32 square.
     * Used so the game doesn't crash if an image path is wrong.
     */
    private static BufferedImage createFallbackImage() {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        
        // Magenta is the universal "missing texture" color in game dev
        g2.setColor(Color.MAGENTA); 
        g2.fillRect(0, 0, 32, 32);
        
        g2.dispose();
        return img;
    }
    
    public static BufferedImage getSlice(BufferedImage sheet, int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}