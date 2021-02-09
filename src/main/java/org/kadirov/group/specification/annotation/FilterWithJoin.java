package org.kadirov.group.specification.annotation;

import org.kadirov.group.specification.exception.SpecificationGeneratorException;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creates a filter with join. For filtering data with multi join
 * <pre>
 *    Example:
 *
 *    &#064;Entity
 *    public class Student {
 *    &#064;FilterWithJoin(filterKey = "userId", joinedEntityFieldName = "id")
 *      private User user;
 *    }
 *
 *    &#064;Entity
 *    public class User {
 *      private Long id;
 *    }
 * </pre>
 * @see     FilterWithMultiJoin
 * @author atabek
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterWithJoin {

    /**
     *  Specified filter key.
     *  If specified value will not match with filter key, specification will not be created
     */
    String filterKey();

    /**
     * Points to a related entity field
     * @throws SpecificationGeneratorException
     *          if field whose name was passed to {@code joinedEntityFieldName} not found in joined entity
     */
    String joinedEntityFieldName();

    JoinType joinType() default JoinType.INNER;

}
