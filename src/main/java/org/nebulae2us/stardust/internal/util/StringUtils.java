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

import static org.nebulae2us.stardust.internal.util.BaseAssert.AssertSyntax;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Trung Phan
 *
 */
public class StringUtils {
	
	public static List<String> splitFunctionInput(String functionInput) {
		List<String> result = new ArrayList<String>();

		int nestedQuotes = 0;
		
		int startIndex = 0;
		int runIndex = startIndex;
		while (runIndex < functionInput.length()) {
			char c = functionInput.charAt(runIndex);
			if (c == '(') {
				nestedQuotes++;
			}
			else if (c == ')') {
				AssertSyntax.isTrue(nestedQuotes > 0, "Syntax is invalid for %s.", functionInput);
				nestedQuotes--;
			}
			else if (c == ',' && nestedQuotes == 0) {
				result.add(functionInput.substring(startIndex, runIndex).trim());
				startIndex = runIndex + 1;
				runIndex = startIndex;
				continue;
			}
			runIndex++;
		}
		result.add(functionInput.substring(startIndex).trim());
		
		return result;
	}
}
