package com.netent.tests.helpers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.netent.helpers.UniqueIdGenerator;

/**
 * Created by nayan.kakati on 11/20/17.
 */
@RunWith(SpringRunner.class)
public class UniqueIdGeneratorTest {

	private UniqueIdGenerator uniqueIdGenerator;

	@Before
	public void setup() {
		uniqueIdGenerator = new UniqueIdGenerator();
	}

	@Test
	public void test_generate_unique_id() {
		String actualString = uniqueIdGenerator.generateUniqueRoundId();
		Assert.assertNotNull(actualString);
	}
}
