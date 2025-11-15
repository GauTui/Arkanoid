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
    requires java.desktop;
    requires javafx.base;
    //requires com.example.arkanoid;
    //requires com.example.arkanoid;

    opens com.example.arkanoid to javafx.fxml;
    exports com.example.arkanoid;
    exports com.example.arkanoid.model;
    opens com.example.arkanoid.model to javafx.fxml;
    exports com.example.arkanoid.model.brick;
    opens com.example.arkanoid.model.brick to javafx.fxml;
    exports com.example.arkanoid.model.powerup;
    opens com.example.arkanoid.model.powerup to javafx.fxml;
    exports com.example.arkanoid.core;
    opens com.example.arkanoid.core to javafx.fxml;
    exports com.example.arkanoid.ui;
    opens com.example.arkanoid.ui to javafx.fxml;
}