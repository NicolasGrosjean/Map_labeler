package input;

import java.util.LinkedList;

import colors.Ck2MapColors;
import colors.EU4MapColors;
import colors.MapColors;
import stateNames.Ck2Files;
import stateNames.Ck2Rank;
import stateNames.EU4Files;
import stateNames.GameFiles;
import Text.AbstractText;
import Text.TextEnglish;
import Text.TextFrancais;

/**
 * Management of the main arguments
 * @author Mouchi
 *
 */
public class MainArguments {
	// Language
	private AbstractText text = null;
	// Files
	private String mapFileName = null;
	private String outFileName = null;
	private String imageFileName = null;
	private GameFiles gameFiles;
	private String gameDirectory = null;
	private LinkedList<String> modDirectories = new LinkedList<String>();
	private MapColors mapColors;
	private boolean ck2 = true;
	// Other parameters
	private String fontName = null;
	private int nbState = 0;
	private boolean allStates = false;
	private int maxTextSize = Integer.MAX_VALUE;
	private boolean proportional = true; // if true -> proportional to map size
	private Ck2Rank rank = null;
	private String overrideFileName = null;
	/* States text color (if true black and white -> harmonized
	 * else it is the opposite color of the state -> multicolor) */
	private boolean harmonize = true;
	// Position of the date on the map
	private boolean leftDate = true;
	// Date format
	private boolean yearOnly = false;
	private boolean textualDate = false;

	public MainArguments() {}

	public MainArguments(String[] args) {

		int i = 0;
		while (i < args.length) {
			switch (args[i]) {
			case "-fr" :
				// French language asked
				text = new TextFrancais();
				break;
			case "-en" :
				// English language asked
				text = new TextEnglish();
				break;
			case "-map" :
				i++;
				if (i < args.length) {
					mapFileName = args[i];
				}
				break;
			case "-out" :
				i++;
				if (i < args.length) {
					outFileName = args[i];
				}
				break;
			case "-img" :
				i++;
				if (i < args.length) {
					imageFileName = args[i];
				}
				break;
			case "-ov" :
				i++;
				if (i < args.length) {
					overrideFileName = args[i];
				}
				break;
			case "-rank" :
				i++;
				if (i < args.length) {
					String sRank = args[i];
					switch (sRank.toLowerCase()) {
					case "count":
						rank = Ck2Rank.COUNT;
						break;
					case "duke":
						rank = Ck2Rank.DUKE;
						break;
					case "king":
						rank = Ck2Rank.KING;
						break;
					case "emperor":
						rank = Ck2Rank.EMPEROR;
						break;
					default:
						if (text == null) {
							throw new IllegalArgumentException("ERROR : language not specified");
						}
						throw new IllegalArgumentException(text.invalidRank());	
					}
				}
				break;
			case "-mod" :
				i++;
				while (i < args.length && args[i].charAt(0) != '-') {
					modDirectories.addLast(args[i]);
					i++;
				}
				i--;
				break;
			case "-gameDir" :
				i++;
				if (i < args.length) {
					gameDirectory = args[i];
				}
				break;
			case "-eu4" :
				ck2 = false;
				break;
			case "-pol" :
				i++;
				if (i < args.length) {
					fontName = args[i];
				}
				break;
			case "-sta" :
				i++;
				if (i < args.length) {
					String sNbState = args[i];
					if (sNbState.charAt(0) == 'a') {
						allStates = true;
					} else {
						try {
							nbState = Integer.parseInt(sNbState);
						} catch (NumberFormatException e) {
							if (text == null) {
								throw new IllegalArgumentException("ERROR : language not specified");
							}
							throw new IllegalArgumentException(text.invalidStateNumber());
						}
					}
				}
				break;
			case "-size" :
				i++;
				if (i < args.length) {
					// The max text size is absolute
					try {
						maxTextSize = Integer.parseInt(args[i]);
						proportional = false;
					} catch (NumberFormatException e) {
						if (text == null) {
							throw new IllegalArgumentException("ERROR : language not specified");
						}
						throw new IllegalArgumentException(text.invalidMaxTextSize());
					}
				}
				break;
			case "-multicolor" :
				harmonize = false;
				break;
			case "-rightDate" :
				leftDate = false;
				break;
			case "-yearOnly" :
				yearOnly = true;
				break;
			case "-textualDate" :
				textualDate = true;
				break;
			default :
				if (text == null) {
					throw new IllegalArgumentException("ERROR : language not specified");
				}
				throw new IllegalArgumentException(text.wrongArgument(args[i]));
			}
			i++;
		}
		
		// Check the needed parameters are here and correct
		checkArgs();

		if (ck2) {
			gameFiles = new Ck2Files(gameDirectory, modDirectories, text, rank);
			mapColors = new Ck2MapColors();
		} else {
			gameFiles = new EU4Files(gameDirectory, modDirectories, text);
			mapColors = new EU4MapColors();
		}		
	}

	private void checkArgs() {
		if (text == null) {
			throw new IllegalArgumentException("ERROR : language not specified");
		}
		if (mapFileName == null) {
			throw new IllegalArgumentException(text.missingMapFile());
		}
		if (outFileName == null) {
			throw new IllegalArgumentException(text.missingOutMapFile());
		}
		if (imageFileName == null) {
			throw new IllegalArgumentException(text.missingWaitingImageFile());
		}
		if (fontName == null) {
			throw new IllegalArgumentException(text.missingFontName());
		}
		if (!allStates && nbState == 0) {
			throw new IllegalArgumentException(text.missingStateNumber());
		} else if (!allStates && nbState < 0) {
			throw new IllegalArgumentException(text.invalidStateNumber());
		}
		if (maxTextSize < 20) {
			throw new IllegalArgumentException(text.invalidMaxTextSize());
		}
	}

	public AbstractText getText() {
		return text;
	}

	public String getMapFileName() {
		return mapFileName;
	}

	public String getOutFileName() {
		return outFileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}	

	public GameFiles getGameFiles() {
		return gameFiles;
	}

	public MapColors getMapColors() {
		return mapColors;
	}

	public String getFontName() {
		return fontName;
	}

	public int getNbState() {
		return nbState;
	}

	public boolean isAllStates() {
		return allStates;
	}

	public int getMaxTextSize() {
		return maxTextSize;
	}

	public String getOverrideFileName() {
		return overrideFileName;
	}

	public boolean isProportional() {
		return proportional;
	}

	public boolean isHarmonize() {
		return harmonize;
	}

	public boolean isLeftDate() {
		return leftDate;
	}

	public boolean isYearOnly() {
		return yearOnly;
	}

	public boolean isTextualDate() {
		return textualDate;
	}


}
