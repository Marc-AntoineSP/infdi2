package fr.awu.annuaire.ui;

import java.util.List;
import java.util.Scanner;

public class LoginConsole {
    private Scanner scanner;

    public LoginConsole() {
        this.scanner = new Scanner(System.in);
    }

    public List<String> creds() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return List.of(email, password);
    }
}
