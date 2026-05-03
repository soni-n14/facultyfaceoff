package com.apcsa;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Pane pane;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(800, 600);

        pane = new Pane(canvas);

        Scene scene = new Scene(pane, 800, 600);

        //scene.setOnMouseMoved(e -> {
        //    Hover.moved(e.getX(), e.getY());
        //});

        stage.setScene(scene);
        stage.setTitle("Teacher Defense");
        stage.show();

        TestCombat.runIt();
        WaveManager.runIt();
    }

    public static void main(String[] args) {
        launch(args);
    }
}