package org.kadirov.group.specification.generator;

import org.springframework.data.jpa.domain.Specification;
import org.kadirov.group.specification.SpecificationGeneratorUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author atabek
 */
public class EnumSpecificationGenerator extends AbstractSpecificationGenerator {

    @Override
    public <Y> Specification<Y> generateSpecification(String filter, String attribute) {
        return (root, query, criteriaBuilder) -> {
            final var declaredField = SpecificationGeneratorUtil.getJoinedEntityField(root, attribute);
            final var enumeration = getEnumSetFromString(filter, ",", declaredField.getType());
            CriteriaBuilder.In<Object> in = criteriaBuilder.in(getPath(root, attribute));
            enumeration.forEach(in::value);
            return in;
        };
    }

    Set<Enum> getEnumSetFromString(String string, String delimiter, Class enumClass) {
        return Arrays.stream(string.split(delimiter)).map(s -> Enum.valueOf(enumClass, s)).collect(Collectors.toSet());
    }

}
