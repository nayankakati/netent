package com.netent.repository;

import java.util.List;

import com.netent.domains.Game;
import com.netent.domains.Round;

/**
 * Created by nayan.kakati on 11/20/17.
 */
public interface GameStore {

	List<Round> getRoundsByGameId(String gameId);
	Round getRoundWinningAmountByRoundId(String roundId);
	Game getGameById(String gameId);
	void saveGame(String gameId, Game game);
}
