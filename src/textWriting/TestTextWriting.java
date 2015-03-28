package textWriting;

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
import java.util.PriorityQueue;

import javax.imageio.ImageIO;

public class TestTextWriting {
	// RGB code of sea and unknown provinces
	static private int SEA_R = 51;
	static private int SEA_G = 67;
	static private int SEA_B = 85;
	static private int UNKNOWN_R = 38;
	static private int UNKNOWN_G = 38;
	static private int UNKNOWN_B = 38;

	public static void main(String[] args) {
		try {
			File mapFile = new File("tache.png");
			BufferedImage map = ImageIO.read(mapFile);
			HashMap<Integer, LinkedList<Line>> h = BlockCutting.enumerateLine(
					map, (SEA_R << 16) + (SEA_G << 8) + SEA_B,
					(UNKNOWN_R << 16) + (UNKNOWN_G << 8) + UNKNOWN_B);
			LinkedList<Line> state = h.get(0xff0000);
			if (state == null) {
				throw new IllegalArgumentException("No block to cut");
			}
			LinkedList<PriorityQueue<Line>> l = BlockCutting.cutBlocks(state);
			System.out.println("Number of red blocks : " + l.size());
			System.out.println("Red blocks :");
			for (PriorityQueue<Line> p : l) {
				PriorityQueue<Line> tmp = new PriorityQueue<Line>(p);
				while (!tmp.isEmpty()) {
					System.out.print(tmp.poll());
				}
				System.out.println("");
			}
			Writing w = new Writing();
			String[] textToWrite = {"Heiliges","Römisches","Reich"};
			w.calculateWriting(l.getFirst(), textToWrite, map);
			if (w.getTextOrigin() != null) {
				Graphics2D g2d = map.createGraphics();
				g2d.setFont(new Font("Serif", Font.BOLD, w.getTextSize() - 1));		        
				g2d.setColor(new Color(0xff0000 ^ 0xffffff));

				System.out.println("Texte écrit à partir de (" + w.getTextOrigin().x + 
						";" + w.getTextOrigin().y +") et de taille " + (w.getTextSize() - 1));
				FontRenderContext frc = g2d.getFontRenderContext();
				GlyphVector gv = g2d.getFont().createGlyphVector(frc, textToWrite[0]);
				// (0,0) because it is unimportant, we need only offset
				Rectangle textRect = gv.getPixelBounds(null, 0, 0); 
				//System.out.println("Taille du texte : " + textRect.width + ";" + -textRect.y);
				int y = w.getTextOrigin().y;
				int textMaxWidth = Writing.calculateTextWidth(textToWrite, g2d, frc);
				// Decreasing loop because text is written from upper to lower
				for (int k = (textToWrite.length - 1); k >= 0; k--) {
					g2d.drawString(textToWrite[k], w.getTextOrigin().x - textRect.x +
							(textMaxWidth - g2d.getFont().createGlyphVector(frc, textToWrite[k]).
									getPixelBounds(null, 0,0).width) / 2, y);
					y += g2d.getFont().createGlyphVector(frc, textToWrite[k]).
							getPixelBounds(null, 0,0).y;
					if (k > 0) {
						// Height for the i-1th line lower its origin
						y -= g2d.getFont().createGlyphVector(frc, textToWrite[k - 1]).
								getPixelBounds(null, 0,0).height +
								g2d.getFont().createGlyphVector(frc, textToWrite[k - 1]).
								getPixelBounds(null, 0,0).y;
					}
				}
				g2d.dispose();
			} else {
				System.out.println("Ecriture impossible de " + textToWrite);
			}
			ImageIO.write(map, "png", new File("tache_empire.png"));
		} catch (IOException e) {
			System.out.println("file not found!");
			System.exit(1);
		}
	}

}
