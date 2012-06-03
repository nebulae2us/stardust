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

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;

/**
 * @author Trung Phan
 *
 */
@Entity
@AttributeOverride(name="version", column=@Column(name="PASSPORT_VERSION"))
public class Passport extends AbstractEntity {

	public Passport() {
	}

	public Passport(int passportNumber) {
		this.passportNumber = passportNumber;
	}
	
	@Id
	private int passportNumber;
	
	private Date expiredDate;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="OWNER_SSN", nullable=false),
		@JoinColumn(name="OWNER_DATE_BORN", nullable=false)
	})
	private Person owner;

	public int getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(int passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	
}
