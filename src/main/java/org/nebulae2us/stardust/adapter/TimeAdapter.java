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
package org.nebulae2us.stardust.adapter;

import java.sql.Time;
import java.util.Date;

/**
 * @author Trung Phan
 *
 */
public class TimeAdapter extends TypeAdapter<Time, Date> {

	@Override
	public Class<Time> getPersistenceType() {
		return Time.class;
	}

	@Override
	public Time toPersistenceType(Date object) {
		return object == null ? null : object instanceof Time ? (Time)object : new Time(object.getTime());
	}

	@Override
	public Date toAttributeType(Class<? extends Date> expectedType, Time object) {
		return new Date(object.getTime());
	}

}
