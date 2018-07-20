package com.netent.domains;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nayan.kakati on 11/20/17.
 */
public class Game {

	String gameId;
	String nextRoundUrl;
	boolean isCompleted = false;

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}

	private List<Round> rounds = new ArrayList<Round>();

	public Game(String gameId) {
		this.gameId = gameId;
	}

	public String getNextRoundUrl() {
		return nextRoundUrl;
	}

	public void setNextRoundUrl(String nextRoundUrl) {
		this.nextRoundUrl = nextRoundUrl;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public Integer getGameWinnings() {
		return (rounds.isEmpty() ? 0 : rounds.parallelStream().mapToInt(Round::getRoundWinningAmount).sum()) - 10;
	}
	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}
}
