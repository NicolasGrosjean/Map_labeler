package Text;

public class TextFrancais extends AbstractText {

	@Override
	public String textWritingMessage() {
		return "Ecriture du texte sur l'image, patientez";
	}

	@Override
	public String outputWritingMessage() {
		return "Création de l'image résultat, patientez";
	}

	@Override
	protected String delimitedProvinceMap() {
		return "Carte des plus grands états nommée";
	}
	
	@Override
	protected String isAvailable() {
		return "disponible";
	}

	@Override
	public String badNumberArguments() {
		return "ERROR : Nombre d'arguments incorrect!";
	}

	@Override
	public String wrongArgument(String arg) {
		return "ERROR : " + arg + " est un argument inconnu. "+
				"Les arguments doivent commencer par un '-'";
	}

	@Override
	protected String file() {
		return "fichier";
	}

	@Override
	protected String notFound() {
		return "n'a pas été trouvé!";
	}

	@Override
	public String writingError() {
		return "ERROR : une erreur s'est produite lors de l'écriture de";
	}

	@Override
	public String moreStateThanAvailable() {
		return "ERROR : Plus d'Etat demandés que sur la carte";
	}

	@Override
	public  String missingMapFile() {
		return "ERROR : fichier de la carte manquant";
	}

	@Override
	public  String missingOutMapFile() {
		return "ERROR : fichier de la carte résultat manquant";
	}

	@Override
	public  String missingWaitingImageFile() {
		return "ERROR : fichier de l'image d'attente manquant";
	}

	@Override
	public  String missingLandedTitleFile() {
		return "ERROR : fichier landed title manquant";
	}

	@Override
	public  String missingLocalisationFiles() {
		return "ERROR : fichiers de localisation manquant";
	}

	@Override
	public  String missingFontName() {
		return "ERROR : la nom de la police d'écriture est manquante";
	}

	@Override
	public  String missingStateNumber() {
		return "ERROR : le nombre des plus grands Etats à afficher est manquant";
	}

	@Override
	public  String invalidStateNumber() {
		return "ERROR : un entier strictement positif est attendu après -sta. " +
				"Cet entier est le nombre des plus grands Etats à afficher";
	}

	@Override
	public  String invalidMaxTextSize() {
		return "ERROR : un entier strictement positif est attendu après -size. " +
				"Cet entier est la taille maximale du texte.";
	}

	@Override
	public  String invalidDay() {
		return "ERROR : le numéro du jour présent dans le fichier de la carte est invalide!";
	}

	@Override
	public  String invalidMonth() {
		return "ERROR : le numéro du mois présent dans le fichier de la carte est invalide!";
	}

	@Override
	public  String invalidYear() {
		return "ERROR : le numéro de l'année présent dans le fichier de la carte est invalide!";
	}
}
