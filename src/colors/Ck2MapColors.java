package colors;

public class Ck2MapColors extends MapColors {
	// RGB code of sea and no-man land provinces
	static private int SEA_R = 51;
	static private int SEA_G = 67;
	static private int SEA_B = 85;
	static private int NOMANLAND_R = 38;
	static private int NOMANLAND_G = 38;
	static private int NOMANLAND_B = 38;
	// Color to fill state to not display
	static private int EMPTYLANDFILLCOLOR = 0xffffff;

	@Override
	public int getFalseStateColorNumber() {
		return 2; // Sea water + border/no-man lands
	}

	@Override
	public boolean isLandButFalseState(int rgb) {
		return rgb == ((NOMANLAND_R << 16) + (NOMANLAND_G << 8) + NOMANLAND_B);
	}

	@Override
	public int getWaterColor() {
		return (SEA_R << 16) + (SEA_G << 8) + SEA_B;
	}

	@Override
	public int emptyLandFillColor() {
		return EMPTYLANDFILLCOLOR;
	}

}
