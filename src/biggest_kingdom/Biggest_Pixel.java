package biggest_kingdom;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
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
		/**
		 * Give middle of the province with this rgb
		 * @param rgb
		 * @return
		 */
		private Point getPosition(int rgb) {
			int sumX = 0;
			int sumY = 0;
			int nbPoints = 0;
			for (int y = 0; y < map.getHeight(); y++) {
				for (int x = 0; x < map.getWidth(); x++) {
					// On chercher une province ayant le même R && G && B
					if ((map.getRGB(x, y) & 0xffffff) == (rgb  & 0xffffff)) {
						sumX += x;
						sumY += y;
						nbPoints++;
					}
				}
			}
			if (nbPoints > 0) {
				return new Point(sumX / nbPoints, sumY / nbPoints);
			} else {
				throw new IllegalArgumentException();
			}
		}

		private void writeText(Point origin, String textToWrite) {
			Graphics2D g2d = map.createGraphics();
	        g2d.setPaint(Color.black);
	        g2d.setFont(new Font("Serif", Font.BOLD, 40));
	        FontMetrics fm = g2d.getFontMetrics();
	        int borderMarge = 5;
	        int x = (int)origin.getX() -
	        		fm.stringWidth(textToWrite)/2;
	        int y = (int)origin.getY() -
	        		fm.getHeight()/2;
	        // If the text is not in the image we move it
	        if (x + fm.stringWidth(textToWrite) > map.getWidth()) {
	        	x = map.getWidth() - fm.stringWidth(textToWrite) - borderMarge;
	        }
	        if (y + fm.getHeight() > map.getHeight()) {
	        	y = map.getWidth() - fm.getHeight() - borderMarge;
	        }
	        g2d.drawString(textToWrite, x, y);
	        g2d.dispose();
		}

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
			LandedTitle landedTitles = new LandedTitle();
			Localisation localisation = new Localisation();
			for (Integer i : kingdomToDisplay) {
				String stateCode = landedTitles.getStateCode(i);				
				if (stateCode != null) {
					String stateName = localisation.getStateName(stateCode);
					if (stateName != null) {
						writeText(getPosition(i), stateName);
					} else {
						writeText(getPosition(i), stateCode);
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
