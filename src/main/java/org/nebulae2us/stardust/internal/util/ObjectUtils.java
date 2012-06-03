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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nebulae2us.electron.Pair;

/**
 * @author Trung Phan
 *
 */
public class ObjectUtils {

	public static Pair<String, String> splitPath(String path, char delimiter) {
		int idx = path.indexOf(delimiter);
		if (idx > -1) {
			return new Pair<String, String>(path.substring(0, idx), path.substring(idx+1));
		}
		else {
			return new Pair<String, String>(path, "");
		}
	}
	
	public static boolean notEqual(Object value1, Object value2) {
		return !equal(value1, value2);
	}
	
	public static boolean equal(Object value1, Object value2) {
		if (value1 == value2) {
			return true;
		}
		if (value1 == null || value2 == null) {
			return false;
		}
		return value1.equals(value2);
	}	
	
    public static <T> T nvl(T str1, T str2) {
        return str1 != null ? str1 : str2;
    }
    
    public static String nvl(String str) {
    	return nvl(str, "");
    }

    @SuppressWarnings("unchecked")
	public static <T> Collection<T> nvl(Collection<T> c) {
    	return nvl(c, Collections.EMPTY_LIST);
    }
    
    @SuppressWarnings("unchecked")
	public static <T> List<T> nvl(List<T> c) {
    	return nvl(c, Collections.EMPTY_LIST);
    }

    @SuppressWarnings("unchecked")
	public static <T> Set<T> nvl(Set<T> c) {
    	return nvl(c, Collections.EMPTY_SET);
    }

    public static String evl(String str1, String str2) {
        return str1 != null && str1.length() > 0 ? str1 : str2;
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        else if (object instanceof String) {
            return ((String)object).length() == 0;
        }
        else if (object instanceof StringBuilder) {
            return ((StringBuilder)object).length() == 0;
        }
        else if (object instanceof Collection) {
            return ((Collection)object).size() == 0;
        }
        else if (object instanceof Map) {
            return ((Map)object).size() == 0;
        }
        else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }

        return false;
    }

    public static boolean notEmpty(Object object) {
        return !isEmpty(object);
    }
}
