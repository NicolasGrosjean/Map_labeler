package tests;

import input.LandedTitle;

import java.util.LinkedList;

import org.junit.Test;

import junit.framework.TestCase;

public class TestLandedTitle extends TestCase {

	@Test
	public void testSWMHLandedTitleOneStateOneColor() {
		LinkedList<String> list = new LinkedList<String>();
		list.add("landed_titles.txt");
		LandedTitle lt = new LandedTitle(list);
		LinkedList<String> doubleStates = lt.getDouble();
		//System.out.println(doubleStates);
		assertEquals(0, doubleStates.size());
	}

	@Test
	public void testSWMHLandedTitle() {
		LinkedList<String> list = new LinkedList<String>();
		list.add("landed_titles.txt");
		LandedTitle lt = new LandedTitle(list);
		assertEquals("k_papal_state", lt.getStateCode((255 << 16) + (249 << 8) + 198));
		assertEquals("k_orthodox", lt.getStateCode((183 << 16) + (60 << 8) + 155));
		assertEquals("e_sunni", lt.getStateCode((40 << 16) + (160 << 8) + 40));
		assertEquals("e_shiite", lt.getStateCode((60 << 16) + (190 << 8) + 60));
		assertEquals("e_golden_horde", lt.getStateCode((243 << 16) + (180 << 8) + 17));
		assertEquals("e_il-khanate", lt.getStateCode((120 << 16) + (180 << 8) + 20));
		assertEquals("e_mongol_empire", lt.getStateCode((255 << 16) + (255 << 8) + 255));
		assertEquals("e_timurids", lt.getStateCode((120 << 16) + (20 << 8) + 20));
		assertEquals("d_knights_templar", lt.getStateCode((230 << 16) + (230 << 8) + 230));
		//assertEquals("d_teutonic_order", lt.getStateCode((50 << 16) + (50 << 8) + 50)); 
		//assertEquals("d_knights_hospitaler", lt.getStateCode((180 << 16) + (180 << 8) + 180)); 
		assertEquals("d_hashshashin", lt.getStateCode((100 << 16) + (100 << 8) + 0));
		assertEquals("d_bektashi", lt.getStateCode((90 << 16) + (150 << 8) + 0));
		assertEquals("d_jomsvikings", lt.getStateCode((50 << 16) + (50 << 8) + 50));
		assertEquals("d_immortals", lt.getStateCode((180 << 16) + (205 << 8) + 20));
		assertEquals("d_zealots", lt.getStateCode((20 << 16) + (100 << 8) + 255));
		assertEquals("d_holy_sepulchre", lt.getStateCode((120 << 16) + (0 << 8) + 145));
		assertEquals("d_saint_anthony", lt.getStateCode((180 << 16) + (165 << 8) + 70));
		assertEquals("d_saint_addai", lt.getStateCode((180 << 16) + (80 << 8) + 140));
		assertEquals("d_sky_lords", lt.getStateCode((220 << 16) + (200 << 8) + 140));
		assertEquals("d_spirit_guardians", lt.getStateCode((120 << 16) + (120 << 8) + 55));
		assertEquals("d_warriors_perun", lt.getStateCode((100 << 16) + (140 << 8) + 100));
		assertEquals("d_chosen_perkunas", lt.getStateCode((200 << 16) + (45 << 8) + 45));
		assertEquals("d_sons_kaleva", lt.getStateCode((140 << 16) + (130 << 8) + 105));
		assertEquals("d_huitzilopochtli", lt.getStateCode((255 << 16) + (200 << 8) + 0));
		assertEquals("d_knights_santiago", lt.getStateCode((255 << 16) + (130 << 8) + 0));
		assertEquals("d_knights_calatrava", lt.getStateCode((230 << 16) + (100 << 8) + 0));
		//assertEquals("d_followers_arjuna", lt.getStateCode((255 << 16) + (0 << 8) + 0));
		assertEquals("d_ashokas_chosen", lt.getStateCode((205 << 16) + (100 << 8) + 0));
		assertEquals("d_bulls_rishabha", lt.getStateCode((255 << 16) + (100 << 8) + 0));
		assertEquals("d_sunni_turkic_company", lt.getStateCode((130 << 16) + (173 << 8) + 70));
		assertEquals("d_sunni_cuman_company", lt.getStateCode((134 << 16) + (155 << 8) + 30));
		assertEquals("d_sunni_berber_company", lt.getStateCode((85 << 16) + (155 << 8) + 33));
		assertEquals("d_sunni_bedouin_company", lt.getStateCode((45 << 16) + (155 << 8) + 35));
		assertEquals("d_shiite_turkic_company", lt.getStateCode((135 << 16) + (170 << 8) + 60));
		// Others mercenaries ...

		assertEquals("e_hre", lt.getStateCode((224 << 16) + (195 << 8) + 65));
		assertEquals("k_frisia", lt.getStateCode((255 << 16) + (120 << 8) + 0));
		//assertEquals("d_holland", lt.getStateCode((250 << 16) + (150 << 8) + 20));
		assertEquals("c_holland", lt.getStateCode((168 << 16) + (168 << 8) + 168));
		assertEquals("c_zeeland", lt.getStateCode((165 << 16) + (165 << 8) + 165));
		assertEquals("d_utrecht", lt.getStateCode((17 << 16) + (104 << 8) + 8));
		assertEquals("c_sticht", lt.getStateCode((156 << 16) + (156 << 8) + 156));
		assertEquals("c_oversticht", lt.getStateCode((159 << 16) + (160 << 8) + 161));
		assertEquals("d_gelre", lt.getStateCode((64 << 16) + (105 << 8) + 139));
		// Others duchies and counties

		assertEquals("k_upper_lorraine", lt.getStateCode((250 << 16) + (150 << 8) + 20));
		assertEquals("k_lower_lorraine", lt.getStateCode((255 << 16) + (204 << 8) + 20));
		assertEquals("k_saxony", lt.getStateCode((78 << 16) + (98 << 8) + 40));
		//assertEquals("k_franconia", lt.getStateCode((130 << 16) + (100 << 8) + 100));
		assertEquals("k_bavaria", lt.getStateCode((56 << 16) + (67 << 8) + 158));
		assertEquals("k_carinthia", lt.getStateCode((165 << 16) + (255 << 8) + 250));
		assertEquals("k_schwaben", lt.getStateCode((57 << 16) + (142 << 8) + 157));
		assertEquals("k_bohemia", lt.getStateCode((176 << 16) + (110 << 8) + 32));
		assertEquals("k_germany", lt.getStateCode((130 << 16) + (100 << 8) + 100));
		assertEquals("k_lotharingia", lt.getStateCode((144 << 16) + (80 << 8) + 60));
		assertEquals("e_roman_empire", lt.getStateCode((167 << 16) + (10 << 8) + 100));
		assertEquals("e_byzantium", lt.getStateCode((154 << 16) + (39 << 8) + 197));
		assertEquals("k_byzantium", lt.getStateCode((217 << 16) + (59 << 8) + 59));
		assertEquals("k_epirus", lt.getStateCode((60 << 16) + (126 << 8) + 200));
		assertEquals("k_nikaea", lt.getStateCode((160 << 16) + (81 << 8) + 9));
		assertEquals("k_anatolia", lt.getStateCode((201 << 16) + (81 << 8) + 9));
		assertEquals("k_cyprus", lt.getStateCode((248 << 16) + (246 << 8) + 137));
		assertEquals("k_trebizond", lt.getStateCode((100 << 16) + (151 << 8) + 33));
		assertEquals("k_cilician", lt.getStateCode((255 << 16) + (249 << 8) + 189));
		assertEquals("k_bulgaria", lt.getStateCode((113 << 16) + (88 << 8) + 101));
		assertEquals("k_serbia", lt.getStateCode((113 << 16) + (98 << 8) + 61));
		assertEquals("k_ragusa", lt.getStateCode((102 << 16) + (33 << 8) + 88));
		assertEquals("k_armenia", lt.getStateCode((172 << 16) + (41 << 8) + 75));
		assertEquals("d_suenik", lt.getStateCode((47 << 16) + (17 << 8) + 85));
		assertEquals("d_vaspurakan", lt.getStateCode((174 << 16) + (23 << 8) + 122));
		assertEquals("d_armenia", lt.getStateCode((88 << 16) + (87 << 8) + 52));
		assertEquals("d_tao", lt.getStateCode((210 << 16) + (204 << 8) + 50));
		assertEquals("d_mesopotamia", lt.getStateCode((30 << 16) + (118 << 8) + 113));
		//assertEquals("e_hungary", lt.getStateCode((150 << 16) + (51 << 8) + 51)); // d_pest -> normal
		assertEquals("k_wallachia", lt.getStateCode((238 << 16) + (192 << 8) + 69));
		assertEquals("k_moldau", lt.getStateCode((176 << 16) + (0 << 8) + 0));
		//assertEquals("k_hungary", lt.getStateCode((187 << 16) + (70 << 8) + 70)); // k_magyar -> normal
		//assertEquals("k_bosnia", lt.getStateCode((0 << 16) + (86 << 8) + 148)); // k_france -> normal
		assertEquals("k_croatia", lt.getStateCode((184 << 16) + (184 << 8) + 0));
		//assertEquals("", lt.getStateCode(( << 16) + ( << 8) + ));
	}
}
