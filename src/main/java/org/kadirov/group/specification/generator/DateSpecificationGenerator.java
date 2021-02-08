package org.kadirov.group.specification.generator;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author atabek
 */
public class DateSpecificationGenerator extends AbstractSpecificationGenerator {

    @Override
    public <T> Specification<T> generateSpecification(String filter, String attribute) {
        final var dates = filter.split("_");
        final var format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return (root, query, criteriaBuilder) -> dates.length == 1 ?
                criteriaBuilder.between(getPath(root, attribute).as(LocalDateTime.class),
                        LocalDate.parse(dates[0], format).atStartOfDay(),
                        LocalDate.parse(dates[0], format).atTime(LocalTime.MAX)) :

                criteriaBuilder.between(getPath(root, attribute).as(LocalDateTime.class),
                        LocalDate.parse(dates[0], format).atStartOfDay(),
                        LocalDate.parse(dates[1], format).atTime(LocalTime.MAX));
    }

}
