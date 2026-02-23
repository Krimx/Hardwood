package System;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import Objects.Block;

/**
 * Utility class for loading map layouts from text files.
 */
public class MapLoader {
    
    /**
     * Parses a text file and constructs a list of Blocks based on the data.
     * * @param filePath The classpath-relative path to the map text file.
     * @return An ArrayList containing all Blocks generated from the file.
     */
    public static ArrayList<Block> loadMap(String filePath) {
        ArrayList<Block> map = new ArrayList<>();
        try {
            InputStream is = MapLoader.class.getResourceAsStream(filePath);
            Scanner scanner = new Scanner(is);
            
            // Read the file line-by-line and convert each line into a Block
            while (scanner.hasNextLine()) {
                map.add(loadBlock(scanner.nextLine()));
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return map;
    }
    
    /**
     * Parses a single line of text into a Block object.
     * The line must contain 5 space-separated integers: X, Y, Width, Height, Layer.
     * * @param mapLine A string containing block data.
     * @return A constructed Block object.
     */
    public static Block loadBlock(String mapLine) {
        Scanner line = new Scanner(mapLine);
        int x = Integer.valueOf(line.next());
        int y = Integer.valueOf(line.next());
        int w = Integer.valueOf(line.next());
        int h = Integer.valueOf(line.next());
        int layer = Integer.valueOf(line.next());
        line.close();
        return new Block(x, y, w, h, layer);
    }
}