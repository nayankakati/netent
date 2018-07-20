package com.netent.tests.services;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.netent.domains.Game;
import com.netent.domains.Round;
import com.netent.domains.RoundDetailResult;
import com.netent.domains.RoundResult;
import com.netent.domains.RoundType;
import com.netent.helpers.PlayGround;
import com.netent.helpers.UniqueIdGenerator;
import com.netent.repository.GameStore;
import com.netent.repository.impl.GameStoreImpl;
import com.netent.services.GameService;
import com.netent.services.impl.GameServiceImpl;

/**
 * Created by nayan.kakati on 11/21/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class GameServiceTest {

	@Configuration
	static class GameServiceTestContextConfiguration {

		@Bean
		public GameStore gameStore() {
			return Mockito.mock(GameStoreImpl.class);
		}

		@Bean
		public UniqueIdGenerator uniqueIdGenerator() {
			return Mockito.mock(UniqueIdGenerator.class);
		}

		@Bean
		public Environment environment() {
			return Mockito.mock(Environment.class);
		}

		@Bean
		public PlayGround playGround() {
			return Mockito.mock(PlayGround.class);
		}
		@Bean
		public GameService gameService() {
			return new GameServiceImpl();
		}
	}

	@Autowired
	private GameStore gameStore;
	@Autowired
	private UniqueIdGenerator uniqueIdGenerator;
	@Autowired
	private Environment environment;
	@Autowired
	private PlayGround playGround;
	@Autowired
	private GameService gameService;
	private Game game;
	private Round round;

	@Test
	public void test_game_play_service_normal_win_coins_only() throws UnknownHostException {
		setupData(RoundType.NORMAL,RoundResult.WIN);
		Mockito.when(playGround.playonTheGround()).thenReturn(createRoundWinCoinsResult());
		Mockito.when(uniqueIdGenerator.generateUniqueRoundId()).thenReturn("game-1234");

		Game actualGame  = gameService.play();
		Assert.assertEquals(game.getGameId(),actualGame.getGameId());
		Assert.assertEquals(game.getGameWinnings(),actualGame.getGameWinnings());
		Assert.assertNotEquals(game.isCompleted(), actualGame.isCompleted());
	}

	@Test
	public void test_game_play_service_normal_win_coins_and_free_round() throws UnknownHostException {
		setupData(RoundType.NORMAL,RoundResult.WIN);
		Mockito.when(playGround.playonTheGround()).thenReturn(createfreeRoundWinAndCoinsResult());
		Mockito.when(uniqueIdGenerator.generateUniqueRoundId()).thenReturn("game-1234");

		Game actualGame  = gameService.play();
		Assert.assertEquals(game.getGameId(),actualGame.getGameId());
		Assert.assertEquals(game.getGameWinnings(),actualGame.getGameWinnings());
		Assert.assertEquals(game.isCompleted(), actualGame.isCompleted());
		Assert.assertNotNull(actualGame.getNextRoundUrl());
	}

	@Test
	public void test_game_play_service_free_win_coins_only() throws UnknownHostException {
		setupData(RoundType.FREE,RoundResult.WIN);
		Mockito.when(playGround.playonTheGround()).thenReturn(createRoundWinCoinsResult());
		Mockito.when(uniqueIdGenerator.generateUniqueRoundId()).thenReturn("game-1234");
		Mockito.when(gameStore.getGameById("game-1234")).thenReturn(game);

		Game actualGame  = gameService.playFreeRound("game-1234");
		Assert.assertEquals(game.getGameId(),actualGame.getGameId());
		Assert.assertEquals(game.getGameWinnings(),actualGame.getGameWinnings());
		Assert.assertEquals(game.isCompleted(), actualGame.isCompleted());
	}

	@Test
	public void test_game_play_service_free_win_free_round_coins() throws UnknownHostException {
		setupData(RoundType.FREE,RoundResult.WIN);
		Mockito.when(playGround.playonTheGround()).thenReturn(createfreeRoundWinAndCoinsResult());
		Mockito.when(uniqueIdGenerator.generateUniqueRoundId()).thenReturn("game-1234");
		Mockito.when(gameStore.getGameById("game-1234")).thenReturn(game);

		Game actualGame  = gameService.playFreeRound("game-1234");
		Assert.assertEquals(game.getGameId(),actualGame.getGameId());
		Assert.assertEquals(game.getGameWinnings(),actualGame.getGameWinnings());
		Assert.assertEquals(game.isCompleted(), actualGame.isCompleted());
	}

	@Test
	public void test_get_round_winnings() {
		setupData(RoundType.NORMAL,RoundResult.WIN);
		Mockito.when(gameStore.getRoundWinningAmountByRoundId("round-1234")).thenReturn(round);
		Round actualRound = gameService.getRoundWinnings("round-1234");
		Assert.assertEquals(round.getRoundId(), actualRound.getRoundId());
		Assert.assertEquals(round.getRoundType(), actualRound.getRoundType());
		Assert.assertEquals(round.getRoundResult(), actualRound.getRoundResult());
	}

	private void setupData(RoundType roundType, RoundResult roundResult){
		game = new Game("game-1234");
		round = new Round("round-1234", roundType);
		round.setRoundResult(roundResult);
		List<Round> rounds = new ArrayList<Round>();
		rounds.add(round);
		game.setRounds(rounds);
	}

	private List<RoundDetailResult> createRoundWinCoinsResult() {
		return Arrays.asList(RoundDetailResult.WIN_COINS);
	}

	private List<RoundDetailResult> createfreeRoundWinAndCoinsResult() {
		return Arrays.asList(RoundDetailResult.WIN_COINS,RoundDetailResult.WIN_ROUND);
	}
}
