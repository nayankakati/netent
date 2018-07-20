package com.netent.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.netent.domains.Game;
import com.netent.domains.Round;
import com.netent.repository.GameStore;

/**
 * Created by nayan.kakati on 11/20/17.
 */

@Component
public class GameStoreImpl implements GameStore {

	private Map<String, Game> gameStore;

	public GameStoreImpl() {
		this.gameStore = new HashMap<>();
	}

	public List<Round> getRoundsByGameId(String gameId) {
		List<Round> rounds = null;
		Game game =  gameStore.get(gameId);
		return game != null ? game.getRounds() : rounds;
	}

	public Round getRoundWinningAmountByRoundId(String roundId) {
		Round winningRound = null;
		for(Game game : gameStore.values()) {
			Round currRound = game.getRounds().stream().filter(round ->
				(round != null && round.getRoundId().equals(roundId))).findAny().orElse(null);
			if(currRound != null) {
				winningRound = currRound;
				break;
			}
		}
		return winningRound;
	}

	public Game getGameById(String gameId) {
		return gameStore.get(gameId);
	}

	public void saveGame(String gameId, Game game) {
		gameStore.put(gameId, game);
	}
}
