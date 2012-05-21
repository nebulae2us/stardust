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
package org.nebulae2us.stardust.expr.domain;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class ExpressionIterator implements Iterator<Expression> {

	private int index = -1;
	private Iterator<? extends Expression> currentChildExpressionIterator;
	
	private final Expression parent;
	private final List<? extends Expression> childExpressions;
	
	public ExpressionIterator(Expression parent, List<? extends Expression> childExpressions) {
		Assert.notNull(parent, "parent cannot be null");
		Assert.notNull(childExpressions, "childExpressions cannot be null");
		this.parent = parent;
		this.childExpressions = childExpressions;
	}
	
	public boolean hasNext() {
		if (index == -1 || (currentChildExpressionIterator != null && currentChildExpressionIterator.hasNext())) {
			return true;
		}
		
		if (index < childExpressions.size() - 1) {
			return true;
		}
		
		return false;
	}

	public Expression next() {
		if (index == -1) {
			index ++;
			if (index < childExpressions.size()) {
				currentChildExpressionIterator = childExpressions.get(index).expressionIterator();
			}
			return parent;
		}

		if (currentChildExpressionIterator.hasNext()) {
			return currentChildExpressionIterator.next();
		}
		else if (index < childExpressions.size() - 1) {
			index++;
			currentChildExpressionIterator = childExpressions.get(index).expressionIterator();
			return currentChildExpressionIterator.next();
		}
		
		throw new NoSuchElementException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
