package org.kadirov.group.specification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creates a filter only for a specific field without any joins. For filtering data with join
 * @see     FilterWithJoin
 * @see     FilterWithMultiJoin
 * @author atabek
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterFor {

    /**
     Specified filter key.
     If specified value will not match with filter key, specification will not be created
     */
    String filterKey();

}
