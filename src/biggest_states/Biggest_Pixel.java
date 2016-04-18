package biggest_states;

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

import colors.MapColors;
import stateNames.GameFiles;
import textWriting.BlockCutting;
import textWriting.Line;
import textWriting.Writing;
import Text.AbstractText;

public class Biggest_Pixel {
	private BufferedImage map;
	private String newMapFile;
	private JProgressBar bar;
	private AbstractText text;
	private int nbStates;
	private boolean allStates;
	private String date;
	private boolean harmonize;
	private int maxTextSize;
	private boolean leftDate;
	private GameFiles gameFiles;
	private String fontName;
	private MapColors mapColors;

	private static int MIN_TEST_SIZE = 15;
	
	public Biggest_Pixel(File mapFile, String newMapFile,
			JProgressBar bar, AbstractText text, int nbStates,
			boolean allStates, String date, boolean harmonize,
			int maxTextSize, boolean proportional, boolean leftDate,
			GameFiles gameFiles, String fontName, MapColors mapColors)
					throws IOException {
		this.bar = bar;
		this.map = ImageIO.read(mapFile);
		this.newMapFile = newMapFile;
		this.text = text;
		this.nbStates = nbStates;
		this.allStates = allStates;
		this.date = date;
		this.harmonize = harmonize;
		if (proportional) {
			this.maxTextSize = Math.min(map.getHeight(), map.getWidth()) *100 
					/ 2048;
		} else {
			this.maxTextSize = maxTextSize;
		}
		this.leftDate = leftDate;
		this.gameFiles = gameFiles;
		this.fontName = fontName;
		this.mapColors = mapColors;
		// To simplify we measure progression by line
		bar.setMaximum(4 * map.getHeight());
		bar.setMinimum(0);
		new Thread(new Algorithm()).run();
	}

	class Algorithm implements Runnable {

