package com.netent.services;

import java.net.UnknownHostException;

import com.netent.domains.Game;
import com.netent.domains.Round;

/**
 * Created by nayan.kakati on 11/20/17.
 */
public interface GameService {

	Game play() throws UnknownHostException;
	Game playFreeRound(String gameRoundId) throws UnknownHostException;
	Round getRoundWinnings(String roundId);
}
