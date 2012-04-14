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
package org.nebulae2us.stardust.my.domain.scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;

/**
 * @author Trung Phan
 *
 */
public class JoinColumnAnnotScanner {

	private final List<JoinColumn> joinColumns;
	
	public JoinColumnAnnotScanner(JoinColumn joinColumn, JoinColumns joinColumns) {
		this.joinColumns = new ArrayList<JoinColumn>();
		if (joinColumn != null) {
			this.joinColumns.add(joinColumn);
		}
		if (joinColumns != null) {
			this.joinColumns.addAll(Arrays.asList(joinColumns.value()));
		}
	}

	
	
}
