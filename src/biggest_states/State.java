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


}
