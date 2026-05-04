package com.apcsa;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Main extends Application {

    public static Pane pane;
    public static Text waveText;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(800, 600);

        pane = new Pane(canvas);

        waveText = new Text("Wave: 0");
        waveText.setX(20);
        waveText.setY(30);

        pane.getChildren().add(waveText);

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