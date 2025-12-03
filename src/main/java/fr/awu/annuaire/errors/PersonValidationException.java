package fr.awu.annuaire.errors;

import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.awu.annuaire.model.Person;
import jakarta.validation.ConstraintViolation;

public class PersonValidationException extends RuntimeException {

    private final Map<String, List<String>> errorMap;

    public PersonValidationException(String message,
            Set<ConstraintViolation<Person>> violations) {
        super(message);
        this.errorMap = violations.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        v -> v.getPropertyPath().toString(),
                        java.util.stream.Collectors.mapping(
                                ConstraintViolation::getMessage,
                                java.util.stream.Collectors.toList())));

    }

    public Map<String, List<String>> getErrors() {
        return errorMap;
    }

}