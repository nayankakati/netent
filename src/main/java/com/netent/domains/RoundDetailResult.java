package com.netent.domains;

/**
 * Created by nayan.kakati on 11/20/17.
 */
public enum  RoundDetailResult {
	WIN_COINS,
	WIN_ROUND;

	public boolean checkCoinsWon(RoundDetailResult checkCoin) {
		return WIN_COINS.equals(checkCoin);
	}

	public boolean checkFreeRoundWon(RoundDetailResult checkFreeRound) {
		return WIN_ROUND.equals(checkFreeRound);
	}
}
