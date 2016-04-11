package colors;

public class EU4MapColors extends MapColors {
	// RGB code of sea and no-man land provinces
	static private int SEA_R = 68;
	static private int SEA_G = 107;
	static private int SEA_B = 163;
	static private int NOMANLAND_R = 94;
	static private int NOMANLAND_G = 94;
	static private int NOMANLAND_B = 94;
	static private int UNCOLONIZED_R = 150;
	static private int UNCOLONIZED_G = 150;
	static private int UNCOLONIZED_B = 150;
	// Color to fill state to not display
	static private int EMPTYLANDFILLCOLOR = (UNCOLONIZED_R << 16) + (UNCOLONIZED_G << 8) + UNCOLONIZED_B;
		
	@Override
	public int getFalseStateColorNumber() {
		return 3;
	}

	@Override
	public boolean isLandButFalseState(int rgb) {
		return ((rgb & 0xffffff) == ((NOMANLAND_R << 16) + (NOMANLAND_G << 8) + NOMANLAND_B) ||
				(rgb & 0xffffff) == ((UNCOLONIZED_R << 16) + (UNCOLONIZED_G << 8) + UNCOLONIZED_B));
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
