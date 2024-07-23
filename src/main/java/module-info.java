module bm.app.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires java.sql;
    requires static lombok;
    requires spring.security.crypto;


    exports bm.app.View;
    opens bm.app.View to javafx.fxml;
    exports bm.app.Controller;
    opens bm.app.Controller to javafx.fxml;
    exports bm.app.Model.cliente;
    opens bm.app.Model.cliente to javafx.fxml;
    exports bm.app.Model.pedidos;
    opens bm.app.Model.pedidos to javafx.fxml;
    exports bm.app.Model;
    opens bm.app.Model to javafx.fxml;
    exports bm.app.Model.notas;
    opens bm.app.Model.notas to javafx.fxml;
    exports bm.app.Utils;
    opens bm.app.Utils to javafx.fxml;
    opens bm.app.Model.credenciamento to javafx.fxml, javafx.base;
    exports bm.app.Model.credenciamento;

}