		@Override
		public void run() {
			Map<Integer, Integer> states = new HashMap<Integer, Integer>();

			// Counting pixel for each State
			for (int y = 0; y < map.getHeight(); y++) {
				bar.setValue(y);
				for (int x = 0; x < map.getWidth(); x++) {
					// Integer and not int because it can be null
					Integer nbPixels = states.get(0xffffff & map.getRGB(x, y));
					if (nbPixels == null) {
						// First occur of this State
						states.put(0xffffff & map.getRGB(x, y), 1);
					} else {
						// Another pixel of this State
						states.put(0xffffff & map.getRGB(x, y), ++nbPixels);
					}
				}
			}

			// Calculating the nbProvinces to display
			if (!allStates && nbStates > states.size() - mapColors.getFalseStateColorNumber()) {
				throw new IllegalArgumentException(text.moreStateThanAvailable());
			}
			if (allStates) {
				nbStates = states.size() - mapColors.getFalseStateColorNumber();
			}
			// Ranking states by decreasing pixel number
			PriorityQueue<State> orderedStates = new PriorityQueue<State>(states.size());
			Set<Integer> stateRGB = states.keySet();
			for (int rgb : stateRGB) {
				orderedStates.offer(new State(rgb, states.get(rgb), harmonize));
			}
			LinkedList<State> stateToDisplay = new LinkedList<State>();
			int foundStates = 0;
			while (foundStates < nbStates) {
				State bigState = orderedStates.poll();
				if (mapColors.isTrueState(bigState.getRGB())) {
					stateToDisplay.addLast(bigState);
					foundStates++;
				}
			}
			// Load lines of the states (must be done before transforming map because of Java bug on map)
			HashMap<Integer, LinkedList<Line>> h = BlockCutting.enumerateLine(map, mapColors);
			/* Transforming the image to the writing image
			 * by putting in white the pixels not in the kingdom to display */
			for (int y = 0; y < map.getHeight(); y++) {
				bar.setValue(map.getHeight() + y);
				for (int x = 0; x < map.getWidth(); x++) {
					// Don't touch to sea pixels and unknown
					if (mapColors.isTrueState(map.getRGB(x, y)) && stateToDisplay
							.indexOf(new State(map.getRGB(x, y) & 0xffffff, 0, harmonize)) == -1) {
						map.setRGB(x, y, mapColors.emptyLandFillColor());
					}
				}
			}

			// Writing text
			bar.setString(text.textWritingMessage());
			// Load texts
			// Indicator for the progression bar
			int displayedStates = 0;
			for (State s : stateToDisplay) {
				displayedStates++;
				bar.setValue(2 * map.getHeight() +
						displayedStates * map.getHeight() / stateToDisplay.size());
				String stateName = gameFiles.getStateName(s.getRGB());
				if (stateName != null) {
					// Loads lines for this state
					LinkedList<Line> state = h.get(s.getRGB());
					if (state == null || state.isEmpty()) {
						// No block to cut
						continue;
					}
					// Blocks for this state
					LinkedList<PriorityQueue<Line>> l = BlockCutting.cutInBlocks(state);
					// Text to write
					Writing w;
					String textToWrite = stateName;
					for (PriorityQueue<Line> p : l) {
						// Calculate optimized writing
						w = new Writing();
						Writing resWriting = new Writing();
						// Separate each words of the text to write
						String[] t = textToWrite.split("[ ]");
						/* Calculating each combination of repartition of
						 * these words by line
						 */
						for (int k = 0; k < (1 << t.length - 1); k++) {
							int j = 1;
							String res = t[0];
							while (j < t.length) {
								if ((k & (1 << (j - 1))) == (1 << (j - 1))) {
									res += "\n";
								} else {
									res += " ";
								}
								res += t[j];
								j++;
							}
							resWriting.calculateWriting(p, res.split("[\n]"), map,
									maxTextSize, false, leftDate, fontName, MIN_TEST_SIZE);
							if (w.getUnVerifiedTextSize() < resWriting.getUnVerifiedTextSize()) {
								// Keep the best combination
								textToWrite = res;
								w = new Writing(resWriting);
							}
						}
						if (w.getTextOriginSolution() != null) {
							// Write text
							Graphics2D g2d = map.createGraphics();
							s.writeText(g2d, w, textToWrite, fontName);
							g2d.dispose();
						}
					}
				}
			}
			// Write the date on the sea
			bar.setString(text.dateWritingMessage());
			LinkedList<Line> dateLines = h.get(mapColors.getWaterColor());
			// Sea blocks
			LinkedList<PriorityQueue<Line>> blocks = BlockCutting.cutInBlocks(dateLines);
			bar.setValue(3 * map.getHeight() + map.getHeight()/2);
			// Searching the higher position of date
			Writing seaW = new Writing();
			int maxDateTextSize = Math.max(3 * maxTextSize / 4, 21);
			int minDateTextSize = maxDateTextSize - 1; // Search directly the maximum size - 1 
			String dateTab[] = {date};
			for (PriorityQueue<Line> p : blocks) {
				Writing resWriting = new Writing();
				resWriting.calculateWriting(p, dateTab, map, maxDateTextSize, true,
						leftDate, fontName, minDateTextSize);
				if (resWriting.getUnVerifiedTextSize() > minDateTextSize) {
					// Keep the first good combination because it is ordered by decreasing y
					seaW = new Writing(resWriting);
					break;
				}
			}
			
			// We were to ambitious on the text size
			if (seaW.getTextOriginSolution() == null) {
				minDateTextSize = 20; // Search directly the maximum size
				for (PriorityQueue<Line> p : blocks) {
					Writing resWriting = new Writing();
					resWriting.calculateWriting(p, dateTab, map, maxDateTextSize, true,
							leftDate, fontName, minDateTextSize);
					if (resWriting.getUnVerifiedTextSize() > minDateTextSize) {
						// Keep the first good combination because it is ordered by decreasing y
						seaW = new Writing(resWriting);
						break;
					}
				}
			}

			if (seaW.getTextOriginSolution() != null) {
				// Write text
				Graphics2D g2d = map.createGraphics();
				g2d.setFont(new Font(fontName, Font.BOLD, seaW.getTextSize() - 1));
				g2d.setColor(new Color(mapColors.getWaterColor() ^ 0xffffff));
				FontRenderContext frc = g2d.getFontRenderContext();
				GlyphVector gv = g2d.getFont().createGlyphVector(frc, date);
				// (0,0) because we need the offset
				Rectangle textRect = gv.getPixelBounds(null, 0, 0);
				g2d.drawString(date,
						seaW.getTextOriginSolution()[0].x - textRect.x,
						seaW.getTextOriginSolution()[0].y);			        
				g2d.dispose();
			}


			// Write image on png file
			try {
				bar.setValue(4 * map.getHeight());
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
