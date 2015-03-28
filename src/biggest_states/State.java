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
	
	public State(int rGB, int size) {
		this.rgb = rGB & 0xffffff;
		this.size = size;
		this.textColor = rgb ^ 0xffffff;
		harmonizeTextColor();
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

	private void harmonizeTextColor() {
		int r = (textColor & 0xff0000) >> 16;
		int g = (textColor & 0xff00) >> 8;
		int b = textColor & 0xff;
		r = selectNeighboor(r);
		g = selectNeighboor(g);
		b = selectNeighboor(b);
		textColor = (r << 16) + (g << 8) + b;
	}

	private int selectNeighboor(int col) {
		if (col < 128) {
			return 0;
		} else {
			return 255;
		}
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
