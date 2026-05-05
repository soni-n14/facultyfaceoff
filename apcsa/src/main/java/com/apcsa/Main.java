package com.apcsa;

import com.apcsa.combat.towers.Signore;
import com.apcsa.ui.InputManager;
import com.apcsa.waves.WaveManager;
import com.apcsa.world.GameWorld;

import javafx.scene.canvas.GraphicsContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class Main extends Application {

    public static Pane pane;
    public static Text waveText;
    public static Text timeText;
    public static Button signoreButton;
    public static Button skipButton;
    public static Scene scene;

    public static Circle rangePreview;
    public static ImageView towerPreview;

    @Override
    public void start(Stage stage) {
        rangePreview = new Circle();
        rangePreview.setFill(Color.rgb(128, 128, 128, 0.25));
        rangePreview.setStroke(Color.GRAY);
        rangePreview.setVisible(false);
        rangePreview.setMouseTransparent(true);
        
        towerPreview = new ImageView();
        towerPreview.setVisible(false);
        towerPreview.setFitWidth(64);
        towerPreview.setFitHeight(64);

        Canvas canvas = new Canvas(800, 600);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.LIGHTGRAY);

        for (int x = 0; x <= 800; x += 64) {
            gc.strokeLine(x, 0, x, 600);
        }

        for (int y = 0; y <= 600; y += 64) {
            gc.strokeLine(0, y, 800, y);
        }

        pane = new Pane(canvas);

        timeText = new Text("0:15");
        timeText.setX(400);
        timeText.setY(520);

        waveText = new Text("Wave: 0");
        waveText.setX(400);
        waveText.setY(500);

        pane.getChildren().add(waveText);
        pane.getChildren().add(timeText);

        signoreButton = new Button("Signore");
        signoreButton.setLayoutX(300);
        signoreButton.setLayoutY(400);

        skipButton = new Button("Skip");
        skipButton.setLayoutX(200);
        skipButton.setLayoutY(200);

        pane.getChildren().add(signoreButton);
        pane.getChildren().add(skipButton);

        scene = new Scene(pane, 800, 600);

        stage.setScene(scene);
        stage.setTitle("Teacher Defense");
        stage.show();

        pane.getChildren().add(rangePreview);
        pane.getChildren().add(towerPreview);


        //main gameloop
        GameWorld.startGameLoop();


        WaveManager.runIt();
        InputManager.setUpImageClick(signoreButton);


    }

    public static void main(String[] args) {
        launch(args);
    }
}