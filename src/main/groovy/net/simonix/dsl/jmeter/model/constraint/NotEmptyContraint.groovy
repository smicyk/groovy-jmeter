package net.simonix.dsl.jmeter.model.constraint

import groovy.transform.CompileDynamic
import groovy.transform.Immutable

@Immutable
@CompileDynamic
final class NotEmptyContraint implements PropertyConstraint {

    @Override
    boolean matches(Object value) {
        if(value != null) {
            if(value instanceof String) {
                return !value.isEmpty()
            } else if(value instanceof Collection) {
                return !value.isEmpty()
            } else if(value instanceof Map) {
                return !value.isEmpty()
            } else {
                return true;
            }
        }

        return false
    }

    @Override
    String description() {
        return "not empty"
    }
}
