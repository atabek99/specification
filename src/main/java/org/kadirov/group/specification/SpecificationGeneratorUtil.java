package org.kadirov.group.specification;

import org.kadirov.group.specification.annotation.SpecificationFor;
import org.kadirov.group.specification.annotation.SpecificationWithJoin;
import org.kadirov.group.specification.annotation.SpecificationWithMultiJoin;
import org.kadirov.group.specification.exception.SpecificationGeneratorException;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @author atabek
 */
public class SpecificationGeneratorUtil {

    public static boolean checkForNull(Field field) {
        return field.getAnnotation(SpecificationFor.class) != null
                || field.getAnnotation(SpecificationWithJoin.class) != null
                || field.getAnnotation(SpecificationWithMultiJoin.class) != null;
    }

    public static <T, X> Field getJoinedEntityField(Join<T, X> join, String attribute) {
        try {
            return join.getJavaType().getDeclaredField(attribute);
        } catch (NoSuchFieldException e) {
            throw new SpecificationGeneratorException(String.format("Joined entity field <%s> not found", attribute));
        }
    }

    public static <T> Field getJoinedEntityField(Root<T> root, String attribute) {
        try {
            return root.getJavaType().getDeclaredField(attribute);
        } catch (NoSuchFieldException e) {
            throw new SpecificationGeneratorException(String.format("Joined entity field <%s> not found", attribute));
        }
    }

    public static boolean isNumericField(Field field) {
        return field.getType().isAssignableFrom(int.class)
                || field.getType().isAssignableFrom(Integer.class)
                || field.getType().isAssignableFrom(double.class)
                || field.getType().isAssignableFrom(BigDecimal.class)
                || field.getType().isAssignableFrom(long.class)
                || field.getType().isAssignableFrom(Long.class);
    }

}
