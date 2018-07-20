package com.netent.integration.tests;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.netent.Application.GameApplication;
import com.netent.domains.Game;
import com.netent.domains.Round;
import com.netent.domains.RoundResult;
import com.netent.domains.RoundType;
import com.netent.helpers.PlayGround;
import com.netent.helpers.UniqueIdGenerator;
import com.netent.repository.GameStore;
import com.netent.repository.impl.GameStoreImpl;
import com.netent.services.GameService;
import com.netent.services.impl.GameServiceImpl;

/**
 * Created by nayan.kakati on 11/20/17.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GameApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class GameControllerIT {

	@Configuration
	static class GameControllerTestContextConfiguration {

		@Bean
		public GameStore gameStore() {
			return new GameStoreImpl();
		}

		@Bean
		public UniqueIdGenerator uniqueIdGenerator() {
			return new UniqueIdGenerator();
		}

		@Bean
		public Environment environment() {
			return Mockito.mock(Environment.class);
		}

		@Bean
		public PlayGround playGround() {
			return new PlayGround();
		}
		@Bean
		public GameService gameService() {
			return new GameServiceImpl();
		}
	}

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	private HttpHeaders headers = new HttpHeaders();
	private Game game;
	private Round round;

	@Autowired
	@Qualifier("gameStore")
	private GameStore gameStore;
	@Autowired
	private UniqueIdGenerator uniqueIdGenerator;
	@Autowired
	private Environment environment;
	@Autowired
	private PlayGround playGround;
	@Autowired
	private GameService gameService;

	@Before
	public void init() {
		setupData(RoundType.NORMAL,RoundResult.WIN);
		gameStore.saveGame(game.getGameId(), game);
	}

	@Test
	public void test_integration_game_play() throws IOException {
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = restTemplate.exchange(createURL("/api/games/play"), HttpMethod.GET, entity, Object.class);
		Assert.assertNotNull(response);
	}

	@Test
	public void test_integration_game_play_free_round() {
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = restTemplate.exchange(createURL("/api/games/play/game-1234"), HttpMethod.GET, entity, Object.class);
		Assert.assertNotNull(response);
	}

	@Test
	public void test_integration_game_get_round_winning() {
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> response = restTemplate.exchange(createURL("/api/games/rounds/round-1234"), HttpMethod.GET, entity, Object.class);
		Assert.assertNotNull(response);
	}

	private String createURL(String uri) {
		return "http://localhost:" + port + uri;
	}
	private void setupData(RoundType roundType, RoundResult roundResult){
		game = new Game("game-1234");
		round = new Round("round-1234", roundType);
		round.setRoundResult(roundResult);
		game.setRounds(Arrays.asList(round));
  }
}
