package org.kadirov.group.specification.generator;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;


/**
 * @author atabek
 */
public abstract class AbstractSpecificationGenerator implements SpecificationGenerator {

    private Path<Object> path;

    @Override
    public <T> Specification<T> generate(String filter, String attribute) {
        try {
            return generateSpecification(filter, attribute);
        } catch (Exception ignore) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.disjunction();
        }
    }

    public <T> Path<Object> getPath(Root<T> root, String attribute) {
        return path == null ? root.get(attribute) : path;
    }

    @Override
    public void setPath(Path<Object> path) {
        this.path = path;
    }

    public Path<Object> getPath() {
        return path;
    }

    public abstract <T> Specification<T> generateSpecification(String filter, String attribute);

}
