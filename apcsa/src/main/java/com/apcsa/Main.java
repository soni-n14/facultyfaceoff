package com.apcsa;

import com.apcsa.combat.towers.Signore;
import com.apcsa.ui.InputManager;
import com.apcsa.waves.WaveManager;
import com.apcsa.world.GameWorld;

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
    public static Button signoreButton;
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

        pane = new Pane(canvas);

        waveText = new Text("Wave: 0");
        waveText.setX(400);
        waveText.setY(500);

        pane.getChildren().add(waveText);

        signoreButton = new Button("Signore");
        signoreButton.setLayoutX(300);
        signoreButton.setLayoutY(400);

        pane.getChildren().add(signoreButton);

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