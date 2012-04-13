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

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;

/**
 * @author Trung Phan
 *
 */
@Entity
@DiscriminatorColumn(name="HOUSE_TYPE_ID")
@Table(name="HOUSE")
@SecondaryTables({
	@SecondaryTable(name="HOUSE_INTERIOR_DETAIL", pkJoinColumns={
			@PrimaryKeyJoinColumn(name="IN_HOUSE_ID", referencedColumnName="HOUSE_ID"),
			@PrimaryKeyJoinColumn(name="IN_HOUSE_LETTER", referencedColumnName="HOUSE_LETTER")}),
	@SecondaryTable(name="HOUSE_EXTERIOR_DETAIL")
})
@Inheritance(strategy=InheritanceType.JOINED)
public class House extends AbstractEntity {

	@EmbeddedId
	private HouseId houseId;
	
	@Column(name="HOUSE_TYPE_ID")
	private Long houseTypeId;

	/**
	 * If room does not define back reference to house, mappedBy cannot be used. In that case, 
	 * we must use @JoinColumn together with @OneToMany.
	 * If an intermediate table is used to define one-to-many relationship (which usually the case
	 * for many-to-many relationship), then @JoinTable must be used together with @OneToMany
	 */
	@OneToMany(mappedBy="house")
	private List<Room> rooms;
	
	@ManyToMany(mappedBy="houses")
	private List<Person> owners;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="red", column=@Column(name="HOUSE_COLOR_RED", table="HOUSE_EXTERIOR_DETAIL")),
		@AttributeOverride(name="green", column=@Column(name="HOUSE_COLOR_GREEN", table="HOUSE_EXTERIOR_DETAIL")),
		@AttributeOverride(name="blue", column=@Column(name="HOUSE_COLOR_BLUE", table="HOUSE_EXTERIOR_DETAIL"))
	})
	private Color color;

	@Column(name="WINDOW_COUNT", table="HOUSE_EXTERIOR_DETAIL")
	private int windowCount;
	
	@Column(name="GARAGE_FLAG", table="HOUSE_EXTERIOR_DETAIL")
	private boolean garageIncluded;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="red", column=@Column(name="DOOR_COLOR_RED", table="HOUSE_INTERIOR_DETAIL")),
		@AttributeOverride(name="green", column=@Column(name="DOOR_COLOR_GREEN", table="HOUSE_INTERIOR_DETAIL")),
		@AttributeOverride(name="blue", column=@Column(name="DOOR_COLOR_BLUE", table="HOUSE_INTERIOR_DETAIL")),
		@AttributeOverride(name="opacity", column=@Column(name="DOOR_COLOR_OPACITY", table="HOUSE_INTERIOR_DETAIL"))
	})
	private TranslucentColor doorColor;
	
	@Column(name="BEDROOM_COUNT", table="HOUSE_INTERIOR_DETAIL")
	private int bedroomCount;
	
	@Column(name="KITCHEN_AREA", table="HOUSE_INTERIOR_DETAIL")
	private double kitchenArea;

	public Long getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(Long houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getWindowCount() {
		return windowCount;
	}

	public void setWindowCount(int windowCount) {
		this.windowCount = windowCount;
	}

	public boolean isGarageIncluded() {
		return garageIncluded;
	}

	public void setGarageIncluded(boolean garageIncluded) {
		this.garageIncluded = garageIncluded;
	}

	public TranslucentColor getDoorColor() {
		return doorColor;
	}

	public void setDoorColor(TranslucentColor doorColor) {
		this.doorColor = doorColor;
	}

	public int getBedroomCount() {
		return bedroomCount;
	}

	public void setBedroomCount(int bedroomCount) {
		this.bedroomCount = bedroomCount;
	}

	public double getKitchenArea() {
		return kitchenArea;
	}

	public void setKitchenArea(double kitchenArea) {
		this.kitchenArea = kitchenArea;
	}

	public HouseId getHouseId() {
		return houseId;
	}

	public void setHouseId(HouseId houseId) {
		this.houseId = houseId;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Person> getOwners() {
		return owners;
	}

	public void setOwners(List<Person> owners) {
		this.owners = owners;
	}

	

}
