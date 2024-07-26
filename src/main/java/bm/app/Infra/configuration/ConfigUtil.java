package bm.app.Infra.configuration;

import bm.app.Model.credenciamento.Credenciamento;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
    public static void permissoesTelaFuncionario(Credenciamento credenciamento, Tab tab, Button button) {
        String tipoCredenciamento = credenciamento.getCargo().toString();
        if (tipoCredenciamento.equals("FUNCIONARIO")) {
            tab.setDisable(true);
            button.setDisable(true);
        }
    }
}
