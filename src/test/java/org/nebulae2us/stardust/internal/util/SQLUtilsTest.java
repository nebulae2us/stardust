/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nebulae2us.stardust.internal.util;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.util.MapBuilder;
import org.nebulae2us.stardust.internal.util.SQLUtils;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class SQLUtilsTest {

	@Test
	public void deparameterizeSql() {

		Pair<String, List<?>> result = SQLUtils.deparameterizeSql("ddd ?, ?, :param1, ? =:param$_#2, :lastParam",
				new MapBuilder<String, Integer>()
				.put("param1", 11)
				.put("param$_#2", 12)
				.put("lastParam", 13)
				.toMap(),
				Arrays.asList(1, 2, 3)
				);
		
		assertEquals("ddd ?, ?, ?, ? =?, ?", result.getItem1());
		
	}

}
