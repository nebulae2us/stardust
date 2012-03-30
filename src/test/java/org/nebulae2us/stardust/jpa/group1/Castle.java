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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

/**
 * @author Trung Phan
 *
 */
@Entity
@DiscriminatorValue("2")
@Table(name="CASTLE")
@PrimaryKeyJoinColumns({
	@PrimaryKeyJoinColumn(name="CASTLE_HOUSE_ID"),
	@PrimaryKeyJoinColumn(name="CASTLE_HOUSE_LETTER")
})
public class Castle extends House {

	@Column(name="CASTLE_STYLE")
	private String castleStyle;

	public String getCastleStyle() {
		return castleStyle;
	}

	public void setCastleStyle(String castleStyle) {
		this.castleStyle = castleStyle;
	}
	
}
