package biggest_kingdom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JProgressBar;

import textWriting.BlockCutting;
import textWriting.Line;
import textWriting.Writing;
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
				throw new IllegalArgumentException(text.moreStateThanAvailable());
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
	        
			// Writing text
			bar.setString(text.textWritingMessage());
			// Load texts
			LandedTitle landedTitles = new LandedTitle();
			Localisation localisation = new Localisation();
			// Load lines of the states
			HashMap<Integer, LinkedList<Line>> h = BlockCutting.enumerateLine(
					map, (SEA_R << 16) + (SEA_G << 8) + SEA_B,
					(UNKNOWN_R << 16) + (UNKNOWN_G << 8) + UNKNOWN_B);	
			for (Integer i : kingdomToDisplay) {
				String stateCode = landedTitles.getStateCode(i);				
				if (stateCode != null) {
					// Search state name
					String stateName = localisation.getStateName(stateCode);
					// Loads lines for this state
					LinkedList<Line> state = h.get(i & 0xffffff);
					if (state == null) {
						throw new IllegalArgumentException("No block to cut");
					}
					// Blocks for this state
					LinkedList<PriorityQueue<Line>> l = BlockCutting.cutBlocks(state);
					// Text to write
					Writing w = new Writing();
					String textToWrite;
					if (stateName != null) {
						textToWrite = stateName;
					} else {
						textToWrite = stateCode;
					}
					for (PriorityQueue<Line> p : l) {
						// Calculate optimized writing
						w.calculateWriting(p, stateName, map);
						if (w.getTextOrigin() != null) {
							// Write text
							Graphics2D g2d = map.createGraphics();
					        g2d.setFont(new Font("Serif", Font.BOLD, w.getTextSize() - 1));
					        g2d.setColor(new Color((i & 0xffffff) ^ 0xffffff));
					        FontRenderContext frc = g2d.getFontRenderContext();
					        GlyphVector gv = g2d.getFont().createGlyphVector(frc, textToWrite);
					        // (0,0) because we need the offset
					        Rectangle textRect = gv.getPixelBounds(null, 0, 0);
					        g2d.drawString(textToWrite, w.getTextOrigin().x - textRect.x, w.getTextOrigin().y);
					        g2d.dispose();
						}
					}
				}
			}
			
			// Write image on png file
			try {
				bar.setString(text.outputWritingMessage());
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
