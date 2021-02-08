package org.kadirov.group.specification.generator;

import org.kadirov.group.specification.SpecificationGeneratorUtil;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author atabek
 */
public class SpecificationGeneratorFactory {

    public SpecificationGenerator getSpecificationGenerator(Field field) {
        if (field.getType().isAssignableFrom(LocalDateTime.class)
                || field.getType().isAssignableFrom(LocalDate.class)) {

            return new DateSpecificationGenerator();

        } else if (field.getType().isAssignableFrom(boolean.class)
                || field.getType().isAssignableFrom(Boolean.class)) {

            return new BooleanSpecificationGenerator();

        } else if (SpecificationGeneratorUtil.isNumericField(field)) {
            return new NumberSpecificationGenerator();

        } else if (field.getClass().isEnum()) {
            return new EnumSpecificationGenerator();

        } else {
            return new StringSpecificationGenerator();
        }
    }

}
