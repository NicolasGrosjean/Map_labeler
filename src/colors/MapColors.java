package colors;

public abstract class MapColors {
	/**
	 * Return the number of color for false state (example sea, uncolonized land, no-man land)
	 * @return
	 */
	public abstract int getFalseStateColorNumber();

	/**
	 * False if the RGB code correspond to a false state (example sea, uncolonized land, no-man land) 
	 * @param rgb
	 * @return
	 */
	public boolean isTrueState(int rgb) {
		return !isWater(rgb) && !isLandButFalseState(rgb);
	}

	/**
	 * True if the RGB code correspond to water (sea, river) 
	 * @param rgb
	 * @return
	 */
	public boolean isWater(int rgb) {
		return (rgb & 0xffffff) == getWaterColor();
	}

	/**
	 * False if the RGB code correspond to a false state but in land (example uncolonized land, no-man land) 
	 * @param rgb
	 * @return
	 */
	public abstract boolean isLandButFalseState(int rgb);

	/**
	 * Get the RGB code for the water
	 * @return
	 */
	public abstract int getWaterColor();

	/**
	 * The RGB code of the color used to fill the empty land (example the state we don't want to display)
	 * @return
	 */
	public abstract int emptyLandFillColor();
}
