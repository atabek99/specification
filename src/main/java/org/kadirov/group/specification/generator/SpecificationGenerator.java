package org.kadirov.group.specification.generator;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;

/**
 * @author atabek
 */
public interface SpecificationGenerator {

    <T> Specification<T> generate(String filter, String attribute);

    void setPath(Path<Object> path);
}
