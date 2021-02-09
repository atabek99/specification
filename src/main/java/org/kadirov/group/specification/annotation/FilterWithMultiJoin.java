package org.kadirov.group.specification.annotation;

import org.kadirov.group.specification.exception.SpecificationGeneratorException;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creates a filter with multi join.
 * In the {@code values}, sequentially specify the name of the entity fields that you want to join
 * <pre>
 *    Example:
 *
 *    &#064;Entity
 *    public class University {
 *    &#064;FilterWithMultiJoin(filterKey = "userId", values = {"student", "user"}, joinedEntityFieldName = "id")
 *      private Student student;
 *    }
 *
 *    &#064;Entity
 *    public class Student {
 *      private User user;
 *    }
 *
 *    &#064;Entity
 *    public class User {
 *      private Long id;
 *    }
 * </pre>
 * @see     org.kadirov.group.specification.annotation.FilterOnMultiFields
 * @author atabek
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterWithMultiJoin {

    /**
     Specified filter key.
     If specified value will not match with filter key, specification will not be created
     */
    String filterKey();

    /**
     * Sequentially specify the name of the entity fields
     */
    String[] values();

    /**
     * Points to a related entity field
     * @throws SpecificationGeneratorException
     *          if field whose name was passed to {@code joinedEntityFieldName} not found in joined entity
     */
    String joinedEntityFieldName();

    JoinType joinType() default JoinType.INNER;

}
