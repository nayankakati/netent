package com.netent.services.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.netent.domains.Game;
import com.netent.domains.Round;
import com.netent.domains.RoundDetailResult;
import com.netent.domains.RoundResult;
import com.netent.domains.RoundType;
import com.netent.helpers.PlayGround;
import com.netent.helpers.UniqueIdGenerator;
import com.netent.repository.GameStore;
import com.netent.services.GameService;

/**
 * Created by nayan.kakati on 11/20/17.
 */
@Component
public class GameServiceImpl implements GameService {


	@Autowired
	GameStore gameStore;
	@Autowired
	UniqueIdGenerator uniqueIdGenerator;
	@Autowired
	Environment environment;
	@Autowired
	PlayGround playGround;


	@Override
	public Game play() throws UnknownHostException {

		boolean iWon = false, isFreeRoundWon = false;
		Game game = new Game(uniqueIdGenerator.generateUniqueRoundId());

		Round currentRound = new Round(uniqueIdGenerator.generateUniqueRoundId(), RoundType.NORMAL);
		List<RoundDetailResult> roundResults = playGround.playonTheGround();
		if(roundResults.size() != 0) {
			for (RoundDetailResult roundResult : roundResults) {
				if (roundResult.checkFreeRoundWon(roundResult)) {
					// create url for next round
					game.setNextRoundUrl(generateFreeRoundUrl(game.getGameId()));
					isFreeRoundWon = true;
				}
				iWon = true;
			}
		}
		processGameResults(iWon, isFreeRoundWon, game, currentRound);
		return game;
	}

	@Override
	public Game playFreeRound(String gameRoundId) throws UnknownHostException {
		boolean iWon = false, isFreeRoundwon = false;
		Game game = gameStore.getGameById(gameRoundId);

		if(game != null && !game.isCompleted()) {
			Round currentRound = new Round(uniqueIdGenerator.generateUniqueRoundId(), RoundType.FREE);
			List<RoundDetailResult> roundResults = playGround.playonTheGround();
			if (roundResults.size() != 0) {
				for (RoundDetailResult roundResult : roundResults) {
					if (roundResult.checkFreeRoundWon(roundResult)) {
						// create url for next round
						game.setNextRoundUrl(generateFreeRoundUrl(gameRoundId));
						isFreeRoundwon = true;
					}
					iWon = true;
				} //else player looses
			}
			processGameResults(iWon, isFreeRoundwon, game, currentRound);
		}
		return game;
	}

	@Override
	public Round getRoundWinnings(String roundId) {
		return gameStore.getRoundWinningAmountByRoundId(roundId);
	}

	private void processGameResults(boolean iWon, boolean isFreeRoundWon, Game game, Round currentRound) {
		if(isFreeRoundWon == false)
			game.setNextRoundUrl(null);
		game.setCompleted(!isFreeRoundWon);
		currentRound.setRoundResult(iWon ? RoundResult.WIN : RoundResult.LOOSE);
		game.getRounds().add(currentRound);
		gameStore.saveGame(game.getGameId(), game);
	}

	private String generateFreeRoundUrl(String gameId) throws UnknownHostException {
		StringBuilder url = new StringBuilder();
		InetAddress address = InetAddress.getLocalHost();
		url.append("http://").append(address.getHostAddress()).append(":").append(environment.getProperty("local.server.port")).append("/api/games/play/").append(gameId);
		return url.toString();
	}
}

