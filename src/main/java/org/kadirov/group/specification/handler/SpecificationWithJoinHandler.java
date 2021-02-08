package org.kadirov.group.specification.handler;

import org.springframework.data.jpa.domain.Specification;
import org.kadirov.group.specification.annotation.SpecificationWithJoin;
import org.kadirov.group.specification.generator.JoinSpecificationGenerator;

import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

import static org.kadirov.group.specification.SpecificationGeneratorUtil.getJoinedEntityField;


/**
 * @author atabek
 */
public class SpecificationWithJoinHandler implements SpecificationHandler {

    @Override
    public <T> Specification<T> handle(Field field, String filter, String attribute) {
        return (root, query, criteriaBuilder) ->
        {
            final var annotation = field.getAnnotation(SpecificationWithJoin.class);
            final var joinedEntityFieldName = annotation.joinedEntityFieldName();

            final var join = root.join(attribute, annotation.joinType());
            final var joinedEntityField = getJoinedEntityField(join, joinedEntityFieldName);

            final var joinedSpecificationGenerator = new JoinSpecificationGenerator(joinedEntityField);
            Specification<Object> generate = joinedSpecificationGenerator.generate(join.get(joinedEntityFieldName), filter, attribute);
            return generate.toPredicate((Root<Object>) root, query, criteriaBuilder);
        };

    }

}
