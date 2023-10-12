module bm.app.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens bm.app.demo to javafx.fxml;
    exports bm.app.demo;

}
