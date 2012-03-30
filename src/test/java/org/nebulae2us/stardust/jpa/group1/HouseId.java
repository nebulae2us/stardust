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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Trung Phan
 *
 */
@Embeddable
public class HouseId {

	@Column(name="HOUSE_ID")
	private int houseId;
	
	@Column(name="HOUSE_LETTER")
	private String houseLetter;

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getHouseLetter() {
		return houseLetter;
	}

	public void setHouseLetter(String houseLetter) {
		this.houseLetter = houseLetter;
	}
	
	
	
}
