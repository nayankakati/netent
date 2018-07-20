package com.netent.domains;

/**
 * Created by nayan.kakati on 11/20/17.
 */
public class Round {

	String roundId ;
	RoundType roundType;
	RoundResult roundResult;

	public Round(String roundId, RoundType roundType) {
		this.roundId = roundId;
		this.roundType = roundType;
	}

	public int getRoundWinningAmount() {
		return roundResult == null ? 0 : roundResult.equals(RoundResult.WIN) ? 20 : 0;
	}

	public String getRoundId() {
		return roundId;
	}

	public void setRoundId(String roundId) {
		this.roundId = roundId;
	}

	public RoundType getRoundType() {
		return roundType;
	}

	public void setRoundType(RoundType roundType) {
		this.roundType = roundType;
	}

	public RoundResult getRoundResult() {
		return roundResult;
	}

	public void setRoundResult(RoundResult roundResult) {
		this.roundResult = roundResult;
	}
}
