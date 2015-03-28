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
	private int textSize = 0;
	private Point textOrigin;
	private boolean isCalculated = false;


	public Writing() {
	}

	public Writing(Writing w) {
		this.textSize = w.textSize;
		this.textOrigin = w.textOrigin;
		this.isCalculated = w.isCalculated;
	}

	/**
	 * Calculate the fields textSize and textOrigin for the block
	 * (textOrigin == null) <=> impossible to write text
	 * @param block Block of a state (obtained with BlockCutting.cutBlocks)
	 * @param textToWrite Text to write by line, first element represent the upper line
	 * @param map Image for size text calculation (the image in which text will be writing)
	 */
	public void calculateWriting(PriorityQueue<Line> block, String[] textToWrite,
			BufferedImage map) {
		isCalculated = true;
		textSize = 20;
		textOrigin = null;
		
		Graphics2D g2d = map.createGraphics();
        g2d.setFont(new Font("Serif", Font.BOLD, textSize));       
        FontRenderContext frc = g2d.getFontRenderContext();
        int textWidth = Writing.calculateTextWidth(textToWrite, g2d, frc);
        int textHeigth = Writing.calculateTextHeight(textToWrite, g2d, frc);
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
			        textWidth = Writing.calculateTextWidth(textToWrite, g2d, frc);
			        textHeigth = Writing.calculateTextHeight(textToWrite, g2d, frc);
				} else {
					// try another candidate, the next point of the p line
					if (p.getX() < l.getEndLine().getX()) {
						p.translate(1, 0);
					}
				}
			}
		}
	}

	public static int calculateTextWidth (String[] textLines, Graphics2D g2d, FontRenderContext frc) {
		// (0,0) because we need offset from the point
		int textWidth = g2d.getFont().createGlyphVector(frc, textLines[0]).
				getPixelBounds(null, 0,0).width;
		for (int i = 1; i < textLines.length; i++) {
			int lineWidth = g2d.getFont().createGlyphVector(frc, textLines[i]).
					getPixelBounds(null, 0,0).width;
			if (textWidth < lineWidth) {
				textWidth = lineWidth;
			}
		}
		return textWidth;
	}

	private static int calculateTextHeight (String[] textLines, Graphics2D g2d, FontRenderContext frc) {
		int textHeight = 0;
		// Decreasing loop because text is written from upper to lower
		for (int i = (textLines.length - 1); i >= 0; i--) {
			// Height for the ith line upper its origin
			textHeight -= g2d.getFont().createGlyphVector(frc, textLines[i]).
					getPixelBounds(null, 0,0).y;
			if (i > 0) {
				// Height for the i-1th line lower its origin
				textHeight += g2d.getFont().createGlyphVector(frc, textLines[i - 1]).
						getPixelBounds(null, 0,0).height +
						g2d.getFont().createGlyphVector(frc, textLines[i - 1]).
						getPixelBounds(null, 0,0).y;
			}
		}
		return textHeight;
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

	/**
	 * Get the text but do not verify it is the result of a calculation
	 * @return
	 */
	public int getUnVerifiedTextSize() {
		return textSize;
	}
}
