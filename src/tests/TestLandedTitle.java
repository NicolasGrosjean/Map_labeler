package tests;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import stateNames.Ck2LandedTitle;

public class TestLandedTitle {

	@Test
	public void testSWMHLandedTitleOneStateOneColor() {
		LinkedList<String> list = new LinkedList<String>();
		list.add("landed_titles.txt");
		Ck2LandedTitle lt = new Ck2LandedTitle(list);
		LinkedList<String> doubleStates = lt.getDouble();
		//System.out.println(doubleStates);
		Assert.assertEquals(0, doubleStates.size());
	}

	@Test
	public void testSWMHLandedTitle() {
		LinkedList<String> list = new LinkedList<String>();
		list.add("landed_titles.txt");
		Ck2LandedTitle lt = new Ck2LandedTitle(list);
		Assert.assertEquals("k_papal_state", lt.getStateCode((255 << 16) + (249 << 8) + 198));
		Assert.assertEquals("k_orthodox", lt.getStateCode((183 << 16) + (60 << 8) + 155));
		Assert.assertEquals("e_sunni", lt.getStateCode((40 << 16) + (160 << 8) + 40));
		Assert.assertEquals("e_shiite", lt.getStateCode((60 << 16) + (190 << 8) + 60));
		Assert.assertEquals("e_golden_horde", lt.getStateCode((243 << 16) + (180 << 8) + 17));
		Assert.assertEquals("e_il-khanate", lt.getStateCode((120 << 16) + (180 << 8) + 20));
		Assert.assertEquals("e_mongol_empire", lt.getStateCode((255 << 16) + (255 << 8) + 255));
		Assert.assertEquals("e_timurids", lt.getStateCode((120 << 16) + (20 << 8) + 20));
		Assert.assertEquals("d_knights_templar", lt.getStateCode((230 << 16) + (230 << 8) + 230));
		//Assert.assertEquals("d_teutonic_order", lt.getStateCode((50 << 16) + (50 << 8) + 50)); 
		//Assert.assertEquals("d_knights_hospitaler", lt.getStateCode((180 << 16) + (180 << 8) + 180)); 
		Assert.assertEquals("d_hashshashin", lt.getStateCode((100 << 16) + (100 << 8) + 0));
		Assert.assertEquals("d_bektashi", lt.getStateCode((90 << 16) + (150 << 8) + 0));
		Assert.assertEquals("d_jomsvikings", lt.getStateCode((50 << 16) + (50 << 8) + 50));
		Assert.assertEquals("d_immortals", lt.getStateCode((180 << 16) + (205 << 8) + 20));
		Assert.assertEquals("d_zealots", lt.getStateCode((20 << 16) + (100 << 8) + 255));
		Assert.assertEquals("d_holy_sepulchre", lt.getStateCode((120 << 16) + (0 << 8) + 145));
		Assert.assertEquals("d_saint_anthony", lt.getStateCode((180 << 16) + (165 << 8) + 70));
		Assert.assertEquals("d_saint_addai", lt.getStateCode((180 << 16) + (80 << 8) + 140));
		Assert.assertEquals("d_sky_lords", lt.getStateCode((220 << 16) + (200 << 8) + 140));
		Assert.assertEquals("d_spirit_guardians", lt.getStateCode((120 << 16) + (120 << 8) + 55));
		Assert.assertEquals("d_warriors_perun", lt.getStateCode((100 << 16) + (140 << 8) + 100));
		Assert.assertEquals("d_chosen_perkunas", lt.getStateCode((200 << 16) + (45 << 8) + 45));
		Assert.assertEquals("d_sons_kaleva", lt.getStateCode((140 << 16) + (130 << 8) + 105));
		Assert.assertEquals("d_huitzilopochtli", lt.getStateCode((255 << 16) + (200 << 8) + 0));
		Assert.assertEquals("d_knights_santiago", lt.getStateCode((255 << 16) + (130 << 8) + 0));
		Assert.assertEquals("d_knights_calatrava", lt.getStateCode((230 << 16) + (100 << 8) + 0));
		//Assert.assertEquals("d_followers_arjuna", lt.getStateCode((255 << 16) + (0 << 8) + 0));
		Assert.assertEquals("d_ashokas_chosen", lt.getStateCode((205 << 16) + (100 << 8) + 0));
		Assert.assertEquals("d_bulls_rishabha", lt.getStateCode((255 << 16) + (100 << 8) + 0));
		Assert.assertEquals("d_sunni_turkic_company", lt.getStateCode((130 << 16) + (173 << 8) + 70));
		Assert.assertEquals("d_sunni_cuman_company", lt.getStateCode((134 << 16) + (155 << 8) + 30));
		Assert.assertEquals("d_sunni_berber_company", lt.getStateCode((85 << 16) + (155 << 8) + 33));
		Assert.assertEquals("d_sunni_bedouin_company", lt.getStateCode((45 << 16) + (155 << 8) + 35));
		Assert.assertEquals("d_shiite_turkic_company", lt.getStateCode((135 << 16) + (170 << 8) + 60));
		// Others mercenaries ...

		Assert.assertEquals("e_hre", lt.getStateCode((224 << 16) + (195 << 8) + 65));
		Assert.assertEquals("k_frisia", lt.getStateCode((255 << 16) + (120 << 8) + 0));
		//Assert.assertEquals("d_holland", lt.getStateCode((250 << 16) + (150 << 8) + 20));
		Assert.assertEquals("c_holland", lt.getStateCode((168 << 16) + (168 << 8) + 168));
		Assert.assertEquals("c_zeeland", lt.getStateCode((165 << 16) + (165 << 8) + 165));
		Assert.assertEquals("d_utrecht", lt.getStateCode((17 << 16) + (104 << 8) + 8));
		Assert.assertEquals("c_sticht", lt.getStateCode((156 << 16) + (156 << 8) + 156));
		Assert.assertEquals("c_oversticht", lt.getStateCode((159 << 16) + (160 << 8) + 161));
		Assert.assertEquals("d_gelre", lt.getStateCode((64 << 16) + (105 << 8) + 139));
		// Others duchies and counties

		Assert.assertEquals("k_upper_lorraine", lt.getStateCode((250 << 16) + (150 << 8) + 20));
		Assert.assertEquals("k_lower_lorraine", lt.getStateCode((255 << 16) + (204 << 8) + 20));
		Assert.assertEquals("k_saxony", lt.getStateCode((78 << 16) + (98 << 8) + 40));
		//Assert.assertEquals("k_franconia", lt.getStateCode((130 << 16) + (100 << 8) + 100));
		Assert.assertEquals("k_bavaria", lt.getStateCode((56 << 16) + (67 << 8) + 158));
		Assert.assertEquals("k_carinthia", lt.getStateCode((165 << 16) + (255 << 8) + 250));
		Assert.assertEquals("k_schwaben", lt.getStateCode((57 << 16) + (142 << 8) + 157));
		Assert.assertEquals("k_bohemia", lt.getStateCode((176 << 16) + (110 << 8) + 32));
		Assert.assertEquals("k_germany", lt.getStateCode((130 << 16) + (100 << 8) + 100));
		Assert.assertEquals("k_lotharingia", lt.getStateCode((144 << 16) + (80 << 8) + 60));

		Assert.assertEquals("e_roman_empire", lt.getStateCode((167 << 16) + (10 << 8) + 100));

		Assert.assertEquals("e_byzantium", lt.getStateCode((154 << 16) + (39 << 8) + 197));
		Assert.assertEquals("k_byzantium", lt.getStateCode((217 << 16) + (59 << 8) + 59));
		Assert.assertEquals("k_epirus", lt.getStateCode((60 << 16) + (126 << 8) + 200));
		Assert.assertEquals("k_nikaea", lt.getStateCode((160 << 16) + (81 << 8) + 9));
		Assert.assertEquals("k_anatolia", lt.getStateCode((201 << 16) + (81 << 8) + 9));
		Assert.assertEquals("k_cyprus", lt.getStateCode((248 << 16) + (246 << 8) + 137));
		Assert.assertEquals("k_trebizond", lt.getStateCode((100 << 16) + (151 << 8) + 33));
		Assert.assertEquals("k_cilician", lt.getStateCode((255 << 16) + (249 << 8) + 189));
		Assert.assertEquals("k_bulgaria", lt.getStateCode((113 << 16) + (88 << 8) + 101));
		Assert.assertEquals("k_serbia", lt.getStateCode((113 << 16) + (98 << 8) + 61));
		Assert.assertEquals("k_ragusa", lt.getStateCode((102 << 16) + (33 << 8) + 88));

		Assert.assertEquals("k_armenia", lt.getStateCode((172 << 16) + (41 << 8) + 75));
		Assert.assertEquals("d_suenik", lt.getStateCode((47 << 16) + (17 << 8) + 85));
		Assert.assertEquals("d_vaspurakan", lt.getStateCode((174 << 16) + (23 << 8) + 122));
		Assert.assertEquals("d_armenia", lt.getStateCode((88 << 16) + (87 << 8) + 52));
		Assert.assertEquals("d_tao", lt.getStateCode((210 << 16) + (204 << 8) + 50));
		Assert.assertEquals("d_mesopotamia", lt.getStateCode((30 << 16) + (118 << 8) + 113));

		//Assert.assertEquals("e_hungary", lt.getStateCode((150 << 16) + (51 << 8) + 51)); // d_pest -> normal
		Assert.assertEquals("k_wallachia", lt.getStateCode((238 << 16) + (192 << 8) + 69));
		Assert.assertEquals("k_moldau", lt.getStateCode((176 << 16) + (0 << 8) + 0));
		//Assert.assertEquals("k_hungary", lt.getStateCode((187 << 16) + (70 << 8) + 70)); // k_magyar -> normal
		//Assert.assertEquals("k_bosnia", lt.getStateCode((0 << 16) + (86 << 8) + 148)); // k_france -> normal
		Assert.assertEquals("k_croatia", lt.getStateCode((184 << 16) + (184 << 8) + 0));

		Assert.assertEquals("k_jerusalem", lt.getStateCode((180 << 16) + (180 << 8) + 180));
		Assert.assertEquals("d_jerusalem", lt.getStateCode((223 << 16) + (224 << 8) + 199));
		//Assert.assertEquals("d_acre", lt.getStateCode((235 << 16) + (224 << 8) + 73)); // d_foix
		Assert.assertEquals("d_oultrejourdain", lt.getStateCode((203 << 16) + (159 << 8) + 24));
		Assert.assertEquals("d_ascalon", lt.getStateCode((225 << 16) + (65 << 8) + 34));
		Assert.assertEquals("d_galilee", lt.getStateCode((41 << 16) + (45 << 8) + 138));

		Assert.assertEquals("k_antioch", lt.getStateCode((50 << 16) + (76 << 8) + 150));
		Assert.assertEquals("d_edessa", lt.getStateCode((89 << 16) + (21 << 8) + 66));
		Assert.assertEquals("d_antioch", lt.getStateCode((91 << 16) + (139 << 8) + 188));
		Assert.assertEquals("d_tripoli", lt.getStateCode((154 << 16) + (39 << 8) + 39));

		Assert.assertEquals("k_syria", lt.getStateCode((145 << 16) + (130 << 8) + 88));
		Assert.assertEquals("d_aleppo", lt.getStateCode((85 << 16) + (232 << 8) + 20));
		Assert.assertEquals("d_homs", lt.getStateCode((107 << 16) + (75 << 8) + 13));
		Assert.assertEquals("d_damascus", lt.getStateCode((107 << 16) + (13 << 8) + 106));
		Assert.assertEquals("d_jordan", lt.getStateCode((35 << 16) + (232 << 8) + 20));
		Assert.assertEquals("d_syria", lt.getStateCode((107 << 16) + (93 << 8) + 66));

		Assert.assertEquals("k_aljazira", lt.getStateCode((255 << 16) + (2 << 8) + 36));
		Assert.assertEquals("d_mosul", lt.getStateCode((89 << 16) + (36 << 8) + 23));
		Assert.assertEquals("d_jazira", lt.getStateCode((96 << 16) + (115 << 8) + 255));
		Assert.assertEquals("d_diyareast", lt.getStateCode((255 << 16) + (239 << 8) + 63));

		//Assert.assertEquals("e_scandinavia", lt.getStateCode((6 << 16) + (42 << 8) + 127)); // d_hereford
		Assert.assertEquals("k_denmark", lt.getStateCode((224 << 16) + (24 << 8) + 54));
		Assert.assertEquals("k_iceland", lt.getStateCode((0 << 16) + (86 << 8) + 147));
		Assert.assertEquals("k_sweden", lt.getStateCode((62 << 16) + (122 << 8) + 189));
		Assert.assertEquals("k_finland", lt.getStateCode((140 << 16) + (65 << 8) + 65));
		Assert.assertEquals("k_norway", lt.getStateCode((95 << 16) + (4 << 8) + 30));

		Assert.assertEquals("k_pomerania", lt.getStateCode((136 << 16) + (89 << 8) + 151));
		Assert.assertEquals("d_mecklemburg", lt.getStateCode((78 << 16) + (49 << 8) + 36));
		Assert.assertEquals("d_pomeralia", lt.getStateCode((236 << 16) + (234 << 8) + 46));
		Assert.assertEquals("d_lubeck", lt.getStateCode((177 << 16) + (69 << 8) + 69));
		Assert.assertEquals("d_lubusz", lt.getStateCode((78 << 16) + (115 << 8) + 219));
		Assert.assertEquals("d_pommerania", lt.getStateCode((63 << 16) + (92 << 8) + 65));
		Assert.assertEquals("d_brandenburg", lt.getStateCode((229 << 16) + (223 << 8) + 200));
		Assert.assertEquals("d_lausitz", lt.getStateCode((87 << 16) + (87 << 8) + 157));

		Assert.assertEquals("e_poland", lt.getStateCode((179 << 16) + (0 << 8) + 45));
		Assert.assertEquals("k_poland", lt.getStateCode((150 << 16) + (23 << 8) + 23));
		Assert.assertEquals("k_lithuania", lt.getStateCode((92 << 16) + (157 << 8) + 103));
		Assert.assertEquals("k_galicia", lt.getStateCode((10 << 16) + (69 << 8) + 106));

		Assert.assertEquals("k_terra", lt.getStateCode((85 << 16) + (96 << 8) + 84));
		Assert.assertEquals("d_livonia", lt.getStateCode((191 << 16) + (191 << 8) + 191));
		Assert.assertEquals("d_talava", lt.getStateCode((126 << 16) + (162 << 8) + 210));
		Assert.assertEquals("d_courland", lt.getStateCode((41 << 16) + (41 << 8) + 77));
		Assert.assertEquals("d_zemigalia", lt.getStateCode((142 << 16) + (65 << 8) + 139));
		Assert.assertEquals("d_lettigale", lt.getStateCode((125 << 16) + (80 << 8) + 50));
		Assert.assertEquals("d_esthonia", lt.getStateCode((178 << 16) + (184 << 8) + 27));
		Assert.assertEquals("d_dorpat", lt.getStateCode((207 << 16) + (45 << 8) + 45));
		Assert.assertEquals("d_osel_wiek", lt.getStateCode((42 << 16) + (78 << 8) + 48));

		Assert.assertEquals("e_russia", lt.getStateCode((66 << 16) + (99 << 8) + 19));
		Assert.assertEquals("k_vladimir", lt.getStateCode((24 << 16) + (78 << 8) + 39));
		Assert.assertEquals("k_polotsk", lt.getStateCode((119 << 16) + (35 << 8) + 100));
		Assert.assertEquals("k_ruthenia", lt.getStateCode((220 << 16) + (183 << 8) + 61));
		//Assert.assertEquals("k_novgorod", lt.getStateCode((224 << 16) + (224 << 8) + 224)); // d_novgorod

		Assert.assertEquals("k_taurica", lt.getStateCode((90 << 16) + (150 << 8) + 175));
		Assert.assertEquals("d_crimea", lt.getStateCode((226 << 16) + (186 << 8) + 80));
		Assert.assertEquals("d_sarkel", lt.getStateCode((236 << 16) + (200 << 8) + 105));
		Assert.assertEquals("d_olvia", lt.getStateCode((231 << 16) + (210 << 8) + 153));
		Assert.assertEquals("d_azov", lt.getStateCode((218 << 16) + (179 << 8) + 130));

		Assert.assertEquals("k_khazaria", lt.getStateCode((250 << 16) + (184 << 8) + 31));
		Assert.assertEquals("d_itil", lt.getStateCode((151 << 16) + (118 << 8) + 30));
		Assert.assertEquals("d_sibir", lt.getStateCode((210 << 16) + (185 << 8) + 15));
		Assert.assertEquals("d_yaik", lt.getStateCode((198 << 16) + (150 << 8) + 11));
		Assert.assertEquals("d_turkestan", lt.getStateCode((224 << 16) + (170 << 8) + 24));

		//Assert.assertEquals("k_volga_bulgaria", lt.getStateCode((255 << 16) + (255 << 8) + 153)); // d_bulgar
		Assert.assertEquals("d_bulgar", lt.getStateCode((255 << 16) + (255 << 8) + 153));
		Assert.assertEquals("d_syrt", lt.getStateCode((75 << 16) + (40 << 8) + 105));
		//Assert.assertEquals("d_mordva", lt.getStateCode((243 << 16) + (183 << 8) + 22)); // c_burtasy
		Assert.assertEquals("d_cheremisa", lt.getStateCode((225 << 16) + (165 << 8) + 4));

		Assert.assertEquals("k_perm", lt.getStateCode((63 << 16) + (102 << 8) + 207));
		Assert.assertEquals("d_hlynov", lt.getStateCode((134 << 16) + (102 << 8) + 207));
		Assert.assertEquals("d_perm", lt.getStateCode((234 << 16) + (208 << 8) + 137));

		//Assert.assertEquals("e_persia", lt.getStateCode((51 << 16) + (255 << 8) + 204)); // k_persia
		Assert.assertEquals("k_persia", lt.getStateCode((51 << 16) + (255 << 8) + 204));
		Assert.assertEquals("k_khiva", lt.getStateCode((39 << 16) + (138 << 8) + 51));
		Assert.assertEquals("k_daylam", lt.getStateCode((50 << 16) + (178 << 8) + 240));
		Assert.assertEquals("k_mesopotamia", lt.getStateCode((55 << 16) + (128 << 8) + 67));

		Assert.assertEquals("k_georgia", lt.getStateCode((122 << 16) + (26 << 8) + 98));
		Assert.assertEquals("d_kartli", lt.getStateCode((184 << 16) + (6 << 8) + 6));
		Assert.assertEquals("d_kakheti", lt.getStateCode((196 << 16) + (131 << 8) + 131));
		Assert.assertEquals("d_albania", lt.getStateCode((236 << 16) + (132 << 8) + 31));
		Assert.assertEquals("d_derbent", lt.getStateCode((97 << 16) + (147 << 8) + 60));
		Assert.assertEquals("d_abkhazia", lt.getStateCode((184 << 16) + (203 << 8) + 170));

		//Assert.assertEquals("k_alania", lt.getStateCode((155 << 16) + (110 << 8) + 48)); // d_alania
		Assert.assertEquals("d_alania", lt.getStateCode((155 << 16) + (110 << 8) + 48));

		Assert.assertEquals("e_italy", lt.getStateCode((51 << 16) + (124 << 8) + 91));
		Assert.assertEquals("k_italy", lt.getStateCode((69 << 16) + (135 << 8) + 81));
		Assert.assertEquals("d_lombardia", lt.getStateCode((133 << 16) + (82 << 8) + 150));
		Assert.assertEquals("d_como", lt.getStateCode((50 << 16) + (123 << 8) + 57));
		//Assert.assertEquals("d_verona", lt.getStateCode((0 << 16) + (86 << 8) + 148)); // k_france
		Assert.assertEquals("d_parma", lt.getStateCode((140 << 16) + (200 << 8) + 213));
		Assert.assertEquals("d_susa", lt.getStateCode((0 << 16) + (66 << 8) + 145));
		//Assert.assertEquals("d_monferrato", lt.getStateCode((104 << 16) + (12 << 8) + 38)); // c_bugey
		Assert.assertEquals("d_genoa", lt.getStateCode((255 << 16) + (194 << 8) + 10));
		Assert.assertEquals("d_savona", lt.getStateCode((140 << 16) + (55 << 8) + 81));
		Assert.assertEquals("d_modena", lt.getStateCode((45 << 16) + (102 << 8) + 59));
		Assert.assertEquals("d_mantua", lt.getStateCode((225 << 16) + (180 << 8) + 180));
		Assert.assertEquals("d_lucca", lt.getStateCode((85 << 16) + (100 << 8) + 130));
		Assert.assertEquals("d_toscana", lt.getStateCode((241 << 16) + (193 << 8) + 26));
		Assert.assertEquals("d_pisa", lt.getStateCode((108 << 16) + (30 << 8) + 40));
		Assert.assertEquals("k_patrimoniumpetri", lt.getStateCode((171 << 16) + (30 << 8) + 30));
		Assert.assertEquals("d_ferrara", lt.getStateCode((123 << 16) + (189 << 8) + 123));
		Assert.assertEquals("d_ravenna", lt.getStateCode((134 << 16) + (76 << 8) + 145));
		Assert.assertEquals("d_ancona", lt.getStateCode((148 << 16) + (118 << 8) + 0));
		Assert.assertEquals("d_urbino", lt.getStateCode((0 << 16) + (85 << 8) + 148));
		Assert.assertEquals("d_spoleto", lt.getStateCode((135 << 16) + (45 << 8) + 49));
		Assert.assertEquals("d_latium", lt.getStateCode((254 << 16) + (245 << 8) + 160));
		Assert.assertEquals("k_sardinia", lt.getStateCode((72 << 16) + (96 << 8) + 176));
		Assert.assertEquals("d_corsica", lt.getStateCode((144 << 16) + (184 << 8) + 115));
		Assert.assertEquals("d_sardinia", lt.getStateCode((132 << 16) + (171 << 8) + 207));
		Assert.assertEquals("k_sicily", lt.getStateCode((170 << 16) + (170 << 8) + 210));
		Assert.assertEquals("d_benevento", lt.getStateCode((87 << 16) + (66 << 8) + 44));
		Assert.assertEquals("d_napoli", lt.getStateCode((196 << 16) + (139 << 8) + 77));
		Assert.assertEquals("d_capua", lt.getStateCode((138 << 16) + (226 << 8) + 138));
		Assert.assertEquals("d_apulia", lt.getStateCode((102 << 16) + (130 << 8) + 128));
		Assert.assertEquals("d_loritello", lt.getStateCode((96 << 16) + (192 << 8) + 140));
		Assert.assertEquals("d_tarent", lt.getStateCode((227 << 16) + (77 << 8) + 77));
		Assert.assertEquals("d_salerno", lt.getStateCode((138 << 16) + (28 << 8) + 28));
		Assert.assertEquals("d_alvito", lt.getStateCode((193 << 16) + (229 << 8) + 253));
		Assert.assertEquals("d_calabria", lt.getStateCode((195 << 16) + (161 << 8) + 44));
		Assert.assertEquals("d_sicily", lt.getStateCode((43 << 16) + (97 << 8) + 251));
		Assert.assertEquals("k_venice", lt.getStateCode((52 << 16) + (127 << 8) + 128));

		//Assert.assertEquals("e_france", lt.getStateCode((74 << 16) + (133 << 8) + 186)); // d_bourdon
		Assert.assertEquals("k_burgundy", lt.getStateCode((134 << 16) + (0 << 8) + 37));
		Assert.assertEquals("d_savoie", lt.getStateCode((113 << 16) + (166 << 8) + 111));
		Assert.assertEquals("d_aosta", lt.getStateCode((120 << 16) + (170 << 8) + 220));
		Assert.assertEquals("d_arles", lt.getStateCode((243 << 16) + (209 << 8) + 23));
		Assert.assertEquals("d_provence", lt.getStateCode((92 << 16) + (127 << 8) + 157));
		Assert.assertEquals("d_lyon", lt.getStateCode((128 << 16) + (0 << 8) + 40));
		Assert.assertEquals("d_dauphine", lt.getStateCode((75 << 16) + (50 << 8) + 50));
		Assert.assertEquals("d_basel", lt.getStateCode((195 << 16) + (183 << 8) + 210));
		Assert.assertEquals("d_upper_burgundy", lt.getStateCode((104 << 16) + (2 << 8) + 94));
		Assert.assertEquals("k_brittany", lt.getStateCode((240 << 16) + (240 << 8) + 240));
		Assert.assertEquals("d_lowerbrittany", lt.getStateCode((210 << 16) + (210 << 8) + 200));
		Assert.assertEquals("d_brittany", lt.getStateCode((210 << 16) + (170 << 8) + 210));
		Assert.assertEquals("k_france", lt.getStateCode((0 << 16) + (86 << 8) + 148));
		Assert.assertEquals("k_aquitaine", lt.getStateCode((54 << 16) + (50 << 8) + 226));

		Assert.assertEquals("e_iberia", lt.getStateCode((250 << 16) + (201 << 8) + 20));
		Assert.assertEquals("k_castille", lt.getStateCode((188 << 16) + (150 << 8) + 0));
		Assert.assertEquals("k_navarra", lt.getStateCode((225 << 16) + (22 << 8) + 22));
		//Assert.assertEquals("k_leon", lt.getStateCode((142 << 16) + (53 << 8) + 124)); // c_inse_gall
		Assert.assertEquals("k_aragon", lt.getStateCode((255 << 16) + (237 << 8) + 120));
		Assert.assertEquals("k_portugal", lt.getStateCode((207 << 16) + (160 << 8) + 255));
		Assert.assertEquals("k_andalusia", lt.getStateCode((140 << 16) + (186 << 8) + 128));
		Assert.assertEquals("k_spanish_galicia", lt.getStateCode((43 << 16) + (185 << 8) + 186));
		//Assert.assertEquals("k_catalunya", lt.getStateCode((200 << 16) + (0 << 8) + 50)); // k_granada
		//Assert.assertEquals("k_valencia", lt.getStateCode((200 << 16) + (0 << 8) + 50)); // k_granada
		Assert.assertEquals("k_granada", lt.getStateCode((200 << 16) + (0 << 8) + 50));
		Assert.assertEquals("k_asturias", lt.getStateCode((49 << 16) + (88 << 8) + 185));

		Assert.assertEquals("e_africa", lt.getStateCode((204 << 16) + (1 << 8) + 1));
		Assert.assertEquals("k_africa", lt.getStateCode((255 << 16) + (204 << 8) + 102));
		Assert.assertEquals("d_tunis", lt.getStateCode((116 << 16) + (116 << 8) + 177));
		Assert.assertEquals("d_qamuda", lt.getStateCode((116 << 16) + (177 << 8) + 156));
		Assert.assertEquals("d_tripolitania", lt.getStateCode((227 << 16) + (227 << 8) + 200));
		Assert.assertEquals("d_kabylia", lt.getStateCode((201 << 16) + (21 << 8) + 38));
		Assert.assertEquals("d_zab", lt.getStateCode((61 << 16) + (21 << 8) + 38));
		Assert.assertEquals("k_maghreb", lt.getStateCode((155 << 16) + (89 << 8) + 155));
		Assert.assertEquals("d_alger", lt.getStateCode((215 << 16) + (113 << 8) + 31));
		Assert.assertEquals("d_tlemcen", lt.getStateCode((128 << 16) + (113 << 8) + 31));
		Assert.assertEquals("d_tangiers", lt.getStateCode((128 << 16) + (113 << 8) + 120));
		Assert.assertEquals("d_fes", lt.getStateCode((128 << 16) + (18 << 8) + 73));
		Assert.assertEquals("d_marrakech", lt.getStateCode((25 << 16) + (95 << 8) + 73));
		Assert.assertEquals("k_mauretania", lt.getStateCode((64 << 16) + (0 << 8) + 128));
		Assert.assertEquals("d_draa", lt.getStateCode((128 << 16) + (95 << 8) + 73));
		//Assert.assertEquals("d_adrar", lt.getStateCode((130 << 16) + (180 << 8) + 61)); // d_mzab
		Assert.assertEquals("d_taghaza", lt.getStateCode((92 << 16) + (188 << 8) + 163));

		Assert.assertEquals("k_canarias", lt.getStateCode((182 << 16) + (198 << 8) + 176));
		//Assert.assertEquals("d_canarias", lt.getStateCode((24 << 16) + (58 << 8) + 121)); // d_galloway
		Assert.assertEquals("d_madeira", lt.getStateCode((213 << 16) + (196 << 8) + 1));

		Assert.assertEquals("e_arabia", lt.getStateCode((21 << 16) + (128 << 8) + 0));
		Assert.assertEquals("k_arabia", lt.getStateCode((45 << 16) + (169 << 8) + 12));
		Assert.assertEquals("k_egypt", lt.getStateCode((65 << 16) + (36 << 8) + 40));
		Assert.assertEquals("d_sinai", lt.getStateCode((100 << 16) + (138 << 8) + 41));

		Assert.assertEquals("e_abyssinia", lt.getStateCode((181 << 16) + (133 << 8) + 180));
		Assert.assertEquals("k_adal", lt.getStateCode((160 << 16) + (0 << 8) + 40));
		Assert.assertEquals("k_abyssinia", lt.getStateCode((201 << 16) + (143 << 8) + 68));
		Assert.assertEquals("k_nubia", lt.getStateCode((161 << 16) + (172 << 8) + 86));
		Assert.assertEquals("k_sennar", lt.getStateCode((200 << 16) + (48 << 8) + 110));
		//Assert.assertEquals("k_maikelebahr", lt.getStateCode((255 << 16) + (67 << 8) + 20)); //k_blemmyes

		Assert.assertEquals("e_britannia", lt.getStateCode((135 << 16) + (6 << 8) + 42));
		Assert.assertEquals("k_england", lt.getStateCode((191 << 16) + (16 << 8) + 16));
		Assert.assertEquals("k_wales", lt.getStateCode((135 << 16) + (25 << 8) + 3));
		Assert.assertEquals("d_gwynedd", lt.getStateCode((99 << 16) + (92 << 8) + 79));
		Assert.assertEquals("d_deheubarth", lt.getStateCode((125 << 16) + (51 << 8) + 3));
		Assert.assertEquals("d_powys", lt.getStateCode((88 << 16) + (33 << 8) + 64));
		Assert.assertEquals("d_morgannwg", lt.getStateCode((201 << 16) + (111 << 8) + 23));
		Assert.assertEquals("k_ireland", lt.getStateCode((3 << 16) + (49 << 8) + 35));
		Assert.assertEquals("d_oneill", lt.getStateCode((70 << 16) + (106 << 8) + 21));
		Assert.assertEquals("d_ulster", lt.getStateCode((248 << 16) + (237 << 8) + 39));
		Assert.assertEquals("d_airgialla", lt.getStateCode((30 << 16) + (180 << 8) + 20));
		Assert.assertEquals("d_breifne", lt.getStateCode((54 << 16) + (135 << 8) + 39));
		Assert.assertEquals("d_connacht", lt.getStateCode((70 << 16) + (123 << 8) + 126));
		Assert.assertEquals("d_meath", lt.getStateCode((106 << 16) + (70 << 8) + 126));
		Assert.assertEquals("d_thomond", lt.getStateCode((30 << 16) + (100 << 8) + 80));
		Assert.assertEquals("d_ossory", lt.getStateCode((60 << 16) + (200 << 8) + 10));
		Assert.assertEquals("d_leinster", lt.getStateCode((51 << 16) + (95 << 8) + 40));
		Assert.assertEquals("d_munster", lt.getStateCode((17 << 16) + (93 << 8) + 112));
		Assert.assertEquals("k_scotland", lt.getStateCode((214 << 16) + (143 << 8) + 0));
		Assert.assertEquals("k_ellan_vannin", lt.getStateCode((60 << 16) + (60 << 8) + 118));
		Assert.assertEquals("d_ellan_vannin", lt.getStateCode((90 << 16) + (126 << 8) + 96));
		Assert.assertEquals("d_hebrides", lt.getStateCode((128 << 16) + (34 << 8) + 34));
		Assert.assertEquals("d_orkney", lt.getStateCode((102 << 16) + (51 << 8) + 93));
		Assert.assertEquals("d_western_isles", lt.getStateCode((32 << 16) + (48 << 8) + 99));

		Assert.assertEquals("e_mali", lt.getStateCode((223 << 16) + (139 << 8) + 40));
		Assert.assertEquals("k_ghana", lt.getStateCode((90 << 16) + (130 << 8) + 30));
		//Assert.assertEquals("k_mali", lt.getStateCode((158 << 16) + (188 << 8) + 61)); // c_mali
		Assert.assertEquals("k_takrur", lt.getStateCode((46 << 16) + (180 << 8) + 70));
		Assert.assertEquals("k_songhay", lt.getStateCode((85 << 16) + (125 << 8) + 120));
		Assert.assertEquals("k_sahara", lt.getStateCode((196 << 16) + (176 << 8) + 46));
		Assert.assertEquals("k_fezzan", lt.getStateCode((110 << 16) + (105 << 8) + 60));
		Assert.assertEquals("k_kanem", lt.getStateCode((255 << 16) + (119 << 8) + 112));
		Assert.assertEquals("k_hausaland", lt.getStateCode((220 << 16) + (58 << 8) + 200));		
	}
}
