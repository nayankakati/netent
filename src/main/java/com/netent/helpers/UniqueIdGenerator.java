package com.netent.helpers;

import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * Created by nayan.kakati on 11/20/17.
 */
@Component
public class UniqueIdGenerator {

	public String generateUniqueRoundId() {
		UUID roundUuid = UUID.randomUUID();
		return roundUuid.toString();
	}
}
