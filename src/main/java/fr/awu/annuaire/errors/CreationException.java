package fr.awu.annuaire.errors;

import fr.awu.annuaire.interfaces.IEntity;

public class CreationException extends Exception {
    private final String message;

    public CreationException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage(IEntity entity) {
        return "CreationException: " + entity.getClass().getSimpleName() + " - " + message;
    }


}
