package tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import biggest_states.Main;
import junit.framework.TestCase;

public class TestArguments extends TestCase {
	// To catch output
	private ByteArrayOutputStream outContent;
	// To restore output
	private final PrintStream originalOutPut = System.out;

	// Separator to compare with \n
	private final String separator = System.getProperty("line.separator");

	// Files
	private final String bmpFileName = "bla_bla_15_9_1066_blabla.bmp";
	private final String pngFileName = "res.png";
	private final String waitingImage = "fond.png";
	private final String gameDirectory = "C:/Jeux/Steam/SteamApps/common/Crusader Kings II";
	private final String modDirectory = "C:/Users/Nicolas/Documents/Paradox Interactive/Crusader Kings II/MOD/Historical Immersion Project";

	@Before
	public void setUp() {
		// Test mod
		Main.TESTMOD = true;
		// Catch output
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() {
		// Restart output
		System.setOut(originalOutPut);
	}

	@Test
	public void testBadArgument() {
		String args[] = {"-fr", "bad"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : " + "bad" + " est un argument inconnu. " +
				"Les arguments doivent commencer par un '-'" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingLanguage() {
		String args[] = {"-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "100", "-pol", "Serif",};
		biggest_states.Main.main(args);
		assertEquals("ERROR : language not specified" + separator,
				outContent.toString());
	}

	@Test
	public void testBadBMP() {
		String args[] = {"-fr", "-map", "bad_" + bmpFileName, "-out", pngFileName,
				"-img", waitingImage,"-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier " + "bad_" + bmpFileName + " n'a pas été trouvé!" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingBMP() {
		String args[] = {"-fr", "-out", pngFileName, "-img", waitingImage,
				"-game", gameDirectory,
				"-mod", modDirectory, "-sta", "10",
				"-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier de la carte manquant" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingWaitingImage() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-game", gameDirectory,
				"-mod", modDirectory, "-sta", "10",
				"-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier de l'image d'attente manquant" + separator,
				outContent.toString());
	}

	@Test
	public void testBadWaitingImage() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", "bad_" + waitingImage,"-game", gameDirectory,
				"-mod", modDirectory, "-sta", "10", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier " + "bad_" + waitingImage + " n'a pas été trouvé!" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingOut() {
		String args[] = {"-fr", "-map", bmpFileName, "-img", waitingImage,
				"-game", gameDirectory,
				"-mod", modDirectory, "-sta", "10",
				"-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier de la carte résultat manquant" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingStateNumber() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : le nombre des plus grands Etats à afficher est manquant" + separator,
				outContent.toString());
	}

	@Test
	public void testBadStateNumber1() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "bad", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : un entier strictement positif est attendu après -sta. " +
				"Cet entier est le nombre des plus grands Etats à afficher" + separator,
				outContent.toString());
	}

	@Test
	public void testBadStateNumber2() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "-1", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : un entier strictement positif est attendu après -sta. " +
				"Cet entier est le nombre des plus grands Etats à afficher" + separator,
				outContent.toString());
	}

	@Test
	public void testBadMaxTextSize1() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "bad", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : un entier supérieur à 20 est attendu après -size. " +
				"Cet entier est la taille maximale du texte." + separator,
				outContent.toString());
	}

	@Test
	public void testBadMaxTextSize2() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "-1", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : un entier supérieur à 20 est attendu après -size. " +
				"Cet entier est la taille maximale du texte." + separator,
				outContent.toString());
	}

	@Test
	public void testBadDay1() {
		String args[] = {"-fr", "-map", "blabla_-1_10_1066_bla_bla", "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : le numéro du jour présent dans le fichier de la carte est invalide!"
				+ separator, outContent.toString());
	}

	@Test
	public void testBadDay2() {
		String args[] = {"-fr", "-map", "blabla_42_10_1066_bla_bla", "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : le numéro du jour présent dans le fichier de la carte est invalide!"
				+ separator, outContent.toString());
	}

	@Test
	public void testBadMonth() {
		String args[] = {"-fr", "-map", "blabla_1_42_1066_bla_bla", "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "100", "-pol", "Serif",
				"-textualDate"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : le numéro du mois présent dans le fichier de la carte est invalide!"
				+ separator, outContent.toString());
	}

	@Test
	public void testBadYear() {
		String args[] = {"-fr", "-map", "blabla_1_10_-42_bla_bla", "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : le numéro de l'année présent dans le fichier de la carte est invalide!"
				+ separator, outContent.toString());
	}

	@Test
	public void testMissingFontName() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", modDirectory,
				"-sta", "10", "-size", "100"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : la nom de la police d'écriture est manquante" + separator,
				outContent.toString());
	}

	@Test
	public void testInvalidDirectoryName() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", "bad",
				"-mod", modDirectory,
				"-sta", "10", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : nom de dossier " + "bad" + " incorrect"
				+ separator, outContent.toString());
	}

	@Test
	public void testInvalidDirectoryName2() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-game", gameDirectory,
				"-mod", "bad",
				"-sta", "10", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : nom de dossier " + "bad" + " incorrect"
				+ separator, outContent.toString());
	}

	@Test
	public void testMissingDirectoryName() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName,
				"-img", waitingImage, "-mod", modDirectory,
				"-sta", "10", "-size", "100", "-pol", "Serif"};
		biggest_states.Main.main(args);
		assertEquals("ERROR : nom de dossier manquant"
				+ separator, outContent.toString());
	}
}
