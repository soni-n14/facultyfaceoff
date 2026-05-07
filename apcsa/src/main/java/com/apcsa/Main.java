package com.apcsa;

import com.apcsa.combat.towers.Signore;
import com.apcsa.ui.InputManager;
import com.apcsa.ui.UIStyles;
import com.apcsa.waves.WaveManager;
import com.apcsa.world.GameWorld;
import com.apcsa.world.Health;

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
    public static Text moneyText;
    public static Text baseHealthText;

    public static Button signoreButton;
    public static Button farmButton;
    public static Button skipButton;

    public static Scene scene;

    public static Circle rangePreview;
    public static Circle rangePreviewPlaced;

    public static ImageView towerPreview;

    @Override
    public void start(Stage stage) {
        rangePreview = new Circle();
        rangePreview.setFill(Color.rgb(128, 128, 128, 0.25));
        rangePreview.setStroke(Color.GRAY);
        rangePreview.setVisible(false);
        rangePreview.setMouseTransparent(true);

        rangePreviewPlaced = new Circle();
        rangePreviewPlaced.setFill(Color.rgb(128, 128, 128, 0.25));
        rangePreviewPlaced.setStroke(Color.GRAY);
        rangePreviewPlaced.setVisible(false);
        rangePreviewPlaced.setMouseTransparent(true);
        
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
        timeText.setX(392);
        timeText.setY(100);

        waveText = new Text("Wave: 0");
        waveText.setX(380);
        waveText.setY(70);

        moneyText = new Text("Money: 100");
        moneyText.setX(650);
        moneyText.setY(50);

        baseHealthText = new Text(Health.baseHealth+"/"+Health.maxBaseHealth);
        baseHealthText.setX(357);
        baseHealthText.setY(40);

        pane.getChildren().add(waveText);
        pane.getChildren().add(timeText);
        pane.getChildren().addAll(moneyText);
        pane.getChildren().addAll(baseHealthText);

        UIStyles.setWaveText(waveText);
        UIStyles.setWaveText(timeText);
        UIStyles.setMoneyText(moneyText);
        UIStyles.setBaseHealthText(baseHealthText);

        signoreButton = new Button("Signore");
        signoreButton.setLayoutX(350);
        signoreButton.setLayoutY(500);

        farmButton = new Button("Farm");
        farmButton.setLayoutX(500);
        farmButton.setLayoutY(500);

        skipButton = new Button("Skip");
        skipButton.setLayoutX(200);
        skipButton.setLayoutY(500);

        UIStyles.styleTowerPlacementButton(signoreButton);
        UIStyles.styleTowerPlacementButton(farmButton);
        UIStyles.styleTowerPlacementButton(skipButton);

        pane.getChildren().add(signoreButton);
        pane.getChildren().add(skipButton);
        pane.getChildren().addAll(farmButton);

        scene = new Scene(pane, 800, 600);

        stage.setScene(scene);
        stage.setTitle("Teacher Defense");
        stage.show();

        pane.getChildren().add(rangePreview);
        pane.getChildren().add(towerPreview);
        pane.getChildren().addAll(rangePreviewPlaced);


        //main gameloop
        GameWorld.startGameLoop();


        WaveManager.runIt();
        InputManager.setUpImageClick(signoreButton);
        InputManager.setUpImageClick(farmButton);
        
        InputManager.setUpKeybindManager();


    }

    public static void main(String[] args) {
        launch(args);
    }
}