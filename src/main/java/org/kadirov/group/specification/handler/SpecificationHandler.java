package org.kadirov.group.specification.handler;

import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;

/**
 * @author atabek
 */
public interface SpecificationHandler {

    <T> Specification<T> handle(Field field, String filter, String attribute);

}
