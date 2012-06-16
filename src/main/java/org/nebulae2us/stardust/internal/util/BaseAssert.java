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

import java.lang.reflect.Constructor;

import org.nebulae2us.stardust.exception.IllegalSyntaxException;

/**
 * @author Trung Phan
 *
 */
public class BaseAssert<T extends Throwable> {
	
    public static BaseAssert<IllegalArgumentException> Assert = BaseAssert.newAssertion(IllegalArgumentException.class);
    public static BaseAssert<IllegalStateException> AssertState = BaseAssert.newAssertion(IllegalStateException.class);
    public static BaseAssert<IllegalSyntaxException> AssertSyntax = BaseAssert.newAssertion(IllegalSyntaxException.class);

    public static <T extends Throwable> BaseAssert<T> newAssertion(Class<T> exceptionClass) {
        return new BaseAssert<T>(exceptionClass);
    }

    private final Class<T> exceptionClass;

    private final Constructor<T> constructor;

    public BaseAssert(Class<T> exceptionClass) {
        this.exceptionClass = exceptionClass;
        Constructor<T> _constructor;
        try {
            _constructor = exceptionClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            _constructor = null;
        }
        constructor = _constructor;
    }

    public void throwException(String errorFormat, Object ... args) throws T {

        String message = errorFormat != null ? String.format(errorFormat, args) : null;

        T exceptionToThrow = null;
        try {
            exceptionToThrow = constructor != null ? constructor.newInstance(message)
                                                   : exceptionClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(message);
        }

        throw exceptionToThrow;
    }

    public void isTrue(boolean expression, String errorFormat, Object... args) throws T {
        if (!expression) {
            throwException(errorFormat, args);
        }
    }

    public void isFalse(boolean expression, String errorFormat, Object... args) throws T {
        if (expression) {
            throwException(errorFormat, args);
        }
    }

    public void notNull(Object object, String errorFormat, Object ... args) throws T {
        if (object == null) {
            throwException(errorFormat, args);
        }
    }

    public void isNull(Object object, String errorFormat, Object ... args) throws T {
        if (object != null) {
            throwException(errorFormat, args);
        }
    }

    public void notEmpty(Object object, String errorFormat, Object ... args) throws T {
        if (ObjectUtils.isEmpty(object)) {
            throwException(errorFormat, args);
        }
    }

    public void empty(Object object, String errorFormat, Object ... args) throws T {
        if (ObjectUtils.notEmpty(object)) {
            throwException(errorFormat, args);
        }
    }
    
    public void fail(String errorFormat, Object ... args) throws T {
        throwException(errorFormat, args);
    }

}
