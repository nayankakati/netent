package com.netent.tests.helpers;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.netent.domains.RoundDetailResult;
import com.netent.helpers.PlayGround;

/**
 * Created by nayan.kakati on 11/20/17.
 */
@RunWith(SpringRunner.class)
public class PlayGroundTest {

	private PlayGround playGround;
	private List<RoundDetailResult> roundDetailResults;

	@Before
	public void setup() {
		playGround = new PlayGround();
	}

	@Test
	public void test_play_ground_round_win_coins() {
		setupDataForWinCoins();
		List<RoundDetailResult> actualRoundDetailsResults =  playGround.playonTheGround();
		Assert.assertNotNull(actualRoundDetailsResults);
	}

	private void setupDataForWinCoins() {
		roundDetailResults = Arrays.asList(RoundDetailResult.WIN_COINS);
	}
}
