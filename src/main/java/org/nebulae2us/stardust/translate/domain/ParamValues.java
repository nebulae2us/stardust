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
package org.nebulae2us.stardust.translate.domain;

import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.util.Immutables;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class ParamValues {

	private int index = 0;
	private final List<?> wildcardValues;
	private final Map<String, ?> namedParamValues;
	
	private static final ParamValues _emptyParamValues = new ParamValues(Immutables.emptyStringMap(), Immutables.emptyList());
	
	public static ParamValues emptyParamValues() {
		return _emptyParamValues;
	}
	
	public ParamValues(Map<String, ?> namedParamValues, List<?> wildcardValues) {
		this.namedParamValues = namedParamValues;
		this.wildcardValues = wildcardValues;
	}
	
	public Object getParamValue(String param) {
		Assert.notEmpty(param, "param cannot be empty");
		
		AssertSyntax.isTrue(namedParamValues.containsKey(param), "Cannot find values for param '%s'", param);
		Object value = namedParamValues.get(param);
		
		return value;
	}
	
	public Object getNextWildcardValue() {
		return wildcardValues.get(index++);
	}

}
