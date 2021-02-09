package org.kadirov.group.specification.handler;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.kadirov.group.specification.SpecificationGeneratorUtil;
import org.kadirov.group.specification.annotation.FilterFor;
import org.kadirov.group.specification.annotation.FilterOnMultiFields;
import org.kadirov.group.specification.annotation.FilterWithJoin;
import org.kadirov.group.specification.annotation.FilterWithMultiJoin;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author atabek
 */
@Component
public class SpecificationHandlerFactory {

    public SpecificationHandler getHandler(@NotNull Field field, String filterKey) {
        Assert.notNull(field, "Field for specification handler cannot be null");
        Assert.notNull(filterKey, "Filter key for specification handler cannot be null");

        final var forAnnotation = field.getAnnotation(FilterFor.class);
        final var join = field.getAnnotation(FilterWithJoin.class);
        final var multiJoin = field.getAnnotation(FilterWithMultiJoin.class);
        final var onMultiFields = field.getAnnotation(FilterOnMultiFields.class);

        if (forAnnotation != null && forAnnotation.filterKey().equals(filterKey)) {
            return new SpecificationForHandler();
        } else if (join != null && join.filterKey().equals(filterKey)) {
            return new SpecificationWithJoinHandler(join);
        } else if (multiJoin != null && multiJoin.filterKey().equals(filterKey)) {
            return new SpecificationWithMultiJoinHandler(multiJoin);
        } else if (SpecificationGeneratorUtil.matchFilterKeyForMultiFields(filterKey, onMultiFields)) {
            final var filterWithJoin = Arrays.stream(onMultiFields.values())
                    .filter(filter -> filter.filterKey().equalsIgnoreCase(filterKey))
                    .findAny().get();

            return new SpecificationWithJoinHandler(filterWithJoin);
        } else {
            return null;
        }
    }

}
