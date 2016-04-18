package tests;

import java.io.File;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import Text.AbstractText;
import Text.TextFrancais;
import stateNames.EU4Countries;
import stateNames.EU4DirectoryReader;
import stateNames.EU4Files;
import stateNames.EU4Localisation;
import stateNames.EU4Tags;
import stateNames.GameFiles.TimeFileComparator;

public class TestEU4Countries {
	private static EU4Countries countries;
	private static EU4Tags tags;
	private static EU4Localisation localisation;

	@BeforeClass
	public static void SetUp() {
		/* Game parameters */
		String gameDirectory = "C:/Jeux/Steam/SteamApps/common/Europa Universalis IV";
		LinkedList<String> modDirectories = new LinkedList<String>();
		AbstractText text = new TextFrancais();
//		modDirectories.add("C:/Users/Nicolas/Documents/Paradox Interactive/Crusader Kings II/MOD/Historical Immersion Project");
		EU4Files files = new EU4Files(gameDirectory, modDirectories, text);
		
		// Read the directories and sort the files
		PriorityQueue<File> countryFiles = new PriorityQueue<File>(20, files.new TimeFileComparator());
		PriorityQueue<File> tagFiles = new PriorityQueue<File>(20, files.new TimeFileComparator());
		PriorityQueue<File> localisationFiles = new PriorityQueue<File>(20, files.new TimeFileComparator());
		EU4DirectoryReader.readAndSortDirectoryFiles(gameDirectory, text,
				countryFiles, tagFiles, localisationFiles);
		while (!modDirectories.isEmpty()) {
			EU4DirectoryReader.readAndSortDirectoryFiles(modDirectories.removeFirst(),
					text, countryFiles, tagFiles, localisationFiles);
		}
		
		// Transform Files priority queues list into String list
		LinkedList<String> countryFileNames = new LinkedList<String>();
		while (!countryFiles.isEmpty()) {
			countryFileNames.addLast(countryFiles.remove().toString());
		}
		LinkedList<String> tagFileNames = new LinkedList<String>();
		while (!tagFiles.isEmpty()) {
			tagFileNames.addLast(tagFiles.remove().toString());
		}
		LinkedList<String> localisationFileNames = new LinkedList<String>();
		while (!localisationFiles.isEmpty()) {
			localisationFileNames.addLast(localisationFiles.remove().toString());
		}
		if (localisationFileNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingCountryFiles());
		}
		
		// Final storage of the useful information
		countries = new EU4Countries(countryFileNames);
		tags = new EU4Tags(tagFileNames);
		localisation = new EU4Localisation(localisationFileNames);
	}

	@Test
	public void testOneCountryOneColor() {
		LinkedList<String> doubleStates = countries.getDouble();
		//System.out.println(doubleStates);
		Assert.assertEquals(0, doubleStates.size());
	}

	@Test
	public void testCountryColor() {
		Assert.assertEquals("Aachen", countries.getIntermediateCountryName((157 << 16) + (51 << 8) + 167));
		Assert.assertEquals("Chimu", countries.getIntermediateCountryName((39 << 16) + (123 << 8) + 126));
		// TODO : More examples
	}

	@Test
	public void testTag() {
		Assert.assertEquals("REB", tags.getTag("Rebels"));
		Assert.assertEquals("PIR", tags.getTag("Pirates"));
		Assert.assertEquals("SWE", tags.getTag("Sweden"));
		Assert.assertEquals("ICE", tags.getTag("Iceland"));
		Assert.assertEquals("MNS", tags.getTag("IRE_Munster"));
		Assert.assertEquals("MIR", tags.getTag("Merina"));
		Assert.assertEquals("SKA", tags.getTag("Sakalava"));
		Assert.assertEquals("ROM", tags.getTag("RomanEmpire"));
		// TODO : More examples
	}

	@Test
	public void testLocalisation() {
		Assert.assertEquals("Adal", localisation.getStateName("ADA"));
		Assert.assertEquals("Aragon", localisation.getStateName("ARA"));
		Assert.assertEquals("Malte", localisation.getStateName("JAI"));
		// TODO : More examples
	}
}
