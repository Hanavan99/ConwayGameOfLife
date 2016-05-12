package com.github.hanavan99.conwaygameoflife.simulator;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.GameView;
import com.github.hanavan99.conwaygameoflife.model.Player;

public class GameSimulatorTest {
	private static final boolean debug = false;
	private static PrintStream debugStream;

	@BeforeClass
	public static void setup() throws IOException {
		if ( debug ) {
			debugStream = new PrintStream(new File("debug.log"));
		}
	}

	@AfterClass
	public static void cleanup() {
		if ( debug ) {
			debugStream.close();
		}
	}

	private void printField(Game game) {
		GameView view = new GameView(game);
		Rectangle size = view.totalSizeExact();
		debugStream.printf("(%d, %d) -> (%d, %d):\n", (int) size.getMinX(), (int) size.getMinY(), (int) size.getMaxX(),
				(int) size.getMaxY());
		for ( int y = (int) size.getMinY(); y <= size.getMaxY(); ++y ) {
			for ( int x = (int) size.getMinX(); x <= size.getMaxX(); ++x ) {
				debugStream.print(view.getTilePlayer(x, y) == null ? ' ' : '#');
			}
			debugStream.println();
		}
	}

	private Chunk getChunk(String name, String player, int generation, Point location) throws IOException {
		try ( InputStream in = getClass().getResourceAsStream(
				String.format("/simulatorTests/%s/%d/%s/%d,%d", name, generation, player, location.x, location.y))) {
			if ( in == null ) {
				return null;
			}
			try ( DataInputStream data = new DataInputStream(in)) {
				long[] raw = new long[64];
				for ( int i = 0; i < 64; ++i ) {
					raw[i] = data.readLong();
				}
				return new Chunk(new Player(player, Color.BLACK, 0, 0), location.x, location.y, raw, generation);
			}
		}
	}

	private List<Chunk> getChunks(String name, List<String> players, Rectangle locations, int generation)
			throws IOException {
		List<Chunk> chunks = new ArrayList<Chunk>();
		for ( String player : players ) {
			for ( int x = (int) locations.getMinX(); x <= locations.getMaxX(); ++x ) {
				for ( int y = (int) locations.getMinY(); y <= locations.getMaxY(); ++y ) {
					Chunk chunk = getChunk(name, player, generation, new Point(x, y));
					if ( chunk != null ) {
						chunks.add(chunk);
					}
				}
			}
		}
		return chunks;
	}

	private void test(String name, int generations, Rectangle location, boolean repeat, List<String> players, Game game,
			int genStart) throws IOException {
		GameSimulator simulator = new GameSimulator(game);
		for ( int generation = genStart; generation < generations; ++generation ) {
			Game target = new Game();
			target.getChunks().addAll(getChunks(name, players, location, generation));
			simulator.tick();
			game.clearHashes();
			if ( debug ) {
				debugStream.printf("Test %s in generation %d:\n", name, generation);
				printField(game);
				debugStream.println("Expected:");
				printField(target);
				debugStream.println();
			}
			Assert.assertEquals(target, game);
		}
		if ( repeat ) {
			for ( Chunk chunk : game.getChunks() ) {
				chunk.setGeneration(-1);
			}
			test(name, generations, location, false, players, game, 0);
		}
	}

	private void test(String name, int generations, Rectangle location, boolean repeat, String... players)
			throws IOException {
		List<String> playerList = Arrays.asList(players);
		Game game = new Game();
		game.getChunks().addAll(getChunks(name, playerList, location, 0));
		if ( debug ) {
			debugStream.printf("Test %s initial pattern (testing for %d generations%s):\n", name, generations,
					repeat ? " with repeat" : "");
			printField(game);
			debugStream.println();
		}
		test(name, generations, location, repeat, playerList, game, 1);
	}

	@Test
	public void blockTest() throws IOException {
		test("block", 2, new Rectangle(0, 0, 1, 1), true, "default");
	}

	@Test
	public void chunkBordersTest() throws IOException {
		test("chunkBorders", 2, new Rectangle(0, 0, 2, 2), true, "default");
	}

	@Test
	public void beehiveTest() throws IOException {
		test("beehive", 2, new Rectangle(0, 0, 1, 1), true, "default");
	}

	@Test
	public void loafTest() throws IOException {
		test("loaf", 2, new Rectangle(0, 0, 1, 1), true, "default");
	}

	@Test
	public void boatTest() throws IOException {
		test("boat", 2, new Rectangle(0, 0, 1, 1), true, "default");
	}

	@Test
	public void blinkerTest() throws IOException {
		test("blinker", 2, new Rectangle(0, 0, 1, 1), true, "default");
	}

	@Test
	public void toadTest() throws IOException {
		test("toad", 2, new Rectangle(0, 0, 1, 1), true, "default");
	}

	@Test
	public void beaconTest() throws IOException {
		test("beacon", 2, new Rectangle(0, 0, 1, 1), true, "default");
	}

	@Test
	public void failTest() throws IOException {
		try {
			test("fail", 2, new Rectangle(0, 0, 1, 1), true, "default");
		} catch ( Throwable ex ) {
			return;
		}
		blockTest(); // Make sure it is not the simulator that is not working
		Assert.fail("Test code does not work");
	}
}
