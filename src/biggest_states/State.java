package biggest_states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;

import textWriting.Writing;

public class State implements Comparable<State> {
	/**
	 * RGB code of the State on the map
	 */
	private int rgb;

	/**
	 * Size of the State (unit depending of the context)
	 */
	private int size;

	/**
	 * RGB code for the text of the State
	 */
	private int textColor;
	
	public State(int rGB, int size, boolean harmonize) {
		this.rgb = rGB & 0xffffff;
		this.size = size;
		if (harmonize) {
			if (((rGB & 0xff0000) >> 16) > 128 &&
					((rGB & 0xff00) >> 8) > 128 &&
					(rGB & 0xff) > 128) {
				// State has V (in TSV code) upper than 128
				// To maximize contrast, textColor has V=0
				this.textColor = 0x0;
			} else {
				this.textColor = 0xffffff;
			}
		} else {
			this.textColor = rgb ^ 0xffffff;
		}
	}

	public State(State s) {
		this.rgb = s.rgb;
		this.size = s.size;
		this.textColor = s.textColor;
	}

	public void writeText (Graphics2D g2d, Writing w, String textToWrite) {
		g2d.setFont(new Font("Serif", Font.BOLD, w.getTextSize() - 1));
		// Border in white if necessary
		if (textColor == 0xffffff) {
			for (int i = 1; i < 4; i++) {
				writeColorText(g2d, w, textToWrite, i, i);
				writeColorText(g2d, w, textToWrite, -i, i);
				writeColorText(g2d, w, textToWrite, i, -i);
				writeColorText(g2d, w, textToWrite, -i, -i);
			}
		}
		g2d.setColor(new Color(0x0)); // Text in black
		writeColorText(g2d, w, textToWrite, 0, 0);
	}

	private void writeColorText (Graphics2D g2d, Writing w, String textToWrite,
			int dx, int dy) {
		FontRenderContext frc = g2d.getFontRenderContext();
		String [] lineToWrite = textToWrite.split("[\n]");
		// Decreasing loop because text is written from upper to lower
		for (int k = (lineToWrite.length - 1); k >= 0; k--) {
			// Write centered line
			g2d.drawString(lineToWrite[k],
					w.getTextOriginSolution()[lineToWrite.length - 1 - k].x
					- g2d.getFont().createGlyphVector(frc, lineToWrite[k])
					.getPixelBounds(null, 0, 0).x + dx,
					w.getTextOriginSolution()[lineToWrite.length - 1 - k].y
					+ dy);
		}
	}

	public int getRGB() {
		return rgb;
	}

	public int getSize() {
		return size;
	}

	public int getTextColor() {
		return textColor;
	}

	/**
	 * Comparison to have decreasing ordering
	 */
	@Override
	public int compareTo(State k) {
		return k.getSize() - size;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (rgb != other.rgb)
			return false;
		return true;
	}

}
