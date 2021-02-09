package org.kadirov.group.specification;

import org.kadirov.group.specification.annotation.FilterFor;
import org.kadirov.group.specification.annotation.FilterWithJoin;
import org.kadirov.group.specification.annotation.FilterWithMultiJoin;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.kadirov.group.specification.handler.SpecificationHandler;
import org.kadirov.group.specification.handler.SpecificationHandlerFactory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @author atabek
 */
@Component
public class SpecificationGenerator {

    private final SpecificationHandlerFactory handlerFactory;

    public SpecificationGenerator(SpecificationHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public <T> Specification<T> generateSpecifications(Map<String, String> params, Class<T> classType) {
        if (params == null || classType == null) {
            return null;
        }
        final var specifications = handle(params, classType);
        return specifications.stream()
                .reduce(Specification::and).orElse(null);
    }

    private <T> LinkedHashSet<Specification<T>> handle(Map<String, String> params, Class<T> classType) {
        final var specifications = new LinkedHashSet<Specification<T>>();
        params.forEach((filterKey, filterVal) -> ReflectionUtils.doWithFields(classType, field -> {
                    SpecificationHandler handler = handlerFactory.getHandler(field, filterKey);
                    if (handler != null) {
                        specifications.add(handler.handle(field, filterVal, field.getName()));
                    }
                }
        ));
        return specifications;
    }

    public String getAvailableSpecifications(Class<?> specificationClass) {
        final var set = new HashSet<String>();
        ReflectionUtils.doWithFields(specificationClass,
                field -> {
                    FilterFor specFor = field.getAnnotation(FilterFor.class);
                    FilterWithJoin specJoin = field.getAnnotation(FilterWithJoin.class);
                    FilterWithMultiJoin specMultiJoin = field.getAnnotation(FilterWithMultiJoin.class);
                    if (specFor != null) {
                        set.add(specFor.filterKey());
                    }
                    if (specJoin != null) {
                        set.add(specJoin.filterKey());
                    }
                    if (specMultiJoin != null) {
                        set.add(specMultiJoin.filterKey());
                    }
                },
                field -> field.getAnnotation(FilterFor.class) != null
                        || field.getAnnotation(FilterWithJoin.class) != null
                        || field.getAnnotation(FilterWithMultiJoin.class) != null);

        return String.join(", ", set);
    }

}
