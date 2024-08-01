package bm.app.Infra.configuration;

import bm.app.Model.credenciamento.Credenciamento;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


public class ConfigUtil {

    private static final Preferences prefs = Preferences.userRoot().node(ConfigUtil.class.getName());

    public static String getProperty(String key) {
        return prefs.get(key, "");
    }

    public static void setProperty(String key, String value) {
        prefs.put(key, value);
    }
    public static void permissoesApp(Credenciamento credenciamento, MenuItem menuAdministrador) {
        String tipoCredenciamento = credenciamento.getCargo().toString();
        if (tipoCredenciamento.equals("FUNCIONARIO")) {
            menuAdministrador.setDisable(true);
        }
    }
    public static void permissoesCadastros(Credenciamento credenciamento, Tab tab, Button button) {
        String tipoCredenciamento = credenciamento.getCargo().toString();
        if (tipoCredenciamento.equals("FUNCIONARIO") || tipoCredenciamento.equals("ADMINISTRADOR")) {
            tab.setDisable(true);
            button.setDisable(true);
        }
    }
    public static boolean primeiroLogin() {
        String pl = prefs.get("primeiroLogin", "");
        if (pl.isEmpty()) {
            prefs.put("primeiroLogin", "true");
        } else return !pl.equals("false");
        return true;
    }

    public static void recarregarPropiedades() throws BackingStoreException {
        prefs.sync();
    }
}
