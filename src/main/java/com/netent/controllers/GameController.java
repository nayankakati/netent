package com.netent.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netent.domains.Game;
import com.netent.domains.Round;
import com.netent.services.GameService;

/**
 * Created by nayan.kakati on 11/20/17.
 */

@RestController
public class GameController {

	@Autowired
	private GameService gameService;

	@RequestMapping(value = "/api/games/play",
		method = RequestMethod.GET,
		produces = {"application/json"})
	@ResponseBody
	public Object playGame() throws Exception {
		Game game  =  gameService.play();
		return new ResponseEntity<>(game, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/games/play/{gameRoundId}",
		method = RequestMethod.GET,
		produces = {"application/json"})
	@ResponseBody
	public Object playFreeRound(@PathVariable ("gameRoundId") String gameRoundId) throws Exception {
		Game game  =  gameService.playFreeRound(gameRoundId);
		if(game != null) {
			return new ResponseEntity<>(game, HttpStatus.OK);
		} else {
			HashMap map = new HashMap();
			map.put("message","Your game is not found or already completed");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/api/games/rounds/{roundId}",
		method = RequestMethod.GET,
		produces = {"application/json"})
	@ResponseBody
	public Object getRoundWinnings(@PathVariable ("roundId") String roundId) throws Exception {
		HashMap map = new HashMap();
		Round roundWinnings  =  gameService.getRoundWinnings(roundId);
		if(roundWinnings != null) {
			map.put("Round Winnings", roundWinnings);
		} else {
			map.put("message", "The given round Id is not found. Please check your round Id.");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
