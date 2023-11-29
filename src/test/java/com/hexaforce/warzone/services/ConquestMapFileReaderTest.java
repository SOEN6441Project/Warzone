package com.hexaforce.warzone.services;

import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test suite aimed at evaluating the parsing functionality of conquest map files.
 */
public class ConquestMapFileReaderTest {

	MapService d_mapservice; // Reference to MapService to store its object.
	List<String> d_mapLines; // Reference to store lines of the Map.
	Map d_map; // Reference to store the Map object.
	GameContext d_context; // Reference to store the GameContext object.
	ConquestMapFileReader d_conquestMapFileReader; // Conquest file reader to parse the map file.

	/**
	 * Setup method executed before each MapService operation.
	 *
	 * @throws InvalidMap Thrown for an invalid map exception.
	 */
	@Before
	public void setup() throws InvalidMap {
		d_conquestMapFileReader = new ConquestMapFileReader();
		d_mapservice = new MapService();
		d_map = new Map();
		d_context = new GameContext();
		d_mapLines = d_mapservice.loadFile("testconquest1.map");
	}

	/**
	 * Test to evaluate the reading functionality of conquest maps.
	 *
	 * @throws IOException Thrown for an I/O exception.
	 * @throws InvalidMap  Thrown for an invalid map exception.
	 */
	@Test
	public void testReadConquestFile() throws IOException, InvalidMap {
		d_conquestMapFileReader.readConquestMapFile(d_context, d_map, d_mapLines);

		assertNotNull(d_context.getD_map());
		assertEquals(d_context.getD_map().getD_continents().size(), 8);
		assertEquals(d_context.getD_map().getD_countries().size(), 99);
	}

	/**
	 * Test for adding or deleting continents via the editcontinent operation.
	 *
	 * @throws IOException      Thrown for I/O exceptions.
	 * @throws InvalidMap       Thrown for invalid map exceptions.
	 * @throws InvalidCommand   Thrown for invalid command exceptions.
	 */
	@Test
	public void testEditMap() throws IOException, InvalidMap, InvalidCommand {
		d_conquestMapFileReader.readConquestMapFile(d_context, d_map, d_mapLines);
		Map l_updatedContinents = d_mapservice.addRemoveContinents(d_context, d_context.getD_map(), "Add", "Asia 10");
		l_updatedContinents = d_mapservice.addRemoveContinents(d_context, d_context.getD_map(), "Add", "Europe 20");

		assertEquals(l_updatedContinents.getD_continents().size(), 10);
		assertEquals(l_updatedContinents.getD_continents().get(8).getD_continentName(), "Asia");
		assertEquals(l_updatedContinents.getD_continents().get(8).getD_continentValue().toString(), "10");

		l_updatedContinents = d_mapservice.addRemoveContinents(d_context, d_context.getD_map(), "Remove", "Asia");
		assertEquals(l_updatedContinents.getD_continents().size(), 9);

		d_mapservice.editFunctions(d_context, "add", "Germany Europe", 2);
		d_mapservice.editFunctions(d_context, "add", "Netherlands Europe", 2);
		assertEquals(d_context.getD_map().getD_countries().size(), 101);

		d_mapservice.editFunctions(d_context, "remove", "Netherlands", 2);
		assertEquals(d_context.getD_map().getD_countries().size(), 100);
	}
}
