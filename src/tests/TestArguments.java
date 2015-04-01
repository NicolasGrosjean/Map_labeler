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
	private final String loc1 = "text1.csv";
	private final String loc2 = "text2.csv";
	private final String loc3 = "v1_06.csv";
	private final String loc4 = "India.csv";
	private final String loc5 = "DuchiesKingdomsandEmpires de jure.csv";
	private final String loc6 = "DuchiesKingdomsandEmpires titular.csv";

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
		assertEquals("ERROR : " + "bad" + " est un argument inconnu." +
				"Les arguments doivent commencer par un '-'" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingLanguage() {
		String args[] = {"-map", bmpFileName, "-out", pngFileName, "-img", waitingImage};
		biggest_states.Main.main(args);
		assertEquals("ERROR : language not specified" + separator,
				outContent.toString());
	}

	@Test
	public void testBadBMP() {
		String args[] = {"-fr", "-map", "bad_" + bmpFileName, "-out", pngFileName, "-img", waitingImage};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier " + "bad_" + bmpFileName + " n'a pas été trouvé!" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingBMP() {
		String args[] = {"-fr", "-out", pngFileName, "-img", waitingImage};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier de la carte manquant" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingWaitingImage() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier de l'image d'attente manquant" + separator,
				outContent.toString());
	}

	@Test
	public void testBadWaitingImage() {
		String args[] = {"-fr", "-map", bmpFileName, "-out", pngFileName, "-img", "bad_" + waitingImage};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier " + "bad_" + waitingImage + " n'a pas été trouvé!" + separator,
				outContent.toString());
	}

	@Test
	public void testMissingOut() {
		String args[] = {"-fr", "-map", bmpFileName, "-img", waitingImage};
		biggest_states.Main.main(args);
		assertEquals("ERROR : fichier de la carte résultat manquant" + separator,
				outContent.toString());
	}
}
