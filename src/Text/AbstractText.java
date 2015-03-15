package Text;

public abstract class AbstractText {
	
	/**
	 * Message in the JProgressBar when the text is written
	 * @return
	 */
	public abstract String textWritingMessage();

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
	
	/* ERRORS */
	/**
	 * Bad number of arguments to run the program
	 * USELESS in fact
	 * @return
	 */
	public abstract String badNumberArguments();
	
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
}
