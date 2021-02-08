package org.kadirov.group.specification.generator;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.lang.reflect.Field;

/**
 * @author atabek
 */
public class JoinSpecificationGenerator {

    private final SpecificationGenerator specificationGenerator;

    public JoinSpecificationGenerator(Field field) {
        this.specificationGenerator = new SpecificationGeneratorFactory().getSpecificationGenerator(field);
    }

    public <T> Specification<T> generate(Path<Object> path, String filter, String attribute) {
        specificationGenerator.setPath(path);
        return specificationGenerator.generate(filter, attribute);
    }

}
