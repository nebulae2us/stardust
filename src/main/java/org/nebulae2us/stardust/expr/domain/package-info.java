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

/**
 * This package includes many types of Expression.
 * 
 * SelectorExpression is a scalar expression.
 * 
 * PredicateExpression defines true / false expression used for where clause.
 * 
 * OrderExpression is used for order by clause.
 * 
 * QueryExpression combines all other Expression to build Select SQL.
 * 
 * These classes from this package has no dependency on any other class in other packages.
 * 
 * The instances of these expressions are mainly from SelectorExpression.parse(),
 * SelectExpression.parse(), PredicateExpression.parse(), OrderExpression.parse().
 * And these are called from Filter
 * 
 * @see org.nebulae2us.stardust.Filter#toExpression()
 * @author Trung Phan
 */
package org.nebulae2us.stardust.expr.domain;

