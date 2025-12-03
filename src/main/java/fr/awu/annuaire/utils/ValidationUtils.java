package fr.awu.annuaire.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;

class ValidationUtils {
    private ValidationUtils() {
        // Utils
    }

    public Map<String, List<String>> buildErrorMap(
            Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getPropertyPath().toString(),
                        Collectors.mapping(
                                ConstraintViolation::getMessage,
                                Collectors.toList())));

    }
}