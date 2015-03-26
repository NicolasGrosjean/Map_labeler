package textWriting;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Writing {
	private int textSize;
	private Point textOrigin;
	private boolean isCalculated = false;

	/**
	 * Calculate the fields textSize and textOrigin for the block
	 * (textOrigin == null) <=> impossible to write text
	 * @param block Block of a state (obtained with BlockCutting.cutBlocks)
	 */
	public void calculateWriting(PriorityQueue<Line> block, String textToWrite,
			BufferedImage map) {
		isCalculated = true;
		textSize = 20;
		textOrigin = null;
		
		Graphics2D g2d = map.createGraphics();
        g2d.setFont(new Font("Serif", Font.BOLD, textSize));       
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = g2d.getFont().createGlyphVector(frc, textToWrite);
        // (0,0) because we need offset from the point
        int textWidth = gv.getPixelBounds(null, 0,0).width;
        int textHeigth = -gv.getPixelBounds(null, 0,0).y;
        /* .y and not .height because we need need distance between the point
         * and the summit of the text */
        
		/* Copying block into a LinkedList in order to have Element access
		 * It MUST be by hand because the copy is made by the iterator
		 * and PriorityQueue iterator give element without order*/
		LinkedList<Line> blockLines = new LinkedList<Line>();
		PriorityQueue<Line> tmp = new PriorityQueue<Line>(block);
		while(!tmp.isEmpty()) {
			blockLines.addLast(tmp.poll());
		}
				
		while (!blockLines.isEmpty()) {
			Line l = blockLines.removeFirst();			
			Point p = new Point(l.getBeginLine()); // candidate for textOrigin
			// the line of p is enough big
			while (textWidth <= l.getEndLine().getX() - p.getX()) {
				boolean textOK = true; // p is a good candidate
				// verify the text can be printed in the upper lines
				int j = 0;
				for (int i = 0; i < textHeigth; i++) {
					Line upperLine = blockLines.get(j);
					// Impose that upperLine is upper than the previous line			
					while (j < blockLines.size() - 1 && 
							upperLine.getBeginLine().getY() > p.getY() - i) {
						upperLine = blockLines.get(++j);
					}
					if (j == blockLines.size() - 1) {
						// Not enough lines in the block for the text
						return; // It is impossible to write text with textSize
					}
					/* Search if a line with the height p.getY() - i can contains
					 * the text */
					while (j < blockLines.size() - 1 && 
							upperLine.getBeginLine().getY() == p.getY() - i) {
						if (p.getX() >= upperLine.getBeginLine().getX() &&
								p.getX() + textWidth <=
								upperLine.getEndLine().getX()) {
							// Found !
							textOK = true;
							break;
						} else {
							textOK = false;
						}							
						upperLine = blockLines.get(++j);	
					}
					if (!textOK) {
						/* Impossible to write the text with textOrigin=p at 
						 * this textSize */
						break;
					}
				}
				if (textOK) {
					// p is the better choice at this moment
					textOrigin = new Point(p);
					// searching a better choice by adding textSize
					g2d.setFont(new Font("Serif", Font.BOLD, ++textSize));
					frc = g2d.getFontRenderContext();
			        gv = g2d.getFont().createGlyphVector(frc, textToWrite);
			        textWidth = gv.getPixelBounds(null, 0,0).width;
			        textHeigth = -gv.getPixelBounds(null, 0,0).y;  
				} else {
					// try another candidate, the next point of the p line
					if (p.getX() < l.getEndLine().getX()) {
						p.translate(1, 0);
					}
				}
			}
		}
	}

	public int getTextSize() {
		if (isCalculated) {
			return textSize;
		} else {
			throw new IllegalAccessError("Wrinting not calculated");
		}
	}

	public Point getTextOrigin() {
		if (isCalculated) {
			return textOrigin;
		} else {
			throw new IllegalAccessError("Wrinting not calculated");
		}
	}
}
