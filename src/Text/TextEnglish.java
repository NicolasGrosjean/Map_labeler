package Text;

public class TextEnglish extends AbstractText {

	@Override
	public String textWritingMessage() {
		return "Writing text on image, please wait";
	}

	@Override
	public String dateWritingMessage() {
		return "Writing date on image, please wait";
	}

	@Override
	public String outputWritingMessage() {
		return "Writing on the output file, please wait";
	}

	@Override
	protected String delimitedProvinceMap() {
		return "The map with biggest states named";
	}
	
	@Override
	protected String isAvailable() {
		return "is available";
	}

	/* --------------------------- DIALOG ----------------------------------- */
	public String yes() {
		return "Yes";
	}

	public String no() {
		return "No";
	}

	public String cancel() {
		return "Cancel";
	}

	public String confirmReplacementTitle() {
		return "Confirm replacement";
	}

	public String warningReplacementMessage(String outFileName) {
		return outFileName + " already exists. Do you want replace it ?";
	}

	public String anotherChoiceTitle() {
		return "New choice";
	}

	public String anotherChoiceMessage() {
		return "Chose another output file name";
	}

	public String exitDialog() {
		return "Program exited by leaving dialog box";
	}

	/* --------------------------- ERRORS ----------------------------------- */
	@Override
	public String badNumberArguments() {
		return "ERROR : Incorrect arguments number!";
	}

	@Override
	public String wrongArgument(String arg) {
		return "ERROR : " + arg + " is unknown argument. "+
				"The arguments must start with '-'";
	}

	@Override
	protected String file() {
		return "file";
	}

	@Override
	protected String notFound() {
		return "was not found!";
	}

	@Override
	public String writingError() {
		return "ERROR : an error occured during writing on";
	}

	@Override
	public String moreStateThanAvailable() {
		return "ERROR : More states asked than there is on the map";
	}

	@Override
	public  String missingMapFile() {
		return "ERROR : missing map file";
	}

	@Override
	public  String missingOutMapFile() {
		return "ERROR : missing out map file";
	}

	@Override
	public  String missingWaitingImageFile() {
		return "ERROR : missing waiting image file";
	}

	@Override
	public  String missingLandedTitleFile() {
		return "ERROR : missing landed title file";
	}

	@Override
	public  String missingLocalisationFiles() {
		return "ERROR : missing localisation file";
	}

	@Override
	public  String missingFontName() {
		return "ERROR : missing font name";
	}

	@Override
	public  String missingStateNumber() {
		return "ERROR : missing the number of biggest States to display";
	}

	@Override
	public  String invalidStateNumber() {
		return "ERROR : a positive integer is waited after -sta. " +
				"It is the number of biggest States to display.";
	}

	@Override
	public  String invalidMaxTextSize() {
		return "ERROR : an integer greater than 20 is waited after -size. " +
				"It is the maximum text size.";
	}

	@Override
	public  String invalidDay() {
		return "ERROR : incorrect day number on map file name!";
	}

	@Override
	public  String invalidMonth() {
		return "ERROR : incorrect month number on map file name!";
	}

	@Override
	public  String invalidYear() {
		return "ERROR : incorrect year number on map file name!";
	}

	@Override
	public String invalidDirectoryName(String directoryName) {
		return "ERROR : invalid directory name" + directoryName;
	}

	@Override
	public String missingDirectoryName() {
		return "ERROR : missing directory name";
	}
}
