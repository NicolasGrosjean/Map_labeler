package Text;

public class TextFrancais extends AbstractText {

	@Override
	public String waitingMessage() {
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

}
