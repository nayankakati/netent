package com.netent.tests.controllers;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.netent.Application.GameApplication;
import com.netent.controllers.GameController;
import com.netent.domains.Game;
import com.netent.domains.Round;
import com.netent.domains.RoundResult;
import com.netent.domains.RoundType;
import com.netent.services.GameService;

/**
 * Created by nayan.kakati on 11/20/17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = GameController.class, secure = false)
@ContextConfiguration(classes = GameApplication.class)
public class GameControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean(name="gameService")
	private GameService gameService;

	private Game game;
	private Round round;

	@Test
	public void test_game_play_normal_round_win() throws Exception {
		setupData(RoundType.NORMAL,RoundResult.WIN);
		Mockito.when(gameService.play()).thenReturn(game);
		RequestBuilder builder = MockMvcRequestBuilders.get("/api/games/play").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		String expected = "{gameId:game-1234,nextRoundUrl:null,rounds:[{roundId:round-1234,roundType:NORMAL,roundResult:WIN }]}";
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	public void test_game_play_normal_round_loose()  throws Exception {
		setupData(RoundType.NORMAL, RoundResult.LOOSE);
		Mockito.when(gameService.play()).thenReturn(game);
		RequestBuilder builder = MockMvcRequestBuilders.get("/api/games/play").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		String expected = "{gameId:game-1234,nextRoundUrl:null,rounds:[{roundId:round-1234,roundType:NORMAL,roundResult:LOOSE }]}";
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	public void test_game_play_free_round_win()  throws Exception {
		setupData(RoundType.FREE, RoundResult.WIN);
		Mockito.when(gameService.playFreeRound("game-1234")).thenReturn(game);
		RequestBuilder builder = MockMvcRequestBuilders.get("/api/games/play/game-1234").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		String expected = "{gameId:game-1234,nextRoundUrl:null,rounds:[{roundId:round-1234,roundType:FREE,roundResult:WIN }]}";
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	public void test_game_play_free_round_loose()  throws Exception {
		setupData(RoundType.FREE, RoundResult.LOOSE);
		Mockito.when(gameService.playFreeRound("game-1234")).thenReturn(game);
		RequestBuilder builder = MockMvcRequestBuilders.get("/api/games/play/game-1234").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		String expected = "{gameId:game-1234,nextRoundUrl:null,rounds:[{roundId:round-1234,roundType:FREE,roundResult:LOOSE }]}";
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	public void test_game_play_free_round_game_id_not_found()  throws Exception {
		setupData(RoundType.FREE, RoundResult.WIN);
		Mockito.when(gameService.playFreeRound("game-1234")).thenReturn(null);
		RequestBuilder builder = MockMvcRequestBuilders.get("/api/games/play/game-12").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		String expected = "{\"message\":\"Your game is not found or already completed\"}";
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	public void test_game_get_round_winnings()  throws Exception {
		setupData(RoundType.NORMAL, RoundResult.WIN);
		Mockito.when(gameService.getRoundWinnings("game-1234")).thenReturn(round);
		RequestBuilder builder = MockMvcRequestBuilders.get("/api/games/rounds/game-1234").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		String expected = "{\"Round Winnings\":{\"roundId\":\"round-1234\",\"roundType\":\"NORMAL\",\"roundResult\":\"WIN\",\"roundWinningAmount\":20}}";
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	public void test_game_get_round_not_found()  throws Exception {
		setupData(RoundType.NORMAL, RoundResult.WIN);
		Mockito.when(gameService.getRoundWinnings("game-1234")).thenReturn(null);
		RequestBuilder builder = MockMvcRequestBuilders.get("/api/games/rounds/game-1234").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		String expected = "{\"message\":\"The given round Id is not found. Please check your round Id.\"}";
		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
	}

	private void setupData(RoundType roundType, RoundResult roundResult){
		game = new Game("game-1234");
		round = new Round("round-1234", roundType);
		round.setRoundResult(roundResult);
		game.setRounds(Arrays.asList(round));
	}
}
