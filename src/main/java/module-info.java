module main.librarymanagement {
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
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires java.sql;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires javafx.swing;

    opens main to javafx.fxml;
    exports main;
    exports controller;
    opens controller to javafx.fxml;
    exports Services;
    opens Services to javafx.fxml;
}