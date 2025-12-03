package fr.awu.annuaire.ui;

import java.util.List;
import java.util.Scanner;

import fr.awu.annuaire.service.AuthService;

public class LoginConsole {
    private Scanner scanner;
    private AuthService authService;

    public LoginConsole(AuthService authService) {
        this.scanner = new Scanner(System.in);
        this.authService = authService;
    }

    public void creds() {
        String email;
        String password;
        boolean hasFailed = false;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine();
            System.out.print("Password: ");
            password = scanner.nextLine();
            if(hasFailed) {
                System.out.println("Login failed. Please try again.");
            }
            hasFailed = true;
        }while (!authService.login(email, password));
        System.out.println("Login successful.");
    }
}
