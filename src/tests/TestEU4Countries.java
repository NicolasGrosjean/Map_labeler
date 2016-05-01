package tests;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import Text.AbstractText;
import Text.TextFrancais;
import stateNames.EU4Files;

public class TestEU4Countries {
	public static EU4Files files;
	public static EU4Files mAndTFiles;

	@BeforeClass
	public static void SetUp() {
		/* Game parameters */
		String gameDirectory = "C:/Jeux/Steam/SteamApps/common/Europa Universalis IV";
		LinkedList<String> modDirectories = new LinkedList<String>();
		AbstractText text = new TextFrancais();		
		files = new EU4Files(gameDirectory, modDirectories, text);
		modDirectories.add("C:/Users/Nicolas/Documents/Paradox Interactive/Europa Universalis IV/MOD/MEIOUandTaxes1");
		mAndTFiles = new EU4Files(gameDirectory, modDirectories, text);
	}

	@Test
	public void testOneCountryOneColor() {
		LinkedList<String> doubleStates = files.getDoubleCountries();
		//System.out.println(doubleStates);
		Assert.assertEquals(0, doubleStates.size());
	}

	@Test
	public void testCountryColor() {
		Assert.assertEquals("Aachen", files.getIntermediateCountryName((157 << 16) + (51 << 8) + 167));
		Assert.assertEquals("Chimu", files.getIntermediateCountryName((39 << 16) + (123 << 8) + 126));
		// TODO : More examples
	}

	@Test
	public void testTag() {
		Assert.assertEquals("REB", files.getTag("Rebels"));
		Assert.assertEquals("PIR", files.getTag("Pirates"));
		Assert.assertEquals("SWE", files.getTag("Sweden"));
		Assert.assertEquals("ICE", files.getTag("Iceland"));
		Assert.assertEquals("MNS", files.getTag("IRE_Munster"));
		Assert.assertEquals("MIR", files.getTag("Merina"));
		Assert.assertEquals("SKA", files.getTag("Sakalava"));
		Assert.assertEquals("ROM", files.getTag("RomanEmpire"));
		// TODO : More examples
	}

	@Test
	public void testLocalisation() {
		Assert.assertEquals("Adal", files.getStateName("ADA"));
		Assert.assertEquals("Aragon", files.getStateName("ARA"));
		Assert.assertEquals("Malte", files.getStateName("JAI"));
		// TODO : More examples
	}
}
