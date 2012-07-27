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
package org.nebulae2us.stardust.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.exception.IllegalSyntaxException;

/**
 * @author Trung Phan
 *
 */
public class SQLUtils {
	
	
	public static Pair<String, List<?>> deparameterizeSql(String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		List<Object> values = new ArrayList<Object>();
		StringBuilder newSql = new StringBuilder();
		
		int length = sql.length();
		
		boolean inParam = false;
		StringBuilder paramNameBuilder = null;
		
		for (int i = 0, j = 0; i <= length; i++) {
			char c = i < length ? sql.charAt(i) : '\0';
			if (c == '?') {
				if (inParam) {
					throw new IllegalSyntaxException("Invalid parameter expression \":" + paramNameBuilder.toString() + "\"");
				}
				values.add(wildcardValues.get(j++));
				newSql.append(c);
			}
			else if (c == ':') {
				if (inParam) {
					throw new IllegalSyntaxException("Invalid parameter expression \":" + paramNameBuilder.toString() + "\"");
				}
				
				paramNameBuilder = new StringBuilder();
				inParam = true;
			}
			else if (inParam) {
				if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c < '9' || c == '_' || c == '$' || c == '#') {
					paramNameBuilder.append(c);
				}
				else {
					inParam = false;
					String paramName = paramNameBuilder.toString();
					paramNameBuilder = null;
					if (!paramValues.containsKey(paramName)) {
						throw new IllegalSyntaxException("Cannot find value for parameter \"" + paramName + "\"");
					}
					Object paramValue = paramValues.get(paramName);
					if (paramValue instanceof Collection) {
						Collection<?> collection = (Collection<?>) paramValue;
						if (collection.size() > 0) {
							for (Object val : collection) {
								values.add(val);
								newSql.append("?,");
							}
							newSql.setCharAt(newSql.length() - 1, c);
						}
						else {
							values.add(null);
							newSql.append('?').append(c);
						}
					}
					else {
						values.add(paramValue);
						newSql.append('?').append(c);
					}
				}
			}
			else {
				newSql.append(c);
			}
		}

		newSql.deleteCharAt(newSql.length() - 1);
		
		return new Pair<String, List<?>>(newSql.toString(), values);
	}
	
	/**
	 * 
	 * Parse a sql such as "select :param1, :param2 from dual" and return a SQL with wildcards such as "select ?, ? from dual" and the list of parameters such as "[param1, param2]"
	 * 
	 * @param sql
	 * @param paramValues
	 * @return new parsed sql and list of parameter names
	 */
	public static Pair<String, List<String>> parseSql(String sql) {
		List<String> params = new ArrayList<String>();
		StringBuilder newSql = new StringBuilder();
		
		int length = sql.length();
		
		boolean inParam = false;
		StringBuilder paramNameBuilder = null;
		
		for (int i = 0; i <= length; i++) {
			char c = i < length ? sql.charAt(i) : '\0';
			if (c == '?') {
				throw new IllegalSyntaxException("Unexpected character ?");
			}
			else if (c == ':') {
				if (inParam) {
					throw new IllegalSyntaxException("Invalid parameter expression \":" + paramNameBuilder.toString() + "\"");
				}
				
				paramNameBuilder = new StringBuilder();
				inParam = true;
			}
			else if (inParam) {
				if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c < '9' || c == '_' || c == '$' || c == '#') {
					paramNameBuilder.append(c);
				}
				else {
					inParam = false;
					String paramName = paramNameBuilder.toString();
					paramNameBuilder = null;
					params.add(paramName);
					newSql.append('?').append(c);
				}
			}
			else {
				newSql.append(c);
			}
		}

		newSql.deleteCharAt(newSql.length() - 1);
		
		return new Pair<String, List<String>>(newSql.toString(), params);
	}		
	
	public static String getFullTableName(String overridingSchema, String defaultSchema, Table table) {
		String schemaName = ObjectUtils.coalesce(overridingSchema, table.getSchemaName(), table.getCatalogName(), defaultSchema);
		return ObjectUtils.isEmpty(schemaName) ? table.getName() : schemaName + '.' + table.getName();
	}
	

}
