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
package org.nebulae2us.stardust.dialect;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.nebulae2us.electron.Pair;
import static java.sql.Types.*;
/**
 * @author Trung Phan
 *
 */
public abstract class Dialect {
	
	public abstract String getSqlToRetrieveIdentityValue();
	
	public abstract String getSqlToRetrieveNextSequenceValue(String sequenceName);
	
	public abstract String getSqlToCreateSequence(String sequenceName);
	
	public abstract String getSqlToDropSequence(String sequenceName);
	
	public String getSqlToDropTable(String tableName) {
		return "drop table " + tableName;
	}
	
	public int getMaxInListSize() {
		return Integer.MAX_VALUE;
	}

	public abstract String applyLimit(String sql, long limitValue);
	
	public abstract String applyOffsetLimit(String sql, long offsetValue, long limitValue);
	
	public abstract String applyOffset(String sql, long offsetValue);
	
	public abstract String getIdentityDeclare();
	
	public Class<?> getJavaTypeForPersistence(Class<?> javaType) {
		if (Enum.class.isAssignableFrom(javaType)) {
			return String.class;
		}
		return javaType;
	}
	
	public int getSqlTypeFrom(Class<?> javaType) {
		if (java.util.Date.class.isAssignableFrom(javaType)) {
			if (Timestamp.class.isAssignableFrom(javaType)) {
				return TIMESTAMP;
			}
			else if (Time.class.isAssignableFrom(javaType)) {
				return TIME;
			}
			else if (Date.class.isAssignableFrom(javaType)) {
				return DATE;
			}
			else {
				return TIMESTAMP;
			}
		}
		else if (Number.class.isAssignableFrom(javaType)) {
			if (Long.class.isAssignableFrom(javaType)) {
				return BIGINT;
			}
			else if (Integer.class.isAssignableFrom(javaType)) {
				return INTEGER;
			}
			else if (Short.class.isAssignableFrom(javaType)) {
				return SMALLINT;
			}
			else if (Byte.class.isAssignableFrom(javaType)) {
				return TINYINT;
			}
			else if (Double.class.isAssignableFrom(javaType)) {
				return DOUBLE;
			}
			else if (Float.class.isAssignableFrom(javaType)) {
				return FLOAT;
			}
			else if (BigDecimal.class.isAssignableFrom(javaType)) {
				return DECIMAL;
			}
			else if (BigInteger.class.isAssignableFrom(javaType)) {
				return INTEGER;
			}
		}
		else if (Enum.class.isAssignableFrom(javaType)) {
			return VARCHAR;
		}
		else if (Character.class.isAssignableFrom(javaType)) {
			return CHAR;
		}
		else if (String.class.isAssignableFrom(javaType)) {
			return VARCHAR;
		}
		else if (byte[].class.isAssignableFrom(javaType)) {
			return BINARY;
		}
		else if (char[].class.isAssignableFrom(javaType)) {
			return VARCHAR;
		}
		else if (Blob.class.isAssignableFrom(javaType)) {
			return BLOB;
		}
		else if (Clob.class.isAssignableFrom(javaType)) {
			return CLOB;
		}
		
		
		return NULL;
	}
}
