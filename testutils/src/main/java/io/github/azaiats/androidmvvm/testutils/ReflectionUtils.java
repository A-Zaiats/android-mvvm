/*
 * Copyright 2016 Andrei Zaiats.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.azaiats.androidmvvm.testutils;

import java.lang.reflect.Field;

/**
 * @author Andrei Zaiats
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * Inject value to private field
     *
     * @param instance  the target of injection
     * @param fieldName injected field
     * @param value     injected value
     */
    public static void setPrivateField(Object instance, String fieldName, Object value) {
        try {
            final Field field = instance.getClass().getDeclaredField(fieldName);
            setField(field, instance, value);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Get private field from object
     *
     * @param instance  the target object
     * @param fieldName name of field
     * @return field value
     */
    public static Object getPrivateField(Object instance, String fieldName) {
        try {
            final Field field = instance.getClass().getDeclaredField(fieldName);
            return getField(field, instance);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inject value to inherited field
     *
     * @param instance  the target of injection
     * @param fieldName injected field
     * @param value     injected value
     */
    public static void setParentField(Object instance, String fieldName, Object value) {
        final Field field = getParentField(instance.getClass(), fieldName);
        if (field == null) {
            throw new RuntimeException(new NoSuchFieldException());
        }
        setField(field, instance, value);
    }

    /**
     * Get field from object
     *
     * @param instance  the target object
     * @param fieldName name of field
     * @return field value
     */
    public static Object getParentField(Object instance, String fieldName) {
        final Field field = getParentField(instance.getClass(), fieldName);
        return getField(field, instance);
    }

    private static Object getField(Field field, Object instance) {
        field.setAccessible(true);
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setField(Field field, Object instance, Object value) {
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field getParentField(Class<?> instance, String fieldName) {
        final Class<?> superclass = instance.getSuperclass();
        if (superclass == null) {
            throw new RuntimeException(new NoSuchFieldException());
        }
        Field field = fieldByName(superclass.getDeclaredFields(), fieldName);
        return field == null ? getParentField(superclass, fieldName) : field;
    }

    private static Field fieldByName(Field[] fields, String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
}
