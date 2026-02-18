package System;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import Objects.Block;

public class MapLoader {
	public static ArrayList<Block> loadMap(String filePath) {
		ArrayList<Block> map = new ArrayList<>();
		try {
			InputStream is = MapLoader.class.getResourceAsStream(filePath);
			Scanner scanner = new Scanner(is);
			while (scanner.hasNextLine()) {
				map.add(loadBlock(scanner.nextLine()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	public static Block loadBlock(String mapLine) {
		Scanner line = new Scanner(mapLine);
		int x = Integer.valueOf(line.next());
		int y = Integer.valueOf(line.next());
		int w = Integer.valueOf(line.next());
		int h = Integer.valueOf(line.next());
		int layer = Integer.valueOf(line.next());
		return new Block(x,y,w,h,layer);
	}
}
