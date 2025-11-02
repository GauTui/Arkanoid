module com.example.arkanoid {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires jdk.jfr;
    requires javafx.media;
    //requires com.example.arkanoid;

    opens com.example.arkanoid to javafx.fxml;
    exports com.example.arkanoid;
    exports com.example.arkanoid.Model;
    opens com.example.arkanoid.Model to javafx.fxml;
}