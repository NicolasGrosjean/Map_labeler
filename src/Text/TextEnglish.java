package Text;

public class TextEnglish extends AbstractText {

	@Override
	public String textWritingMessage() {
		return "Writing text on image, please wait";
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

	@Override
	public String badNumberArguments() {
		return "ERROR : Incorrect arguments number!";
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
}
