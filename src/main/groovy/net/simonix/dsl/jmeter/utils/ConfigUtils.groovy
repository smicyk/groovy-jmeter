/*
 * Copyright 2019 Szymon Micyk
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
package net.simonix.dsl.jmeter.utils

final class ConfigUtils {
    static Object readValue(Object value, Object defaultValue) {
        if (value != null) {
            if(String.isAssignableFrom(value.getClass())) {
                return value ?: defaultValue
            } else {
                return value
            }
        } else {
            return defaultValue
        }
    }

    static <T> T readValue(Class<T> type, Object value, Object defaultValue) {
        if (value != null) {
            if(String.isAssignableFrom(value.getClass())) {
                return value.asType(type) ?: defaultValue.asType(type)
            } else {
                return value.asType(type)
            }
        } else {
            return defaultValue.asType(type)
        }
    }

    static Object readValues(Object value, String separator, Object defaultValue) {
        Object values = readValue(value, defaultValue)
        if (values != null) {
            if (List.isAssignableFrom(values.class)) {
                return values.join(separator)
            } else {
                return values.toString()
            }
        } else {
            return defaultValue
        }
    }

    static boolean hasValue(Object value) {
        return value != null
    }
}