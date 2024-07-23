package bm.app.Infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Criptografia {
    public static String criptografa(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }
    public static boolean verifica(String senha, String senhaCriptografada) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(senha, senhaCriptografada);
    }
}
