package org.kadirov.group.specification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Filters data by multiple fields joined entity.
 * <pre>
 *    Example:
 *
 *    &#064;Entity
 *    public class Student {
 *    &#064;FilterOnMultiFields(values = {
 *        &#064;FilterWithJoin(filterKey = "userId", joinedEntityFieldName = "id")
 *        &#064;FilterWithJoin(filterKey = "userName", joinedEntityFieldName = "name")
 *    })
 *      private User user;
 *    }
 *
 *    &#064;Entity
 *    public class User {
 *      private Long id;
 *      private String name;
 *    }
 * </pre>
 * @see     FilterOnMultiFields
 * @author atabek
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterOnMultiFields {

    FilterWithJoin[] values();
}
