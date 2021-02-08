package org.kadirov.group.specification.generator;

import org.springframework.data.jpa.domain.Specification;

import java.util.stream.Stream;

/**
 * @author atabek
 */
public class NumberSpecificationGenerator extends AbstractSpecificationGenerator {


    @Override
    public <T> Specification<T> generateSpecification(String filter, String attribute) {
            long[] numbers = Stream.of(filter.split("_", 2)).mapToLong(Long::parseLong).toArray();
            return (root, query, criteriaBuilder) -> numbers.length == 1 ?
                    criteriaBuilder.equal(getPath(root, attribute), numbers[0]) :
                    criteriaBuilder.between(getPath(root, attribute).as(Long.class), numbers[0], numbers[1]);

    }

}
