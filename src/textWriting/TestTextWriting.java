package textWriting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;

import colors.Ck2MapColors;

public class TestTextWriting {
	public static void main(String[] args) {
		try {
			File mapFile = new File("tache.png");
			BufferedImage map = ImageIO.read(mapFile);
			HashMap<Integer, LinkedList<Line>> h = BlockCutting.enumerateLine(
					map, new Ck2MapColors());
			LinkedList<Line> state = h.get(0xff0000);
			if (state == null) {
				throw new IllegalArgumentException("No block to cut");
			}
			LinkedList<PriorityQueue<Line>> l = BlockCutting.cutInBlocks(state);
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
			String[] textToWrite = {"Heiliges", "Heiliges", "Römisches", "Reich", "Reich"};
			w.calculateWriting(l.getFirst(), textToWrite, map, 100, false, true, "Serif", 20);
			if (w.getTextOriginSolution() != null) {
				Graphics2D g2d = map.createGraphics();
				g2d.setFont(new Font("Serif", Font.BOLD, w.getTextSize() - 1));		        
				g2d.setColor(new Color(0xff0000 ^ 0xffffff));

				System.out.println("Texte écrit à partir de (" + w.getTextOriginSolution()[0].x +
						";" + w.getTextOriginSolution()[0].y +") et de taille " + (w.getTextSize() - 1));
				FontRenderContext frc = g2d.getFontRenderContext();
				// Decreasing loop because text is written from upper to lower
				for (int k = (textToWrite.length - 1); k >= 0; k--) {
					g2d.drawString(textToWrite[k],
							w.getTextOriginSolution()[textToWrite.length - 1 - k].x
							- g2d.getFont().createGlyphVector(frc, textToWrite[k])
							.getPixelBounds(null, 0, 0).x,
							w.getTextOriginSolution()[textToWrite.length - 1 - k].y);
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
