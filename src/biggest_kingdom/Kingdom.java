package biggest_kingdom;

public class Kingdom implements Comparable<Kingdom> {
	/**
	 * RGB code of the Kingdom on the map
	 */
	private int rgb;

	/**
	 * Size of the Kingdom (unit depending of the context)
	 */
	private int size;

	public Kingdom(int rGB, int size) {
		this.rgb = rGB;
		this.size = size;
	}

	public int getRGB() {
		return rgb;
	}

	public int getSize() {
		return size;
	}

	/**
	 * Comparison to have decreasing ordering
	 */
	@Override
	public int compareTo(Kingdom k) {
		return k.getSize() - size;
	}


}
