package fr.awu.annuaire.utils;

import java.util.List;

class ApiUserResult {
    Name name;
    String email;
    Login login;
    String phone;
    String cell;
}

class ApiUserResponse {
    List<ApiUserResult> results;
}

class Name {
    String first;
    String last;
}

class Login {
    String password;
}