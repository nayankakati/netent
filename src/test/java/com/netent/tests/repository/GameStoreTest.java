package com.netent.tests.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.netent.domains.Game;
import com.netent.domains.Round;
import com.netent.domains.RoundResult;
import com.netent.domains.RoundType;
import com.netent.repository.impl.GameStoreImpl;

/**
 * Created by nayan.kakati on 11/21/17.
 */
@RunWith(SpringRunner.class)
public class GameStoreTest {

	private GameStoreImpl gameStore;
	private Game game;
	private Round round;

	@Before
	public void setup() {
		gameStore = new GameStoreImpl();
		game = new Game("game-1234");
		round = new Round("round-1234", RoundType.NORMAL);
		round.setRoundResult(RoundResult.WIN);
		game.setRounds(Arrays.asList(round));
		gameStore.saveGame(game.getGameId(), game);
	}

	@Test
	public void test_game_store_get_rounds_by_round_id() {
		List<Round> actualRounds = gameStore.getRoundsByGameId("game-1234");
		Assert.assertEquals(actualRounds.get(0), round);
		Assert.assertEquals(actualRounds.get(0).getRoundType(), round.getRoundType());
		Assert.assertEquals(actualRounds.get(0).getRoundResult(), round.getRoundResult());
	}

	@Test
	public void test_game_store_get_round_winning_by_id() {
		Round actualRound = gameStore.getRoundWinningAmountByRoundId("round-1234");
		Assert.assertEquals(actualRound, round);
		Assert.assertEquals(actualRound.getRoundType(), round.getRoundType());
		Assert.assertEquals(actualRound.getRoundResult(), round.getRoundResult());
	}

	@Test
	public void test_game_store_get_round_winning_by_id_round_not_found() {
		Round actualRound = gameStore.getRoundWinningAmountByRoundId("round-000");
		Assert.assertNull(actualRound);
	}

	@Test
	public void test_game_by_game_id() {
		Game actualGame = gameStore.getGameById("game-1234");
		Assert.assertEquals(actualGame, game);
		Assert.assertEquals(actualGame.getGameId(), game.getGameId());
		Assert.assertEquals(actualGame.getRounds(), game.getRounds());
	}

}
