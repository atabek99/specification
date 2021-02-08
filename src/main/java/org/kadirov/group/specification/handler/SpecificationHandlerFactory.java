package org.kadirov.group.specification.handler;

import com.sun.istack.NotNull;
import org.kadirov.group.specification.annotation.SpecificationFor;
import org.kadirov.group.specification.annotation.SpecificationWithJoin;
import org.kadirov.group.specification.annotation.SpecificationWithMultiJoin;
import org.kadirov.group.specification.exception.SpecificationHandlerException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

/**
 * @author atabek
 */
@Component
public class SpecificationHandlerFactory {

    public SpecificationHandler getHandler(@NotNull Field field, String filterKey) {
        Assert.notNull(field, "Field for specification handler cannot be null");
        Assert.notNull(filterKey, "Filter key for specification handler cannot be null");

        final var forAnnotation = field.getAnnotation(SpecificationFor.class);
        final var joinAnnotation = field.getAnnotation(SpecificationWithJoin.class);
        final var multiJoinAnnotation = field.getAnnotation(SpecificationWithMultiJoin.class);

        if (forAnnotation != null && forAnnotation.filterKey().equals(filterKey)) {
            return new SpecificationForHandler();
        } else if (joinAnnotation != null && joinAnnotation.filterKey().equals(filterKey)) {
            return new SpecificationWithJoinHandler();
        } else if (multiJoinAnnotation != null && multiJoinAnnotation.filterKey().equals(filterKey)) {
            return new SpecificationWithMultiJoinHandler();
        } else {
            throw new SpecificationHandlerException(String.format("Handler not found for field <%s>", field.getName()));
        }
    }

}
