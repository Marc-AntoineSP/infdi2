package fr.awu.annuaire.controller;

import fr.awu.annuaire.service.AuthService;

public class ConnexionController {
    private AuthService authService;
/**
 * Donc dans l'ordre :
 *  - On va afficher la page de connexion
 *  - On va vérifier les info : authService
 */
    public ConnexionController(AuthService authService) {
        this.authService = authService;
    }

    public boolean login(String email, String password) {
        return authService.login(email, password);
    }

}
