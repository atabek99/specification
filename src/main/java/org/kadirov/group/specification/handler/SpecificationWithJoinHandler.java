package org.kadirov.group.specification.handler;

import org.springframework.data.jpa.domain.Specification;
import org.kadirov.group.specification.annotation.FilterWithJoin;
import org.kadirov.group.specification.generator.JoinSpecificationGenerator;

import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

import static org.kadirov.group.specification.SpecificationGeneratorUtil.getJoinedEntityField;


/**
 * @author atabek
 */
public class SpecificationWithJoinHandler implements SpecificationHandler {

    private final FilterWithJoin filterWithJoin;

    public SpecificationWithJoinHandler(FilterWithJoin filterWithJoin) {
        this.filterWithJoin = filterWithJoin;
    }

    @Override
    public <T> Specification<T> handle(Field field, String filter, String attribute) {
        return (root, query, criteriaBuilder) ->
        {
            final var joinedEntityFieldName = filterWithJoin.joinedEntityFieldName();

            final var join = root.join(attribute, filterWithJoin.joinType());
            final var joinedEntityField = getJoinedEntityField(join, joinedEntityFieldName);

            final var joinedSpecificationGenerator = new JoinSpecificationGenerator(joinedEntityField);
            Specification<Object> generate = joinedSpecificationGenerator.generate(join.get(joinedEntityFieldName), filter, attribute);
            return generate.toPredicate((Root<Object>) root, query, criteriaBuilder);
        };
    }

}
