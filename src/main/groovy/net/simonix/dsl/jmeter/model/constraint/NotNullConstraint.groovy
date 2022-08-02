package net.simonix.dsl.jmeter.model.constraint

import groovy.transform.CompileDynamic
import groovy.transform.Immutable

@Immutable
@CompileDynamic
final class NotNullConstraint implements PropertyConstraint {
    @Override
    boolean matches(Object value) {
        return value != null
    }

    @Override
    String description() {
        return "not null"
    }
}
