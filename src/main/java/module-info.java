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
    requires javafx.swing;
    requires javafx.graphics;
    requires jdk.jfr;
    requires javafx.media;
    requires java.desktop;
    requires javafx.base;
    //requires com.example.arkanoid;
    //requires com.example.arkanoid;
    //requires com.example.arkanoid;

    opens com.example.arkanoid to javafx.fxml;
    exports com.example.arkanoid.Model;
    opens com.example.arkanoid.Model to javafx.fxml;
    exports com.example.arkanoid.Model.Bricks;
    opens com.example.arkanoid.Model.Bricks to javafx.fxml;
    exports com.example.arkanoid.Model.Entities;
    opens com.example.arkanoid.Model.Entities to javafx.fxml;
    exports com.example.arkanoid.Model.PowerUps;
    opens com.example.arkanoid.Model.PowerUps to javafx.fxml;
    exports com.example.arkanoid.Application;
    opens com.example.arkanoid.Application to javafx.fxml;
    exports com.example.arkanoid.Controller.Managers;
    opens com.example.arkanoid.Controller.Managers to javafx.fxml;
    exports com.example.arkanoid.Controller.GameState;
    opens com.example.arkanoid.Controller.GameState to javafx.fxml;
    exports com.example.arkanoid.Controller.Loop;
    opens com.example.arkanoid.Controller.Loop to javafx.fxml;
    exports com.example.arkanoid.Controller.Utils;
    opens com.example.arkanoid.Controller.Utils to javafx.fxml;
}