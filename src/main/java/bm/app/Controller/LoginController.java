package bm.app.Controller;

import bm.app.Infra.configuration.ConfigUtil;
import bm.app.Infra.dao.CredenciamentoDAO;
import bm.app.Infra.security.Criptografia;
import bm.app.Model.credenciamento.AdminService;
import bm.app.Model.credenciamento.Cargo;
import bm.app.Model.credenciamento.Credenciamento;
import bm.app.Utils.AppUtils;
import bm.app.Utils.CaixaDeMensagem;
import bm.app.Utils.Validacao;
import bm.app.View.ViewService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;

public class LoginController implements Initializable  {
    @FXML
    private PasswordField pfSenha, pfSenhaBanco;
    @FXML
    private TextField tfLogin,tfUrlBanco, tfUser;
    @FXML
    private Pane paneBanco, paneInicio;
    @FXML
    private Button btConfiguracoes;
    @FXML
    private Label labelConfig;

    private final CredenciamentoDAO credenciamentoDAO = new CredenciamentoDAO();

    public void entrar() throws IOException {
        if (credenciamentoDAO.verificaLogin(pfSenha.getText(),tfLogin.getText())) {
            AppController.credenciamento = credenciamentoDAO.carregarCredenciamento(tfLogin.getText());
            ViewService.telaView();
            sair();
            System.out.println("1");
        }
        System.out.println("2");
    }

    @FXML
    void trocarTela() {
                AppUtils.trocaTelaLogin(paneInicio, paneBanco,false,true,tfUrlBanco,tfUser,pfSenhaBanco);
    }
    @FXML
    void salvarUrl() throws BackingStoreException {
        boolean val = Validacao.validarBancoDeDados(tfUrlBanco,tfUser,pfSenhaBanco);
        if (val) {
            ConfigUtil.setProperty("urlBanco",tfUrlBanco.getText());
            ConfigUtil.setProperty("user",tfUser.getText());
            ConfigUtil.setProperty("senha",pfSenhaBanco.getText());
            if (ConfigUtil.getProperty("primeiroLogin").equals("create")) {
                try {
                    String senha = "admin";
                    String user = "adm";
                    String nome = "admin";
                    credenciamentoDAO.criar(new Credenciamento(nome, user, senha, Cargo.MASTER));
                    ConfigUtil.setProperty("primeiroLogin", "false");
                    CaixaDeMensagem.mensagemInformacao("Sucesso", "Primeiro login", "Você já pode acessar o sistema com o usuário 'adm' e senha 'admin'. \n Será o usuário com permissão máxima no sistema.", "done.png");
                    AppUtils.trocaTelaLogin(paneInicio, paneBanco, true, false, tfUrlBanco, tfUser, pfSenhaBanco);
                } catch (RuntimeException e) {
                    CaixaDeMensagem.mensagemErro("Erro", "Erro na conexão com o banco de dados", "Erro na conexão com o banco de dados, por favor verifique as configurações. ", "botao-x.png");
                    AppUtils.trocaTelaLogin(paneInicio, paneBanco, true, false, tfUrlBanco, tfUser, pfSenhaBanco);
                    throw new RuntimeException(e);
                }
            } else if (ConfigUtil.getProperty("primeiroLogin").equals("true")) {
                ConfigUtil.setProperty("primeiroLogin", "create");
            } else {
                AppUtils.trocaTelaLogin(paneInicio, paneBanco,true,false,tfUrlBanco,tfUser,pfSenhaBanco);
            }
            CaixaDeMensagem.mensagemInformacao("Sucesso", "Configurações salvas com sucesso", "O Sistema será fechado para serem aplicadas as configurações.", "done.png");
            ConfigUtil.recarregarPropiedades();
            Stage stage = (Stage) tfUrlBanco.getScene().getWindow();
            stage.close();
        }
    }
    public void sair() {
        Stage stage = (Stage) tfLogin.getScene().getWindow();
        stage.close();
    }

    private void verificaPrimeiroLogin() {
        boolean pl = ConfigUtil.primeiroLogin();
        if (pl) {
            AppUtils.trocaTelaLogin(paneInicio, paneBanco,false,true,tfUrlBanco,tfUser,pfSenhaBanco);
        } else {
            AppUtils.trocaTelaLogin(paneInicio, paneBanco,true,false,tfUrlBanco,tfUser,pfSenhaBanco);
        }
    }

    private void resetProperties() {
        ConfigUtil.setProperty("urlBanco", "");
        ConfigUtil.setProperty("user", "");
        ConfigUtil.setProperty("senha", "");
        ConfigUtil.setProperty("primeiroLogin", "true");
    }

    private void texto() {
        if (ConfigUtil.getProperty("primeiroLogin").equals("true")) {
            labelConfig.setText("Primeira configuração do Banco de Dados");
            btConfiguracoes.setText("Configurar");
        } else if(ConfigUtil.getProperty("primeiroLogin").equals("create")) {
            labelConfig.setText("Confirme os dados para criar o acesso.");
            btConfiguracoes.setText("Criar acesso");
        } else {
            labelConfig.setText("Configurações do Banco de Dados");
            btConfiguracoes.setText("Salvar");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //resetProperties();
        texto();
        verificaPrimeiroLogin();
    }
}
