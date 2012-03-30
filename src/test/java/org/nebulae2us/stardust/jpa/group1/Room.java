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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

/**
 * @author Trung Phan
 *
 */
@Entity
@Table(name="ROOM")
@SecondaryTable(name="ROOM_DETAIL")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ROOM_TYPE")
public class Room extends AbstractEntity {

	@EmbeddedId
	private HouseId houseId;
	
	@Id
	@Column(name="SEQUENCE_NUMBER")
	private int roomSequence;
	
	@Column(name="ROOM_TYPE")
	private RoomType roomType;
	
	@Embedded
	private Color color;
	
	@Column(name="DOOR_COUNT", table="ROOM_DETAIL")
	private int doorCount;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="HOUSE_ID"),
		@JoinColumn(name="HOUSE_LETTER")
	})
	private House house;

	public HouseId getHouseId() {
		return houseId;
	}

	public void setHouseId(HouseId houseId) {
		this.houseId = houseId;
	}

	public int getRoomSequence() {
		return roomSequence;
	}

	public void setRoomSequence(int roomSequence) {
		this.roomSequence = roomSequence;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getDoorCount() {
		return doorCount;
	}

	public void setDoorCount(int doorCount) {
		this.doorCount = doorCount;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	
}
