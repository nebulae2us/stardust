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
package org.nebulae2us.stardust.def.domain;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.internal.util.ObjectUtils;

/**
 * @author Trung Phan
 *
 */
public class JoinDefinition {

	private final String leftTableName;
	
	private final String leftColumnName;
	
	private final String rightTableName;
	
	private final String rightColumnName;
	
	public JoinDefinition(String leftTableName, String leftColumnName, String rightTableName, String rightColumnName) {
		this.leftTableName = leftTableName;
		this.leftColumnName = leftColumnName;
		this.rightTableName = rightTableName;
		this.rightColumnName = rightColumnName;
	}
	
	public static JoinDefinition parseDefinition(String definition, String validExamples) {
		Pair<String, String> splitResult = ObjectUtils.splitPath(definition, '=');
		String left = splitResult.getItem1().trim();
		String right = splitResult.getItem2().trim();
		
		AssertSyntax.notEmpty(right, "Unrecognized syntax: %s. Valid syntax examples: " + validExamples, definition);
		AssertSyntax.isTrue(right.indexOf('=') == -1, "Unrecognized syntax: %s. Valid syntax examples: " + validExamples, definition);
		
		splitResult = ObjectUtils.splitPath(left, '.');
		String leftTableName = splitResult.getItem1();
		String leftColumnName = splitResult.getItem2();
		
		AssertSyntax.notEmpty(leftTableName, "Unrecognized syntax: %s. Valid syntax examples: " + validExamples, definition);
		AssertSyntax.notEmpty(leftColumnName, "Unrecognized syntax: %s. Valid syntax examples: " + validExamples, definition);
		AssertSyntax.isTrue(leftColumnName.indexOf('.') == -1, "Unrecognized syntax: %s. Valid syntax examples: " + validExamples, definition);
		
		splitResult = ObjectUtils.splitPath(right, '.');
		String rightTableName = splitResult.getItem1();
		String rightColumnName = splitResult.getItem2();

		AssertSyntax.notEmpty(rightTableName, "Unrecognized syntax: %s. Valid syntax examples: " + validExamples, definition);
		AssertSyntax.notEmpty(rightColumnName, "Unrecognized syntax: %s. Valid syntax examples: " + validExamples, definition);
		AssertSyntax.isTrue(rightColumnName.indexOf('.') == -1, "Unrecognized syntax: %s. Valid syntax examples: " + validExamples, definition);
		
		return new JoinDefinition(leftTableName, leftColumnName, rightTableName, rightColumnName);
	}

	public String getLeftTableName() {
		return leftTableName;
	}

	public String getLeftColumnName() {
		return leftColumnName;
	}

	public String getRightTableName() {
		return rightTableName;
	}

	public String getRightColumnName() {
		return rightColumnName;
	}
	
	
}
