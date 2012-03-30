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
package org.nebulae2us.stardust.jpa.group1;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * @author Trung Phan
 *
 */
@Entity
@DiscriminatorValue("KITCHEN")
public class Kitchen extends Room {

	@Column(name="DISH_WASHER_FLAG", table="ROOM_DETAIL")
	private boolean containsDishwasher;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="red", column=@Column(name="STOVE_COLOR_RED", table="ROOM_DETAIL")),
		@AttributeOverride(name="green", column=@Column(name="STOVE_COLOR_GREEN", table="ROOM_DETAIL")),
		@AttributeOverride(name="blue", column=@Column(name="STOVE_COLOR_BLUE", table="ROOM_DETAIL"))
	})
	private Color stoveColor;

	public boolean isContainsDishwasher() {
		return containsDishwasher;
	}

	public void setContainsDishwasher(boolean containsDishwasher) {
		this.containsDishwasher = containsDishwasher;
	}

	public Color getStoveColor() {
		return stoveColor;
	}

	public void setStoveColor(Color stoveColor) {
		this.stoveColor = stoveColor;
	}
	
}
