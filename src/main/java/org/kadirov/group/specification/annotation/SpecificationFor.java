package org.kadirov.group.specification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author atabek
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecificationFor {

    /**
     The value which have to match with request filter key.
     If specified value will not match with request filter key, specification will not be created
     */
    String filterKey();

    boolean ignoreForResponseHeader() default false;

}
