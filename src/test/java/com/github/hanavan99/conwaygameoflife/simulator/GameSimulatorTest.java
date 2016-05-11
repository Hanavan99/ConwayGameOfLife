package com.github.hanavan99.conwaygameoflife.simulator;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.Player;

public class GameSimulatorTest {
	private Chunk getChunk(String name, String player, int generation, Point location) throws IOException {
		try ( InputStream in = getClass().getResourceAsStream(
				String.format("simulatorTests/%s/%d/%s/%d,%d", name, generation, player, location.x, location.y))) {
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

	private void test(String name, int generations, Rectangle location, boolean repeat, List<String> players, Game game)
			throws IOException {
		GameSimulator simulator = new GameSimulator(game);
		for ( int generation = 1; generation < generations; ++generation ) {
			Game target = new Game();
			target.getChunks().addAll(getChunks(name, players, location, generation));
			simulator.tick();
			Assert.assertEquals(target, game);
		}
		if ( repeat ) {
			test(name, generations, location, false, players, game);
		}
	}

	private void test(String name, int generations, Rectangle location, boolean repeat, String... players)
			throws IOException {
		List<String> playerList = Arrays.asList(players);
		Game game = new Game();
		game.getChunks().addAll(getChunks(name, playerList, location, 0));
		test(name, generations, location, repeat, playerList, game);
	}

	@Test
	public void blockTest() throws IOException {
		test("block", 2, new Rectangle(0, 0, 1, 1), true, "default");
	}
}
