package com.github.hanavan99.conwaygameoflife.network.packets;

import com.github.hanavan99.conwaygameoflife.model.Game;

public class ChallengePacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		Game game = new Game();
		game.setGenerationPeriod(42);
		return new ChallengePacket(game, 0);
	}

	@Override
	protected IPacket packetB() {
		Game game = new Game();
		game.setGenerationPeriod(53);
		return new ChallengePacket(game, 100);
	}
}
