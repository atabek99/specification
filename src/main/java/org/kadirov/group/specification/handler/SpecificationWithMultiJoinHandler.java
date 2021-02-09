package org.kadirov.group.specification.handler;

import org.springframework.data.jpa.domain.Specification;
import org.kadirov.group.specification.SpecificationGeneratorUtil;
import org.kadirov.group.specification.annotation.FilterWithMultiJoin;
import org.kadirov.group.specification.generator.JoinSpecificationGenerator;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;


/**
 * @author atabek
 */
public class SpecificationWithMultiJoinHandler implements org.kadirov.group.specification.handler.SpecificationHandler {

    private final FilterWithMultiJoin filterWithMultiJoin;

    public SpecificationWithMultiJoinHandler(FilterWithMultiJoin multiJoin) {
        this.filterWithMultiJoin = multiJoin;
    }

    @Override
    public <T> Specification<T> handle(Field field, String filter, String attribute) {
        return (root, query, criteriaBuilder) ->
        {
            final var joinedEntityFieldName = filterWithMultiJoin.joinedEntityFieldName();
            String[] values = filterWithMultiJoin.values();
            Join<Object, Object> join = root.join(values[0]);
            for (int i = 1; i < values.length; i++) {
                join = join.join(values[i]);
            }
            final var joinedEntityField = SpecificationGeneratorUtil.getJoinedEntityField(join, joinedEntityFieldName);

            final var joinedSpecificationGenerator = new JoinSpecificationGenerator(joinedEntityField);
            Specification<Object> generate = joinedSpecificationGenerator.generate(join.get(joinedEntityFieldName), filter, attribute);
            return generate.toPredicate((Root<Object>) root, query, criteriaBuilder);
        };

    }

}
