module bm.app.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    exports bm.app.View;
    opens bm.app.View to javafx.fxml;
    exports bm.app.Metodos;
    opens bm.app.Metodos to javafx.fxml;
    exports bm.app.Controller;
    opens bm.app.Controller to javafx.fxml;
    exports bm.app.Model.cliente;
    opens bm.app.Model.cliente to javafx.fxml;
    exports bm.app.Model.pedidos;
    opens bm.app.Model.pedidos to javafx.fxml;

}
