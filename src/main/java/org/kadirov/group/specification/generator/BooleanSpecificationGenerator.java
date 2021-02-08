package org.kadirov.group.specification.generator;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author atabek
 */
public class BooleanSpecificationGenerator extends AbstractSpecificationGenerator {

    @Override
    public <T> Specification<T> generateSpecification(String filter, String attribute) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(getPath(root, attribute), Boolean.parseBoolean(filter));
    }

}
