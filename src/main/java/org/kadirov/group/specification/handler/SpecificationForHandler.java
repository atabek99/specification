package org.kadirov.group.specification.handler;

import org.springframework.data.jpa.domain.Specification;
import org.kadirov.group.specification.generator.SpecificationGeneratorFactory;

import java.lang.reflect.Field;

/**
 * @author atabek
 */
public class SpecificationForHandler implements SpecificationHandler {

    private final SpecificationGeneratorFactory specificationGeneratorFactory = new SpecificationGeneratorFactory();

    @Override
    public<T> Specification<T> handle(Field field, String filter, String attribute) {
        final var specificationGenerator = specificationGeneratorFactory.getSpecificationGenerator(field);
        return specificationGenerator.generate(filter, attribute);
    }

}
