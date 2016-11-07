package stateNames;

/**
 * Rank in Ck2 for which we have color in the map.
 * The Rank has also a code to identify the title rank.
 * @author Nicolas
 *
 */
public enum Ck2Rank {
	COUNT("c_"),
	DUKE("d_"),
	KING("k_"),
	EMPEROR("e_");
	
	private final String code;
	
	private Ck2Rank(String code) {
		this.code = code;
	}

	public String toString(){
		return code;
	}
}
