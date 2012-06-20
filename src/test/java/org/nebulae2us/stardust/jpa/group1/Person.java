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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Trung Phan
 *
 */
@Entity
public class Person extends AbstractEntity {

	public Person() {
	}
	
	public Person(String ssn, Date dateBorn) {
		this.ssn = ssn;
		this.dateBorn = dateBorn;
	}

	/**
	 * Note: SSN may not uniquely identify a person as SSN is reused.
	 */
	@Id
	@Column(name="SSN")
	private String ssn;
	
	@Id
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_BORN")
	private Date dateBorn;
	

	@Embedded
	private BasicName name;
	
	@Column(name="GENDER")
	private Gender gender;
	
	@OneToOne(mappedBy="owner")
	private Passport passport;

	@ManyToMany
	@JoinTable(name="OWNER_DWELLING",
		joinColumns={
			@JoinColumn(name="OWNER_SSN", referencedColumnName="SSN"),
			@JoinColumn(name="OWNER_DATE_BORN", referencedColumnName="DATE_BORN")},
		inverseJoinColumns={
			@JoinColumn(name="DWELLING_HOUSE_ID", referencedColumnName="HOUSE_ID"),
			@JoinColumn(name="DWELLING_HOUSE_LETTER", referencedColumnName="HOUSE_LETTER")}
	)
	private List<House> houses;

	public String getSsn() {
		return ssn;
	}
	
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Date getDateBorn() {
		return dateBorn;
	}

	public void setDateBorn(Date dateBorn) {
		this.dateBorn = dateBorn;
	}

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}

	public BasicName getName() {
		return name;
	}

	public void setName(BasicName name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Person(")
			.append(ssn)
			.append(')');
		
		return result.toString();
	}
	
}
