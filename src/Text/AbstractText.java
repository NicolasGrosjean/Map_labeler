package Text;

public abstract class AbstractText {

	/**
	 * Return if the language is the French language
	 * @return
	 */
	public abstract boolean isFrench();

	/**
	 * Message in the JProgressBar when the text is written
	 * @return
	 */
	public abstract String textWritingMessage();

	/**
	 * Message in the JProgressBar when the date is written
	 * @return
	 */
	public abstract String dateWritingMessage();

	/**
	 * Message in the JProgressBar when the output file is written
	 * @return
	 */
	public abstract String outputWritingMessage();

	/**
	 * Begging of the end of program sentence
	 * @return
	 */
	protected abstract String delimitedProvinceMap();
	
	/**
	 * Print is available
	 * @return
	 */
	protected abstract String isAvailable();
	
	/**
	 * Message in the console to avert that the output file is available
	 * @param delimitedProvinceMapName Output file name
	 * @return
	 */
	public String endMessage(String delimitedProvinceMapName) {
		return delimitedProvinceMap() + " " + delimitedProvinceMapName +
				" " + isAvailable();
	}

	/* --------------------------- DIALOG ----------------------------------- */
	/**
	 * Translation of the word yes
	 * @return
	 */
	public abstract String yes();

	/**
	 * Translation of the word no
	 * @return
	 */
	public abstract String no();

	/**
	 * Translation of the word cancel
	 * @return
	 */
	public abstract String cancel();

	/**
	 * Title of the replacement confirmation dialog box
	 * @return
	 */
	public abstract String confirmReplacementTitle();

	/**
	 * Message of the replacement confirmation dialog box
	 * @return
	 */
	public abstract String warningReplacementMessage(String outFileName);

	/**
	 * Title of the choice another output file name dialog box
	 * @return
	 */
	public abstract String anotherChoiceTitle();

	/**
	 * Message of the choice another output file name dialog box
	 * @return
	 */
	public abstract String anotherChoiceMessage();
	
	/**
	 * Error when exit the confirmation dialog box
	 * @return
	 */
	public abstract String exitDialog();

	/* --------------------------- ERRORS ----------------------------------- */
	/**
	 * Bad number of arguments to run the program
	 * USELESS in fact
	 * @return
	 */
	public abstract String badNumberArguments();

	/**
	 * Bad argument to run the program
	 * @param arg
	 * @return
	 */
	public abstract String wrongArgument(String arg);

	/**
	 * Word file
	 * @return
	 */
	protected abstract String file();
	
	/**
	 * Expression "was not found"
	 * @return
	 */
	protected abstract String notFound();
	
	/**
	 * File : file was not found
	 * @param file
	 * @return
	 */
	public String fileNotFound(String file) {
		return "ERROR : " + file() + " " + file + " " + notFound();
	}
	
	/**
	 * Error by writing the output file
	 * @return
	 */
	public abstract String writingError();

	/**
	 * Error if the number of states to keep is higher than available
	 * @return
	 */
	public abstract String moreStateThanAvailable();

	/**
	 * The name of the map file which will be decorated is missing
	 * @return
	 */
	public abstract String missingMapFile();

	/**
	 * The name of the out file is missing
	 * @return
	 */
	public abstract String missingOutMapFile();

	/**
	 * The name of the waiting image file is missing
	 * @return
	 */
	public abstract String missingWaitingImageFile();

	/**
	 * The name of the landed title file is missing
	 * @return
	 */
	public abstract String missingLandedTitleFile();

	/**
	 * The name of the country files is missing
	 * @return
	 */
	public abstract String missingCountryFiles();

	/**
	 * The name of the country files is missing
	 * @return
	 */
	public abstract String missingTagFiles();

	/**
	 * The name of a localisation file is missing
	 * @return
	 */
	public abstract String missingLocalisationFiles();

	/**
	 * The font name for the text is missing
	 * @return
	 */
	public abstract String missingFontName();

	/**
	 * The number of biggest states to display is missing
	 * @return
	 */
	public abstract String missingStateNumber();

	/**
	 * The number of biggest states to display is invalid
	 * @return
	 */
	public abstract String invalidStateNumber();

	/**
	 * The maximum text size is invalid
	 * @return
	 */
	public abstract String invalidMaxTextSize();

	/**
	 * The day of the date is invalid
	 * @return
	 */
	public abstract String invalidDay();

	/**
	 * The month of the date is invalid
	 * @return
	 */
	public abstract String invalidMonth();

	/**
	 * The year of the date is invalid
	 * @return
	 */
	public abstract String invalidYear();

	/**
	 * The directory name is not valid
	 * @param directoryName
	 * @return
	 */
	public abstract String invalidDirectoryName(String directoryName);

	/**
	 * The directory name is missing
	 * @return
	 */
	public abstract String missingDirectoryName();
}
