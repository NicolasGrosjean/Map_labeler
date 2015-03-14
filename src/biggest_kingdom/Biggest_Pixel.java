package biggest_kingdom;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JProgressBar;

import Text.AbstractText;

public class Biggest_Pixel {
	// RGB code of sea and unknown provinces
	static private int SEA_R = 51;
	static private int SEA_G = 67;
	static private int SEA_B = 85;
	static private int UNKNOWN_R = 38;
	static private int UNKNOWN_G = 38;
	static private int UNKNOWN_B = 38;

	// Black and white pixels
	static private int BLACK = 0;
	static private int WHITE = 0xffffff;

	private BufferedImage map;
	private String newMapFile;
	private JProgressBar bar;
	private AbstractText text;
	private int nbKingdoms;

	public Biggest_Pixel(File mapFile, String newMapFile,
			JProgressBar bar, AbstractText text, int nbKingdoms) throws IOException {
		this.bar = bar;
		this.map = ImageIO.read(mapFile);
		this.newMapFile = newMapFile;
		this.text = text;
		this.nbKingdoms = nbKingdoms;	
		// To simplify we measure progression by line
		bar.setMaximum(2 * map.getHeight());
		bar.setMinimum(0);
		new Thread(new Algorithm()).run();
	}

	class Algorithm implements Runnable {

		@Override
		public void run() {
			Map<Integer, Integer> kingdoms = new HashMap<Integer, Integer>();

			// Counting pixel for each Kingdom
			for (int y = 0; y < map.getHeight(); y++) {
				bar.setValue(y);
				for (int x = 0; x < map.getWidth(); x++) {
					// Integer and not int because it can be null
					Integer nbPixels = kingdoms.get(0xffffff & map.getRGB(x, y));
					if (nbPixels == null) {
						// First occur of this kingdom
						kingdoms.put(0xffffff & map.getRGB(x, y), 1);
					} else {
						// Another pixel of this kingdom
						kingdoms.put(0xffffff & map.getRGB(x, y), ++nbPixels);
					}
				}
			}

			// Calculating the nbProvinces to display
			if (nbKingdoms > kingdoms.size() + 1) {
				throw new IllegalArgumentException("Plus d'Etat demandés que sur la map"); // TODO Text
			}
			// Ranking kingdoms by decreasing pixel number
			PriorityQueue<Kingdom> orderedKingdoms = new PriorityQueue<Kingdom>(kingdoms.size());
			Set<Integer> kingdomRGB = kingdoms.keySet();
			for (int rgb : kingdomRGB) {
				orderedKingdoms.offer(new Kingdom(rgb, kingdoms.get(rgb)));
			}
			LinkedList<Integer> kingdomToDisplay = new LinkedList<Integer>();
			int foundKingdoms = 0;
			while (foundKingdoms < nbKingdoms) {
				int rgb = orderedKingdoms.poll().getRGB();
				if (rgb != (SEA_R << 16) + (SEA_G << 8) + SEA_B &&
						rgb != (UNKNOWN_R << 16) + (UNKNOWN_G << 8) + UNKNOWN_B) {
					kingdomToDisplay.addLast(rgb);
					foundKingdoms++;
				}
			}
			/* Transforming the image to the writing image
			 * by putting in white the pixels not in the kingdom to display */
			for (int y = 0; y < map.getHeight(); y++) {
				bar.setValue(map.getHeight() + y);
				for (int x = 0; x < map.getWidth(); x++) {
					// Don't touch to sea pixels and unknown
					if ((map.getRGB(x, y) & 0xffffff) != (SEA_R << 16)
							+ (SEA_G << 8) + SEA_B
							&& (map.getRGB(x, y) & 0xffffff) != (UNKNOWN_R << 16)
									+ (UNKNOWN_G << 8) + UNKNOWN_B
							&& kingdomToDisplay
									.indexOf(map.getRGB(x, y) & 0xffffff) == -1) {
						map.setRGB(x, y, WHITE);
					}
				}
			}

			// Write image on png file
			try {
				bar.setString(text.waitingMessage());
				ImageIO.write(map, "png", new File(newMapFile));
				System.out.println(text.endMessage(newMapFile));
				System.exit(0);
			} catch (IOException e) {
				System.out.println(text.writingError() + newMapFile);
				System.exit(1);
			}
		}
	}


}
