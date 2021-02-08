package org.kadirov.group.specification.annotation;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author atabek
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecificationWithJoin {

    /**
     * Entity field name for which need create specification.
     * If specified column name will not match with request filter key, specification will not be created
     */
    String filterKey();

    String joinedEntityFieldName();

    JoinType joinType() default JoinType.INNER;

}
