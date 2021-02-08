package org.kadirov.group.specification.generator;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author atabek
 */
public class StringSpecificationGenerator extends AbstractSpecificationGenerator {

    @Override
    public <T> Specification<T> generateSpecification(String filter, String attribute) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(getPath(root, attribute).as(String.class)),
                            criteriaBuilder.lower(criteriaBuilder.literal('%' + filter + '%')));
    }
}
