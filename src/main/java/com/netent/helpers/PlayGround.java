package com.netent.helpers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.netent.domains.RoundDetailResult;

/**
 * Created by nayan.kakati on 11/20/17.
 */

@Component
public class PlayGround {

	public List<RoundDetailResult> playonTheGround() {
		List<RoundDetailResult> results = new ArrayList<>();
		double d = Math.random();
		if (d <= 0.1) {
			results.add(RoundDetailResult.WIN_COINS);
			results.add(RoundDetailResult.WIN_ROUND);
			return results;
		} else if (d <= 0.3) {
			results.add(RoundDetailResult.WIN_COINS);
		}
		return results;
	}
}
