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

	/**
	 * textOrigin[i] = origin of the text for the ith lower line
	 */
	private Point [] textOrigin;

	/**
	 * textOriginSolution[i] = Solution of the origin of the text
	 * for the ith lower line
	 */
	private Point[] textOriginSolution;

	/**
	 * calculateWriting was called
	 */
	private boolean isCalculated = false;

	/**
	 * textWidth[i] = width of the text for the ith lower line
	 */
	private int [] textWidth;

	/**
	 * textHeight[i] = height of the text for the ith lower line
	 */
	private int [] textHeight;

	public Writing() {
	}

	public Writing(Writing w) {
		this.textSize = w.textSize;
		this.textOrigin = w.textOrigin;
		this.textOriginSolution = w.textOriginSolution;
		this.isCalculated = w.isCalculated;
		this.textWidth = w.textWidth;
		this.textHeight = w.textHeight;
	}

	/**
	 * Calculate the fields textSize and textOrigin for the block
	 * (textOrigin == null) <=> impossible to write text
	 * N.B : The text can exceed the State because of 2 things
	 *  - we don't check first block line for each text line except the lower
	 *  	it is pathological case, impossible in practice
	 *  - we don't check the text under textOrigin[0]
	 * @param block Block of a state (obtained with BlockCutting.cutBlocks)
	 * @param textToWrite Text to write by line, first element represent the upper line
	 * @param map Image for size text calculation (the image in which text will be writing)
	 */
	public void calculateWriting(PriorityQueue<Line> block, String[] textToWrite,
			BufferedImage map, int maxTextSize, boolean date, boolean leftDate,
			String fontName) {
		isCalculated = true;
		textSize = 20;
		textOrigin = new Point[textToWrite.length];

		// Variable for text size maximum limit
		Point addSolution = new Point(0, 0); // add of valid textOrigin[0] except first
		LinkedList<Point> solutions = new LinkedList<Point>();
		// list of valid textOrigin[0] except first

		Graphics2D g2d = map.createGraphics();
		g2d.setFont(new Font(fontName, Font.BOLD, textSize));
		FontRenderContext frc = g2d.getFontRenderContext();
		textWidth = new int[textToWrite.length];
		calculateTextWidth(textToWrite, g2d, frc);
		textHeight = new int[textToWrite.length];
		calculateTextHeight(textToWrite, g2d, frc);

		int solutionNumber = 0;
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
			textOrigin[0] = new Point(l.getBeginLine());
			calculateTextOrigin(textToWrite, g2d, frc);
			// the line of textOrigin[0] is enough big
			while (textWidth[0] <= l.getEndLine().x - textOrigin[0].x) {
				boolean textOK = true; // textOrigin[0] is a good candidate
				// verify the text can be printed in the upper lines of the blocks
				// For each text line
				for (int k = 0; k < textToWrite.length; k++) {
					int j = -1;
					// For each block line for this text line
					for (int i = 0; i < textHeight[k]; i++) {
						if (blockLines.size() == 0) {
							// Not enough lines in the block for the text
							return; // It is impossible to write text with textSize
						}
						Line upperLine;
						if (j == -1) {
							upperLine = l;
						} else {
							upperLine = blockLines.get(j);
						}
						// Impose that upperLine is upper than the previous line			
						while (j < blockLines.size() - 1 && 
								upperLine.getBeginLine().y != textOrigin[k].y - i) {
							upperLine = blockLines.get(++j);
						}
						if (j == blockLines.size() - 1) {
							// Not enough lines in the block for the text
							return; // It is impossible to write text with textSize
						}
						/* Search if a line with the height p.getY() - i can contains
						 * the text */
						while (j < blockLines.size() - 1 && 
								upperLine.getBeginLine().y == textOrigin[k].y - i) {
							if (textOrigin[k].x >= upperLine.getBeginLine().x &&
									textOrigin[k].x + textWidth[k] <=
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
							/* Impossible to write the text with
							 * this textOrigin[0] and textSize */
							break;
						}
					}
					if (!textOK) {
						/* Impossible to write the text with
						 * this textOrigin[0] and textSize */
						break;
					}
				}
				if (textOK) {
					if (textSize < maxTextSize) {
						// textOrigin[0] is the better choice at this moment
						saveSolution();
						// searching a better choice by adding textSize
						g2d.setFont(new Font(fontName, Font.BOLD, ++textSize));
						frc = g2d.getFontRenderContext();
						calculateTextWidth(textToWrite, g2d, frc);
						calculateTextHeight(textToWrite, g2d, frc);
					} else if (solutionNumber == 0) {
						// textOrigin[0] is the better choice at this moment
						if (!date || !leftDate || textOriginSolution != null &&								
								textOriginSolution[0].y > textOrigin[0].y) {
							// For a date in left, we save only if it is higher
							// Else always save
							saveSolution();
						}
						// Now searching other solution of this size
						nextCandidate(l);
						// For the date we store only the last solution
						if (!date) {
							// For State we search to center
							solutionNumber++;
						}
					} else {
						// Another solution found
						solutionNumber++;
						addSolution.x += textOrigin[0].x;
						addSolution.y += textOrigin[0].y;
						solutions.add(new Point(textOrigin[0]));
						// searching other solution of this size
						nextCandidate(l);
					}
				} else {
					// try another candidate, the next point of the line
					nextCandidate(l);
				}
			}
		}
		if (solutionNumber > 1) {
			int meanX = (addSolution.x + textOriginSolution[0].x) /
					solutionNumber;
			int meanY = (addSolution.y + textOriginSolution[0].y) /
					solutionNumber;
			// Search in the solution list the nearest Point
			nearestSolution(solutions, meanX, meanY);
			// Calculate textOrigin[i] for i>0
			calculateTextOrigin(textToWrite, g2d, frc);
			// Save final result
			saveSolution();
			// Add 1 to textSize because subtract 1 when we draw
			textSize++;
		}
		if (date && textSize == maxTextSize) {
			// Add 1 to textSize because subtract 1 when we draw
			textSize++;
		}
	}

	private void calculateTextWidth(String[] textLines, Graphics2D g2d, FontRenderContext frc) {
		for (int i = 0; i < textLines.length; i++) {
			textWidth[i] = g2d.getFont().createGlyphVector(frc,
					textLines[textLines.length - 1 - i]).
					getPixelBounds(null, 0,0).width;
		}
	}

	private void calculateTextHeight(String[] textLines, Graphics2D g2d, FontRenderContext frc) {
		for (int i = 0; i < textLines.length; i++) {
			textHeight[i] = - g2d.getFont().createGlyphVector(frc,
					textLines[textLines.length - 1 - i]).
					getPixelBounds(null, 0,0).y;
		}
	}

	/**
	 * Calculate textOrigin[i] for i > 0 with textOrigin[0]
	 * Required textOrigin[0] and textWidth known/calculated
	 * @param textLines
	 * @param g2d
	 * @param frc
	 * @return
	 */
	private void calculateTextOrigin(String[] textLines, Graphics2D g2d, FontRenderContext frc) {
		for (int i = 1; i < textLines.length; i++) {
			textOrigin[i] = new Point();
			textOrigin[i].x = textOrigin[0].x - (textWidth[i] - textWidth[0]) / 2;
			GlyphVector gvI = g2d.getFont().createGlyphVector(frc, textLines[i]);
			textOrigin[i].y = textOrigin[i - 1].y // origin of the lower line
					+ g2d.getFont().createGlyphVector(frc, textLines[i - 1]).
					getPixelBounds(null, 0,0).y  // height of the lower line compared to its origin
					- calculateMargin(g2d, frc) // margin
					- gvI.getPixelBounds(null, 0,0).y
					- gvI.getPixelBounds(null, 0,0).height; // height under its origin of the ith lower line
		}
	}

	private int calculateMargin(Graphics2D g2d, FontRenderContext frc) {
		return g2d.getFontMetrics().getHeight() -
				g2d.getFont().createGlyphVector(frc, "hy").
				getPixelBounds(null, 0,0).height;
	}

	private void saveSolution() {
		textOriginSolution = new Point[textOrigin.length];
		for (int i = 0; i < textOrigin.length; i++) {
			textOriginSolution[i] = new Point(textOrigin[i].x, textOrigin[i].y);
		}
	}

	private void nextCandidate (Line l) {
		if (textOrigin[0].x < l.getEndLine().x) {
			for (int i = 0; i < textOrigin.length; i++) {
				textOrigin[i].translate(1, 0);
			}
		}
	}

	private void nearestSolution(LinkedList<Point> solutions, int meanX, int meanY) {
		double dist = Math.sqrt((textOrigin[0].x - meanX) * (textOrigin[0].x - meanX) +
				(textOrigin[0].y - meanY) * (textOrigin[0].y - meanY));
		while (!solutions.isEmpty()) {
			Point p = solutions.removeFirst();
			double pDist = Math.sqrt((p.x - meanX) * (p.x - meanX) +
					(p.y - meanY) * (p.y - meanY));
			if (pDist < dist) {
				dist = pDist;
				textOrigin[0].x = p.x;
				textOrigin[0].y = p.y;
			}
		}
	}

	public int getTextSize() {
		if (isCalculated) {
			return textSize;
		} else {
			throw new IllegalAccessError("Writing not calculated");
		}
	}

	public Point[] getTextOriginSolution() {
			return textOriginSolution;
	}

	/**
	 * Get the text but do not verify it is the result of a calculation
	 * @return
	 */
	public int getUnVerifiedTextSize() {
		return textSize;
	}

	/**
	 * Get the text but do not verify it is the result of a calculation
	 * @return
	 */
	public int getTextOriginSolutionYOfLowerWord() {
		if (isCalculated && textOriginSolution != null) {
			return textOriginSolution[0].y;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	public int[] getTextWidth() {
		return textWidth;
	}

	public int[] getTextHeight() {
		return textHeight;
	}
}
