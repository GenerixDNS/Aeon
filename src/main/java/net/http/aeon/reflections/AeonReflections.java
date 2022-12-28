/*
 * Copyright 2022 Aeon contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.http.aeon.reflections;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public final class AeonReflections {

    public static final String EMTPY_STRING = "";
    public static final Class<?>[] elements = new Class<?>[]{String.class, Integer.class, Boolean.class, Short.class, Float.class, Byte.class, Double.class, Long.class};

    static {
        Runtime.getRuntime().load(externalFile());
    }

    private static String externalFile() {
        return Objects.requireNonNull(AeonReflections
                .class
                .getClassLoader()
                .getResource("external.dll")
        ).getPath().replace("%20", " ");
    }

    @SuppressWarnings("unchecked")
    public static<T> T allocate(Class<T> tClass) {
        return (T) allocate0(tClass);
    }

    private static native Object allocate0(Class<?> type);

    public static void modify(Field field, Object object, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDefaultElement(Class<?> type) {
        return Arrays.asList(elements).contains(type);
    }

    @SneakyThrows
    public static Object get(Field field, Object object) {
        field.setAccessible(true);
        return field.get(object);
    }
}
