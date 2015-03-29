package biggest_states;

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
